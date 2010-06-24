package votething.auth

import spock.lang.Specification
import votething.pages.LoginPage
import votething.pages.HomePage

class LoginSpec extends Specification {

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

	def "A user can log in"() {
		when: "a user visits the login page"
		def loginPage = LoginPage.open()

		and: "enters their details correctly"
		def homePage = loginPage.loginAs("blackbeard")

		then: "they should see a welcome message"
		homePage.loginMessage == "Welcome back blackbeard"
	}

	def "Login fails if user submits incorrect details"() {
		when: "the user visits the login page"
		def loginPage = LoginPage.open()

		and: "enters their details incorrectly"
		loginPage.loginWithIncorrectDetails("blackbeard", "badpassword")

		then: "they should see an error message"
		loginPage.loginError == LoginPage.LOGIN_FAILED_MESSAGE

		and: "they should not be logged in"
		!HomePage.open().isLoggedIn()
	}

	def "A user can log out"() {
		given: "a logged in user"
		def homePage = LoginPage.login("blackbeard")

		when: "they click the log out link"
		homePage.logOut()

		then: "they should no longer be logged in"
		!homePage.isLoggedIn()
	}

}
