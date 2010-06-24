package votething.pages

import grails.plugins.selenium.pageobjects.Page
import votething.poll.Poll

class PollPage extends Page {

	private String expectedTitle

	static PollPage open(Poll poll) {
		new PollPage(poll)
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

	String getHeading() {
		selenium.getText("css=h1")
	}

	List<String> getOptions() {
		int numOptions = selenium.getXpathCount("//fieldset[@class='options']/ol/li")
		numOptions == 0 ? [] : (1..numOptions).collect { i ->
			selenium.getText("//fieldset[@class='options']/ol/li[$i]/label")
		}
	}

}
