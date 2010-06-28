package votething.poll

import grails.plugins.springsecurity.Secured
import votething.auth.Role
import static javax.servlet.http.HttpServletResponse.*
import votething.auth.User

class PollController {

	static allowedMethods = [vote: "POST"]

	def springSecurityService

	def beforeInterceptor = {
		println params
	}

	@Secured("ROLE_USER")
	def show = {
		def pollInstance = params.id ? Poll.read(params.id) : null
		if (pollInstance) {
			[pollInstance: pollInstance]
		} else {
			response.sendError SC_NOT_FOUND
		}
	}

	@Secured("ROLE_USER")
	vote = {
		def pollInstance = params.id ? Poll.read(params.id) : null
		if (pollInstance) {
			def voteInstance = new Vote(user: loggedInUser, poll: pollInstance, option: params.int("option"))
			log.info "vote: saving ${voteInstance.dump()}"
			if (voteInstance.save()) {
				redirect action: "show", id: pollInstance.id
			} else {
				log.error "vote: failed to save $voteInstance.errors"
				render view: "show", model: [pollInstance: pollInstance, voteInstance: voteInstance]
			}
		} else {
			log.error "vote: no poll with id $params.id exists"
			response.sendError SC_NOT_FOUND
		}
	}

	private User getLoggedInUser() {
		def userDetails = springSecurityService.principal
		userDetails ? User.get(userDetails.id) : null
	}

}
