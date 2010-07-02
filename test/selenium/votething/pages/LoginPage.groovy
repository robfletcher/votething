package votething.pages

import grails.plugins.selenium.pageobjects.Page
import votething.auth.User

class LoginPage extends Page {

	static final String LOGIN_FAILED_MESSAGE = "Sorry, we were not able to find a user with that username and password."
	
	private final Class<? extends Page> loginSuccessPage

	static LoginPage open() {
		new LoginPage("/login")
	}

	static HomePage logout() {
		new HomePage("/logout")
	}

	static Page login(String username, String password = "password") {
		open().loginAs(username, password)
	}

	static Page login(User user, String password = "password") {
		login user.username, password
	}

	LoginPage() {
		super()
	}

	protected LoginPage(String uri) {
		super(uri)
	}

	LoginPage(String uri, Class<? extends Page> loginSuccessPage) {
		super(uri)
		this.loginSuccessPage = loginSuccessPage
	}

	void verifyPage() {
		pageTitleIs "Login"
	}

	Page loginAs(String username, String password = "password") {
		selenium.type "j_username", username
		selenium.type "j_password", password
		selenium.clickAndWait "css=input[type=submit]"
		if (loginSuccessPage) {
			return loginSuccessPage.newInstance()
		} else {
			return new HomePage()
		}
	}

	Page loginAs(User user, String password = "password") {
		loginAs(user.username, password)
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
