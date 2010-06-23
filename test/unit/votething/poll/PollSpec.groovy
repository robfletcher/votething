package votething.poll

import grails.plugin.spock.*
import votething.auth.User

class PollSpec extends UnitSpec {

	def user = new User(username: "blackbeard")

	def setup() {
		mockDomain Poll
	}

    def "Poll requires a title and a creator"() {
		when: "a poll is created with no title or creator"
		def poll = new Poll()

		then: "validation should fail"
		!poll.validate()

		and: "nullable errors should be set on the title and creator fields"
		poll.errors.title == "nullable"
		poll.errors.creator == "nullable"
    }

	def "Poll requires a title"() {
		when: "a poll is created with a blank title"
		def poll = new Poll(title: "", creator: user)

		then: "validation should fail"
		!poll.validate()

		and: "blank errors should be set on the title field"
		poll.errors.title == "blank"
	}

	def "Poll requires at least two options"() {
		when: "a poll is created with only one option"
		def poll = new Poll(title: "whatever", creator: user, options: ["whatever"])

		then: "validation should fail"
		!poll.validate()

		and: "minimum size error should be set on the options field"
		poll.errors.options == "minSize"
	}
}
