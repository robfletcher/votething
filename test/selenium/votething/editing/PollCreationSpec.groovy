package votething.editing

import spock.lang.Specification
import votething.pages.CreatePollPage
import votething.auth.User
import votething.auth.Role
import votething.auth.UserRole
import votething.pages.LoginPage
import votething.poll.Poll
import spock.lang.Shared

class PollCreationSpec extends Specification {

	@Shared User user

	def setupSpec() {
		user = User.build(username: "blackbeard")
		UserRole.create(user, Role.findByAuthority(Role.USER), true)
	}

	def cleanup() {
		LoginPage.logout()
	}

	def cleanupSpec() {
		UserRole.removeAll Role.findByAuthority(Role.USER)
		User.list()*.delete(flush: true)
	}

	def "Only logged in users can create polls"() {
		when: "an anonymous user tries to visit the create poll page"
		def loginPage = CreatePollPage.openNotAuthenticated()

		and: "logs in"
		def createPage = loginPage.loginAs("blackbeard")

		then: "they are redirected to the create poll page"
		createPage.pageTitle == "Create Poll"
	}

	def "Mandatory fields must be filled in"() {
		given: "a logged in user"
		LoginPage.login("blackbeard")

		when: "the user submits the form without filling in any fields"
		def createPage = CreatePollPage.open()
		createPage.saveInvalid()

		then: "errors are displayed"
		createPage.hasFieldErrors("title")
		createPage.getFieldErrors("title") == "Property [title] of class [class votething.poll.Poll] cannot be blank"
		createPage.hasFieldErrors("options_0")
		createPage.hasFieldErrors("options_1")
	}

	def "A user can create a poll"() {
		given: "a logged in user"
		LoginPage.login("blackbeard")

		when: "the user submits the form with valid details"
		def createPage = CreatePollPage.open()
		createPage.title = title
		createPage."options\\[0\\]" = options[0]
		createPage."options\\[1\\]" = options[1]
		def pollPage = createPage.save()

		then: "a poll is created"
		def poll = Poll.findByTitle(title)
		poll != null
		poll.title == title
		poll.options == options
		poll.creator == user

		and: "the user is redirected to the poll page"
		pollPage.heading == title
		pollPage.options == options

		where:
		title = "Who is the deadliest warrior?"
		options = ["Pirate", "Ninja"]
	}

}
