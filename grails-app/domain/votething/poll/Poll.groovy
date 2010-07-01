package votething.poll

import org.joda.time.DateTime
import votething.auth.User
import org.springframework.validation.Errors
import org.codehaus.groovy.grails.validation.BlankConstraint

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
				def constraint = new BlankConstraint()
				constraint.parameter = false
				constraint.owningClass = Poll
				constraint.propertyName = "options[$i]"
				constraint.validate self, s, errors
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
