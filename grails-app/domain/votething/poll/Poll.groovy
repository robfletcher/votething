package votething.poll

import org.joda.time.DateTime
import votething.auth.User
import org.springframework.validation.Errors
import org.codehaus.groovy.grails.validation.BlankConstraint
import org.codehaus.groovy.grails.validation.NullableConstraint

class Poll {

	User creator
	String title
	List<String> options = []
	DateTime dateCreated

	static hasMany = [options: String]

	static constraints = {
		title blank: false
		options minSize: 2, validator: { List<String> value ->
			if (value.any { !it }) {
				return "blank"
			}
		}
	}

	static transients = ["optionRange", "votes", "voteCount"]

	Range<Integer> getOptionRange() {
		0..<options.size()
	}

	List<Integer> getVotes() {
		optionRange.collect {
			Vote.countByPollAndOption(this, it)
		}
	}

	int getVoteCount() {
		Vote.countByPoll(this)
	}
}
