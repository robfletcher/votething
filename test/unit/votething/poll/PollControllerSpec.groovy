package votething.poll

import grails.plugin.spock.ControllerSpec
import spock.lang.Shared
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import static javax.servlet.http.HttpServletResponse.SC_OK
import votething.auth.User
import org.apache.commons.lang.math.RandomUtils
import grails.plugins.springsecurity.SpringSecurityService

class PollControllerSpec extends ControllerSpec {

	@Shared List<Poll> polls = (1..3).collect { i ->
		new Poll(title: "Poll $i", uri: "poll$i", options: ["Option 1", "Option 2", "Option 3"])
	}

	User user = new User(username: "blackbeard")

	def setup() {
		mockLogging PollController, true
		mockDomain Poll, polls
		mockDomain Vote
		mockDomain User, [user]
	}

	def "The show action requires a valid URI parameter"() {
		when: "the show action is invoked without a valid URI"
		controller.params.uri = uri
		controller.show()

		then: "the response status is 404"
		controller.response.status == SC_NOT_FOUND

		where:
		uri << [null, "invalid"]
	}

	def "The show action finds the poll with the specified URI"() {
		when: "the show action is invoked with a valid URI"
		controller.params.uri = poll.uri
		def model = controller.show()

		then: "the response status is 200"
		controller.response.status == SC_OK

		and: "the correct poll is added to the model"
		model.pollInstance == poll

		where:
		poll << polls
	}

	def "The vote action requires a valid id"() {
		when: "the vote action is invoked without a valid id"
		controller.params.id = id
		controller.vote()

		then: "the respons status is 404"
		controller.response.status == SC_NOT_FOUND

		where:
		id << [null, 9]
	}

	def "A user can vote on a poll"() {
		given: "a logged in user"
		controller.springSecurityService = Mock(SpringSecurityService)
		controller.springSecurityService.principal >> [id: user.id]

		when: "the user submits a vote"
		controller.params.id = poll.id
		controller.params.option = option
		controller.vote()

		then: "the user is redirected to the show action"
		controller.redirectArgs.action == "show"
		controller.redirectArgs.params == [uri: poll.uri]

		and: "the vote is registered"
		Vote.count() == 1
		def vote = Vote.findByUserAndPoll(user, poll)
		vote != null
		vote.option == option

		where:
		poll << polls
		option << [0, 1, 2]
	}
}
