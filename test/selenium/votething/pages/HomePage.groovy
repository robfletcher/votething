package votething.pages

import grails.plugins.selenium.pageobjects.Page

class HomePage extends Page {

	static HomePage open() {
		new HomePage("/")
	}

	HomePage() {
		super()
	}

	protected HomePage(String uri) {
		super(uri)
	}

	void verifyPage() {
		pageTitleIs "Welcome to Grails"
	}

	String getLoginMessage() {
		isLoggedIn() ? selenium.getText("css=#login .message") : null
	}

	boolean isLoggedIn() {
		return selenium.isElementPresent("css=#login .message")
	}

	HomePage logOut() {
		selenium.clickAndWait("link=Log out")
		return new HomePage()
	}

	LoginPage logIn() {
		selenium.clickAndWait("link=Log in here")
		return new LoginPage()
	}
}
