package votething.voting

import spock.lang.Specification
import votething.auth.User
import votething.auth.Role
import votething.auth.UserRole
import votething.poll.Poll
import votething.pages.LoginPage
import votething.pages.PollPage

class VotingSpec extends Specification {

	def cleanup() {
		Poll.withTransaction {
			Poll.list()*.delete()
			UserRole.removeAll Role.findByAuthority(Role.USER)
			User.list()*.delete()
		}
	}

	def "A logged-in user can visit a poll page"() {
		given: "a logged in user"
		Poll.withTransaction {
			def user = User.build(username: "blackbeard")
			UserRole.create(user, Role.findByAuthority(Role.USER), true)
		}
		LoginPage.login("blackbeard")

		and: "a poll"
		def poll = null
		Poll.withTransaction {
			poll = Poll.build()
		}

		when: "the user tries to visit the poll page"
		def pollPage = PollPage.open(poll)

		then: "he should see the poll details"
		pollPage.heading == poll.title
		pollPage.options == poll.options
	}
}
