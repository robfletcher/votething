package votething.poll

import grails.plugins.springsecurity.Secured
import votething.auth.Role
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

class PollController {

	@Secured(Role.USER)
	def show = {
		def pollInstance = params.uri ? Poll.findByUri(params.uri) : null
		if (!pollInstance) {
			response.sendError SC_NOT_FOUND
		} else {
			[pollInstance: pollInstance]
		}
	}

}
