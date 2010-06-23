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

	def "a user can log in"() {
		when: "a user visits the login page"
		def loginPage = LoginPage.open()

		and: "enters their details correctly"
		def homePage = loginPage.login("blackbeard")

		then: "they should see a welcome message"
		homePage.loginMessage == "Welcome back blackbeard"
	}

	def "login fails if user submits incorrect details"() {
		when: "the user visits the login page"
		def loginPage = LoginPage.open()

		and: "enters their details incorrectly"
		loginPage.loginIncorrect("blackbeard", "badpassword")

		then: "they should see an error message"
		loginPage.loginMessage == "Sorry, we were not able to find a user with that username and password."

		and: "they should not be logged in"
		!HomePage.open().isLoggedIn()
	}

	def "a user can log out"() {
		given: "a logged in user"
		def loginPage = LoginPage.open()
		def homePage = loginPage.login("blackbeard")

		when: "they click the log out link"
		homePage.logOut()

		then: "they should no longer be logged in"
		!homePage.isLoggedIn()
	}

}
