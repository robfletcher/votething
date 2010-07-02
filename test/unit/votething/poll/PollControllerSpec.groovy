package votething.poll

import grails.plugin.spock.ControllerSpec
import org.apache.commons.lang.math.RandomUtils
import spock.lang.Shared
import votething.auth.User
import votething.auth.UserService
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import static javax.servlet.http.HttpServletResponse.SC_OK

class PollControllerSpec extends ControllerSpec {

	@Shared List<Poll> polls = (1..3).collect { i ->
		new Poll(title: "Poll $i", options: ["Option 1", "Option 2", "Option 3"])
	}

	@Shared User user = new User(username: "blackbeard")

	def setup() {
		mockLogging PollController, true
		mockMessage PollController

		mockDomain Poll, polls
		mockDomain Vote
		mockDomain User, [user]
	}

	private void mockMessage(Class clazz) {
		registerMetaClass clazz
		clazz.metaClass.message = { Map attrs ->
			attrs.code
		}
	}

	def "The show action requires a valid id parameter"() {
		when: "the show action is invoked without a valid id"
		controller.params.id = id
		controller.show()

		then: "the response status is 404"
		controller.response.status == SC_NOT_FOUND

		where:
		id << [null, 9]
	}

	def "The show action finds the poll with the specified id"() {
		when: "the show action is invoked with a valid id"
		controller.params.id = poll.id
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
		controller.userService = Mock(UserService)
		controller.userService.currentUser >> user

		when: "the user submits a vote"
		controller.params.id = poll.id
		controller.params.option = option
		controller.vote()

		then: "the user is redirected to the show action"
		controller.redirectArgs.action == "show"
		controller.redirectArgs.id == poll.id

		and: "the vote is registered"
		Vote.count() == 1
		def vote = Vote.findByUserAndPoll(user, poll)
		vote != null
		vote.option == option

		where:
		poll << polls
		option = RandomUtils.nextInt(poll.options.size())
	}

	def "A user can only vote on a poll once"() {
		given: "a logged in user"
		controller.userService = Mock(UserService)
		controller.userService.currentUser >> user

		and: "that user has already voted on the poll"
		new Vote(user: user, poll: poll, option: 0).save()

		when: "the user submits a second vote"
		controller.params.id = poll.id
		controller.params.option = "0"
		controller.vote()

		then: "the poll is re-displayed with an error"
		controller.renderArgs.model.voteInstance.errors.user == "unique"

		and: "the second vote is not registered"
		Vote.count() == 1

		where:
		poll = polls.head()
	}

	def "The vote action requires a valid option"() {
		given: "a logged in user"
		controller.userService = Mock(UserService)
		controller.userService.currentUser >> user

		when: "the user submits a vote with an invalid option"
		controller.params.id = polls[0].id
		controller.params.option = option
		controller.vote()

		then: "the poll is re-displayed with an error"
		controller.renderArgs.model.voteInstance.errors.option == errorCode

		and: "the vote is not registered"
		Vote.count() == 0

		where:
		option << [null, 9, -1, "a"]
		errorCode << ["nullable", "range", "range", "nullable"]
	}

	def "A user can create a new poll"() {
		given: "a logged in user"
		controller.userService = Mock(UserService)
		controller.userService.currentUser >> user

		when: "the users saves a poll"
		controller.params.title = title
		controller.params."options[0]" = options[0]
		controller.params."options[1]" = options[1]
		controller.save()

		then: "the user is redirected to the poll"
		controller.redirectArgs.action == "show"
		def pollId = controller.redirectArgs.id

		and: "the poll is saved with the submitted details"
		def poll = Poll.get(pollId)
		poll.title == title
		poll.options == options

		and: "the user becomes the poll creator"
		poll.creator == user

		where:
		title = "Whatever"
		options = ["Option 1", "Option 2"]
	}
}
