package votething.poll

import org.joda.time.DateTime
import votething.auth.User

class Poll {

	User creator
	String title
	List<String> options = []
	DateTime dateCreated

	static hasMany = [options: String]

	static constraints = {
		title blank: false
		options minSize: 2
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
