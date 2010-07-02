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

	String getPageTitle() {
		selenium.title
	}

	PollPage save() {
		selenium.clickAndWait "css=input[name=create]"
		new PollPage()
	}

	CreatePollPage saveInvalid() {
		selenium.clickAndWait "css=input[name=create]"
		new CreatePollPage()
	}

	void addOption() {
		int optionCount = selenium.getXpathCount("//ol[@id='options']/li/input")
		selenium.click "css=a.addOption"
		selenium.waitForXpathCount "//ol[@id='options']/li/input", optionCount + 1
	}

	void removeOption(int index) {
		int optionCount = selenium.getXpathCount("//ol[@id='options']/li/input")
		int i = index + 1 // 1 based index
		selenium.click "//ol[@id='options']/li[$i]/a[@class='removeOption']"
		selenium.waitForXpathCount "//ol[@id='options']/li/input", optionCount - 1
	}

	boolean hasFieldErrors(String name) {
		selenium.getAttribute("css=input#$name@class") =~ /\berror\b/
	}

	String getFieldErrors(String name) {
		selenium.getText "css=input#$name + .errorMessage"
	}

	def propertyMissing(String name) {
		if (name ==~ /options_\d+/) {
			selenium.getValue "css=input#$name"
		} else {
			super.propertyMissing(name)
		}
	}

	def propertyMissing(String name, Object value) {
		if (name ==~ /options_\d+/) {
			selenium.type "css=input#$name", value
		} else {
			super.propertyMissing(name, value)
		}
	}
}
