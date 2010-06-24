package votething.poll

import grails.plugin.spock.ControllerSpec
import spock.lang.Shared
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import static javax.servlet.http.HttpServletResponse.SC_OK

class PollControllerSpec extends ControllerSpec {

	@Shared List<Poll> polls = (1..3).collect { i ->
		new Poll(title: "Poll $i", uri: "poll$i")
	}

	def setup() {
		mockDomain Poll, polls
	}

	def "The show action requires a valid URI parameter"() {
		when: "the show action is invoked without a URI"
		controller.params.uri = uri
		controller.show()

		then: "the response status is not-found"
		controller.response.status == SC_NOT_FOUND

		where:
		uri << [null, "invalid"]
	}

	def "The show action finds the poll with the specified URI"() {
		when: "the show action is invoked with a valid URI"
		controller.params.uri = uri
		def model = controller.show()

		then: "the response status is OK"
		controller.response.status == SC_OK

		and: "the correct poll is added to the model"
		model.pollInstance == poll

		where:
		uri << polls.uri
		poll << polls
	}
}
