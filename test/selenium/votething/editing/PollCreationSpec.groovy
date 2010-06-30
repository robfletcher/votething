package votething.editing

import spock.lang.Specification
import votething.pages.CreatePollPage
import votething.auth.User
import votething.auth.Role
import votething.auth.UserRole
import votething.pages.LoginPage

class PollCreationSpec extends Specification {

	def setupSpec() {
		def user = User.build(username: "blackbeard")
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
		createPage.title == "Create Poll"
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

	}

}
