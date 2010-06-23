package votething.poll

import grails.plugin.spock.*
import votething.auth.User

class VoteSpec extends UnitSpec {

	def user = new User(username: "blackbeard")
	def poll = new Poll(title: "whatever", creator: user, options: ["option 1", "option 2"])

	def setup() {
		mockDomain Vote
	}

	def "Vote requires a poll, a user and an option"() {
		when: "a vote is created without a poll, a user or an option"
		def vote = new Vote()

		then: "validation fails"
		!vote.validate()

		and: "nullable errors are present on the poll, user and option fields"
		vote.errors.poll == "nullable"
		vote.errors.user == "nullable"
		vote.errors.option == "nullable"
	}

	def "User can only vote for an option from those available on the poll"() {
		when: "a vote is created with an option outside the available range"
		def vote = new Vote(poll: poll, user: user, option: option)

		then: "validation fails"
		!vote.validate()

		and: "range error is present on the option field"
		vote.errors.option == "validator"

		where:
		option << [-1, 2]
	}

	def "A user can only vote on a poll once"() {
		given: "a user has already voted on a poll"
		new Vote(poll: poll, user: user, option: 0).save()

		when: "the user tries to vote again on the same poll"
		def vote = new Vote(poll: poll, user: user, option: 1)

		then: "validation fails"
		!vote.validate()

		and: "unique error is present on the user field"
		vote.errors.user == "unique"
	}

}
