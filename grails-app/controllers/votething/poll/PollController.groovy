package votething.poll

import grails.plugins.springsecurity.Secured
import votething.auth.Role
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import votething.auth.User

class PollController {

	static allowedMethods = [vote: "POST"]

	def springSecurityService

	@Secured(Role.USER)
	def show = {
		def pollInstance = params.uri ? Poll.findByUri(params.uri) : null
		if (!pollInstance) {
			response.sendError SC_NOT_FOUND
		} else {
			[pollInstance: pollInstance]
		}
	}

	@Secured(Role.USER)
	vote = {
		def pollInstance = params.id ? Poll.read(params.id) : null
		if (!pollInstance) {
			log.error "vote: no poll with id $params.id exists"
			response.sendError SC_NOT_FOUND
		} else {
			def userDetails = springSecurityService.principal
			def user = User.get(userDetails.id)
			new Vote(user: user, poll: poll, option: params.int("option"))
		}
	}

}
