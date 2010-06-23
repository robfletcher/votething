package votething.poll

import votething.auth.User

class Poll {

	User creator
	String title
	List<String> options

	static hasMany = [options: String]

	static constraints = {
		title blank: false
		options minSize: 2
    }
}
