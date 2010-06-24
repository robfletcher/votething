package votething.poll

import grails.plugin.spock.UnitSpec
import votething.auth.User
import grails.plugin.spock.IntegrationSpec

class PollSpec extends UnitSpec {

	def user = new User(username: "blackbeard")

	def setup() {
		mockDomain Poll
	}

    def "Poll requires a title and a creator"() {
		when: "a poll is created with no title or creator"
		def poll = new Poll()

		then: "validation fails"
		!poll.validate()

		and: "nullable errors are present on the title and creator fields"
		poll.errors.title == "nullable"
		poll.errors.creator == "nullable"
    }

	def "Poll requires a non-blank title"() {
		when: "a poll is created with a blank title"
		def poll = new Poll(title: "", creator: user)

		then: "validation fails"
		!poll.validate()

		and: "blank errors are present on the title field"
		poll.errors.title == "blank"
	}

	def "Poll requires at least two options"() {
		when: "a poll is created with only one option"
		def poll = new Poll(title: "whatever", creator: user, options: ["whatever"])

		then: "validation fails"
		!poll.validate()

		and: "minimum size are present set on the options field"
		poll.errors.options == "minSize"
	}
	
	def "Date created is set automatically"() {
		when: "a new poll is saved"
		def poll = new Poll(title: "whatever", creator: user, options: ["option 1", "option 2"]).save(failOnError: true)
		
		then: "date created is set automatically"
		poll.dateCreated != null
	}

	def "Poll URI is set automatically"() {
		when: "a new poll is saved"
		def poll = new Poll(title: "whatever", creator: user, options: ["option 1", "option 2"]).save(failOnError: true)

		then: "its URI is set automatically"
		poll.uri ==~ /[\w\d]+/
	}
}
