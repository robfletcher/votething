package votething.pages

import grails.plugins.selenium.pageobjects.Page

class LoginPage extends Page {

	static final String LOGIN_FAILED_MESSAGE = "Sorry, we were not able to find a user with that username and password."

	static LoginPage open() {
		new LoginPage("/login")
	}

	static HomePage logout() {
		new HomePage("/logout")
	}

	static HomePage login(String username, String password = "password") {
		return open().loginAs(username, password)
	}

	LoginPage() {
		super()
	}

	protected LoginPage(String uri) {
		super(uri)
	}

	void verifyPage() {
		pageTitleIs "Login"
	}

	HomePage loginAs(String username, String password = "password") {
		selenium.type "j_username", username
		selenium.type "j_password", password
		selenium.clickAndWait "css=input[type=submit]"
		return new HomePage()
	}

	LoginPage loginWithIncorrectDetails(String username, String password = "password") {
		selenium.type "j_username", username
		selenium.type "j_password", password
		selenium.clickAndWait "css=input[type=submit]"
		return this
	}

	String getLoginError() {
		selenium.isElementPresent("css=.login_message") ? selenium.getText("css=.login_message") : null
	}
}
