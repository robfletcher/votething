package votething.auth

class UserService {

    static transactional = true

	def springSecurityService

	User getCurrentUser() {
		def principal = springSecurityService.principal
		principal ? User.read(principal.id) : null
    }
}
