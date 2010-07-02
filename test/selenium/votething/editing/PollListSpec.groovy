package votething.editing

import spock.lang.Shared
import spock.lang.Specification
import votething.auth.Role
import votething.auth.User
import votething.auth.UserRole
import votething.pages.LoginPage
import votething.poll.Poll
import votething.poll.Vote
import votething.pages.ListPollPage

class PollListSpec extends Specification {

	@Shared List<User> users

	@Shared List<Poll> polls

	def setupSpec() {
		Poll.withTransaction {
			users = ["blackbeard", "ponytail", "roundhouse"].collect {
				def user = User.build(username: it)
				UserRole.create(user, Role.findByAuthority(Role.USER), true)
				user
			}

			polls = (0..3).collect {
				Poll.build()
			}
		}
	}

	def cleanup() {
		LoginPage.logout()
	}

	def cleanupSpec() {
		Poll.withTransaction {
			Vote.list()*.delete()
			Poll.list()*.delete()
			UserRole.removeAll Role.findByAuthority(Role.USER)
			User.list()*.delete()
		}
	}

	def "A user can view a list of polls"() {
		given: "a logged in user"
		LoginPage.login "blackbeard"

		when: "the user opens the poll list"
		def listPage = ListPollPage.open()

		then: "the polls are displayed"
		listPage.rowCount == polls.size()
		def rows = listPage.rows
		rows[0].Title == polls[0].title
		rows[1].Title == polls[1].title
		rows[2].Title == polls[2].title
		rows[3].Title == polls[3].title
	}

	def "The poll list contains vote counts"() {
		given: "a logged in user"
		LoginPage.login "blackbeard"

		and: "some votes for the polls"
		Vote.withTransaction {
			3.times { Vote.build(poll: polls[0], user: users[it]) }
			2.times { Vote.build(poll: polls[1], user: users[it]) }
			1.times { Vote.build(poll: polls[2], user: users[it]) }
		}

		when: "the user opens the poll list"
		def listPage = ListPollPage.open()

		then: "the vote counts are displayed"
		def rows = listPage.rows
		rows[0].Votes == "3"
		rows[1].Votes == "2"
		rows[2].Votes == "1"
		rows[3].Votes == "0"
	}

}
