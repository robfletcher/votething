package votething.auth

class Role {

	static final String USER = "ROLE_USER"

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
