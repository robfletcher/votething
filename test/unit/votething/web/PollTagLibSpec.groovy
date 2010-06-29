package votething.web

import grails.plugin.spock.TagLibSpec
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import votething.poll.Poll
import votething.poll.Vote
import votething.auth.User
import grails.plugins.springsecurity.SpringSecurityService

class PollTagLibSpec extends TagLibSpec {

	def setup() {
		tagLib.springSecurityService = Mock(SpringSecurityService)
	}

	def "poll:userHasVoted requires a poll"() {
		when: "the tag is invoked without a 'poll' attribute"
		userHasVoted([:])

		then: "an exception is raised"
		thrown(GrailsTagException)
	}

	def "poll:userHasNotVoted requires a poll"() {
		when: "the tag is invoked without a 'poll' attribute"
		userHasNotVoted([:])

		then: "an exception is raised"
		thrown(GrailsTagException)
	}

	def "poll:userHasVoted outputs the tag body when there is no logged in user"() {
		given: "a poll"
		def poll = new Poll()
		mockDomain Poll, [poll]

		and: "no logged in user"
		tagLib.springSecurityService.principal >> null

		expect:
		userHasVoted(poll: poll) { "body" } == "body"
	}

	def "poll:userHasNotVoted outputs the tag body when there is no logged in user"() {
		given: "a poll"
		def poll = new Poll()
		mockDomain Poll, [poll]

		and: "no logged in user"
		tagLib.springSecurityService.principal >> null

		expect:
		userHasNotVoted(poll: poll) { "body" } == "body"
	}

	def "poll:userHasVoted skips the tag body if a user has not voted"() {
		given: "a poll"
		def poll = new Poll()
		mockDomain Poll, [poll]

		and: "a logged in user who has not voted"
		def user = new User(username: "blackbeard")
		mockDomain User, [user]
		tagLib.springSecurityService.principal >> [id: user.id]
		mockDomain Vote

		expect:
		userHasVoted(poll: poll) { "body" } != "body"
	}

	def "poll:userHasNotVoted outputs the tag body if a user has not voted"() {
		given: "a poll"
		def poll = new Poll()
		mockDomain Poll, [poll]

		and: "a logged in user who has not voted"
		def user = new User(username: "blackbeard")
		mockDomain User, [user]
		tagLib.springSecurityService.principal >> [id: user.id]
		mockDomain Vote

		expect:
		userHasNotVoted(poll: poll) { "body" } == "body"
	}

	def "poll:userHasVoted skips the tag body if a user has already voted"() {
		given: "a poll"
		def poll = new Poll()
		mockDomain Poll, [poll]

		and: "a logged in user who has voted on the poll"
		def user = new User(username: "blackbeard")
		mockDomain User, [user]
		tagLib.springSecurityService.principal >> [id: user.id]
		mockDomain Vote, [new Vote(poll: poll, user: user)]

		expect:
		userHasVoted(poll: poll) { "body" } == "body"
	}

	def "poll:userHasNotVoted skips the tag body if a user has already voted"() {
		given: "a poll"
		def poll = new Poll()
		mockDomain Poll, [poll]

		and: "a logged in user who has voted on the poll"
		def user = new User(username: "blackbeard")
		mockDomain User, [user]
		tagLib.springSecurityService.principal >> [id: user.id]
		mockDomain Vote, [new Vote(poll: poll, user: user)]

		expect:
		userHasNotVoted(poll: poll) { "body" } != "body"
	}

}
