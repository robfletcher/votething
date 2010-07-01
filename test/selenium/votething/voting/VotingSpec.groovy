package votething.voting

import spock.lang.Specification
import votething.auth.User
import votething.auth.Role
import votething.auth.UserRole
import votething.poll.Poll
import votething.pages.LoginPage
import votething.pages.PollPage
import spock.lang.Shared
import votething.poll.Vote

class VotingSpec extends Specification {

	@Shared User user

	def setupSpec() {
		User.withTransaction {
			user = User.build(username: "blackbeard")
			UserRole.create(user, Role.findByAuthority(Role.USER), true)
		}
	}

	def cleanup() {
		LoginPage.logout()

		Poll.withTransaction {
			Vote.list()*.delete()
			Poll.list()*.delete()
		}
	}

	def cleanupSpec() {
		User.withTransaction {
			UserRole.removeAll Role.findByAuthority(Role.USER)
			User.list()*.delete()
		}
	}

	def "A logged-in user can visit a poll page"() {
		given: "a poll"
		def poll = null
		Poll.withTransaction {
			poll = Poll.build()
		}

		and: "a logged-in user"
		LoginPage.login(user)

		when: "the user tries to visit the poll page"
		def pollPage = PollPage.open(poll)

		then: "he should see the poll details"
		pollPage.heading == poll.title
		pollPage.options == poll.options
	}

	def "A user must log in before visiting a poll page"() {
		given: "a poll"
		def poll = null
		Poll.withTransaction {
			poll = Poll.build()
		}

		when: "an anonymous user tries to visit the poll page"
		def loginPage = PollPage.openAnonymous(poll)

		and: "they then log in"
		def pollPage = loginPage.loginAs(user)

		then: "they are redirected back to the poll"
		pollPage.title == poll.title
	}

	def "A user can vote on a poll"() {
		given: "a poll"
		def poll = null
		Poll.withTransaction {
			poll = Poll.build()
		}

		and: "a logged-in user"
		LoginPage.login(user)

		when: "the user votes on a poll"
		def pollPage = PollPage.open(poll)
		pollPage.voteFor(poll.options[i])

		then: "a vote is recorded"
		def vote = Vote.findByUserAndPoll(user, poll)
		vote != null
		vote.option == i

		where:
		i << (0..4)
	}

	def "A user cannot vote on a poll more than once"() {
		given: "a poll that a user has already voted on"
		def poll = null
		Poll.withTransaction {
			poll = Poll.build()
			Vote.build(poll: poll, user: user, option: 0)
		}

		and: "a logged-in user"
		LoginPage.login(user)

		when: "the visits the poll page"
		def pollPage = PollPage.open(poll)

		then: "the user sees the results of the poll"
		!pollPage.votingEnabled
		pollPage.resultVisible
	}
}
