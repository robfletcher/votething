package votething.poll

import votething.auth.User

class Vote {

	Poll poll
	User user
	Integer option

    static constraints = {
		user unique: "poll"
		option validator: { Integer value, Vote self ->
			value in self.poll?.optionRange
		}
    }
}
