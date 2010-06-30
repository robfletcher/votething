package votething.web

import votething.auth.User
import votething.poll.Vote

class PollTagLib {

	static namespace = "poll"

	def springSecurityService

	def userHasVoted = { attrs, body ->
		if (!attrs.poll) throwTagError("attribute 'poll' is required")
		def user = loggedInUser
		if (!user || Vote.countByPollAndUser(attrs.poll, user) > 0) {
			out << body()
		}
	}

	def userHasNotVoted = { attrs, body ->
		if (!attrs.poll) throwTagError("attribute 'poll' is required")
		def user = loggedInUser
		if (!user || Vote.countByPollAndUser(attrs.poll, user) == 0) {
			out << body()
		}
	}

	def eachOption = { attrs, body ->
		if (!attrs.poll) throwTagError("attribute 'poll' is required")
		int totalVotes = Vote.countByPoll(attrs.poll)
		attrs.poll.options.eachWithIndex { String option, int i ->
			int votes = Vote.countByPollAndOption(attrs.poll, i)
			def pct = (votes / totalVotes) * 100
			log.error "$option: $votes votes ($pct%)"
			pageScope.option = option
			pageScope.votes = votes
			pageScope.pct = pct
			out << body()
		}
	}

	private User getLoggedInUser() {
		def userDetails = springSecurityService.principal
		println "userDetails: $userDetails"
		userDetails ? User.get(userDetails.id) : null
	}

}
