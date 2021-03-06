package votething.web

import votething.poll.Vote

class PollTagLib {

	static namespace = "poll"

	def userService

	def userHasVoted = { attrs, body ->
		if (!attrs.poll) throwTagError("attribute 'poll' is required")
		def user = userService.currentUser
		if (!user || Vote.countByPollAndUser(attrs.poll, user) > 0) {
			out << body()
		}
	}

	def userHasNotVoted = { attrs, body ->
		if (!attrs.poll) throwTagError("attribute 'poll' is required")
		def user = userService.currentUser
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
			pageScope.option = option
			pageScope.votes = votes
			pageScope.pct = pct
			out << body()
		}
	}

}
