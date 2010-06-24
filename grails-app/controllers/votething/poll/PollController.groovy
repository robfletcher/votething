package votething.poll

import static javax.servlet.http.HttpServletResponse.*

class PollController {

	def show = {
		def pollInstance = params.uri ? Poll.findByUri(params.uri) : null
		if (!pollInstance) {
			response.sendError SC_NOT_FOUND
		} else {
			[pollInstance: pollInstance]
		}
	}

}
