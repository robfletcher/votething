package votething.pages

import grails.plugins.selenium.pageobjects.GrailsListPage

class ListPollPage extends GrailsListPage {

	static ListPollPage open() {
		new ListPollPage("/poll/list")
	}

	protected ListPollPage(String uri) {
		super(uri)
	}

	ListPollPage() {
		super()
	}
	
}
