package votething.poll

import org.joda.time.DateTime
import votething.auth.User

class Poll {

	User creator
	String title
	List<String> options = []
	DateTime dateCreated
	String uri

	static hasMany = [options: String]

	static constraints = {
		title blank: false
		options minSize: 2
		uri nullable: true // unfortunately required so validation will pass
	}

	static mapping = {
		options lazy: false
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

	void beforeInsert() {
		uri = Integer.toHexString(UUID.randomUUID().hashCode())
	}
}
