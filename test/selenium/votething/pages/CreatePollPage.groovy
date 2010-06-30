package votething.pages

import grails.plugins.selenium.pageobjects.GrailsFormPage

class CreatePollPage extends GrailsFormPage {

	static CreatePollPage open() {
		new CreatePollPage("/poll/create")
	}

	static LoginPage openNotAuthenticated() {
		new LoginPage("/poll/create", CreatePollPage)
	}

	CreatePollPage() {
		super()
	}

	protected CreatePollPage(String uri) {
		super(uri)
	}

	void verifyPage() {
		pageTitleIs "Create Poll"
	}

	String getTitle() {
		selenium.title
	}

	PollPage save() {
		selenium.clickAndWait("css=input[name=create]")
		new PollPage()
	}

	CreatePollPage saveInvalid() {
		selenium.clickAndWait("css=input[name=create]")
		new CreatePollPage()
	}

	boolean hasFieldErrors(String name) {
		selenium.getAttribute("css=input[name=$name]@class") =~ /\berror\b/
	}

	String getFieldErrors(String name) {
		selenium.getText("css=input[name=$name] + .errorMessage")
	}


}
