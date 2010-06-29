package votething.poll

import votething.auth.User
import org.codehaus.groovy.grails.validation.RangeConstraint
import org.springframework.validation.Errors

class Vote {

	Poll poll
	User user
	Integer option

	static constraints = {
		user unique: "poll"
		option validator: { Integer value, Vote self, Errors errors ->
			if (self.poll) {
				def constraint = new RangeConstraint()
				constraint.owningClass = Vote
				constraint.propertyName = "option"
				constraint.parameter = self.poll.optionRange
				constraint.validate self, value, errors
			}
		}
	}
}
