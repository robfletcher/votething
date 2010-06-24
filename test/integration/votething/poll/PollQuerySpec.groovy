package votething.poll

import grails.plugin.spock.IntegrationSpec
import spock.lang.Shared
import votething.auth.User

class PollQuerySpec extends IntegrationSpec {

	def users = []

	def setup() {
		users << User.build(username: "blackbeard")
		users << User.build(username: "roundhouse")
		users << User.build(username: "ponytail")
	}

	def "A vote summary can be retrieved"() {
		given: "a poll with some votes"
		def poll = Poll.build()
		users.eachWithIndex { user, i ->
			Vote.build(poll: poll, user: user, option: i % 5)
		}

		when: "I query for the vote summary"
		def votes = poll.votes

		then: "I get the numbers of votes for each option"
		votes == [1, 1, 1, 0, 0]
	}

}
