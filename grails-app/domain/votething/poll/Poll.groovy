package votething.poll

import org.joda.time.DateTime
import votething.auth.User
import org.springframework.validation.Errors

class Poll {

	User creator
	String title
	List<String> options = []
	DateTime dateCreated

	static hasMany = [options: String]

	static constraints = {
		title blank: false
		options minSize: 2, validator: { List<String> value, Poll self, Errors errors ->
			value.eachWithIndex { s, i ->
				if (!s) errors.rejectValue("options[$i]", "blank")
			}
		}
	}

	static transients = ["optionRange", "votes"]

	Range<Integer> getOptionRange() {
		0..<options.size()
	}

	List<Integer> getVotes() {
		optionRange.collect {
			Vote.countByPollAndOption(this, it)
		}
	}
}
