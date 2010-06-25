package votething.pages

import grails.plugins.selenium.pageobjects.Page
import votething.poll.Poll

class PollPage extends Page {

	private String expectedTitle

	static PollPage open(Poll poll) {
		new PollPage(poll)
	}

	static LoginPage openAnonymous(Poll poll) {
		new LoginPage("/poll/$poll.uri", PollPage)
	}

	PollPage() {
		super()
	}

	protected PollPage(Poll poll) {
		super("/poll/$poll.uri" as String)
		this.expectedTitle = poll.title
		pageTitleIs expectedTitle
	}

	void verifyPage() {
	}

	String getTitle() {
		selenium.title
	}

	String getHeading() {
		selenium.getText("css=h1")
	}

	List<String> getOptions() {
		int numOptions = selenium.getXpathCount("//fieldset[@class='options']/ol/li")
		numOptions == 0 ? [] : (1..numOptions).collect { i ->
			selenium.getText("//fieldset[@class='options']/ol/li[$i]/label")
		}
	}

	PollPage voteFor(String option) {
		int index = options.indexOf(option)
		selenium.check "css=input#option-$index"
		selenium.clickAndWait "css=input[name=submitVote]"
		return new PollPage()
	}
}