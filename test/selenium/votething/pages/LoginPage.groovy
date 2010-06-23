package votething.pages

import grails.plugins.selenium.pageobjects.Page

class LoginPage extends Page {

	static LoginPage open() {
		new LoginPage("/login")
	}

	static HomePage logout() {
		new HomePage("/logout")
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

	HomePage login(String username, String password = "password") {
		selenium.type "j_username", username
		selenium.type "j_password", password
		selenium.clickAndWait "css=input[type=submit]"
		return new HomePage()
	}

	LoginPage loginIncorrect(String username, String password = "password") {
		selenium.type "j_username", username
		selenium.type "j_password", password
		selenium.clickAndWait "css=input[type=submit]"
		return this
	}

	String getLoginMessage() {
		selenium.getText("css=.login_message")
	}
}
