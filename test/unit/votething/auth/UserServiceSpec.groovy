package votething.auth

import grails.plugin.spock.UnitSpec
import grails.plugins.springsecurity.SpringSecurityService

class UserServiceSpec extends UnitSpec {

	UserService service = new UserService()

	def setup() {
		service.springSecurityService = Mock(SpringSecurityService)
	}

    def "The current user can be retrieved"() {
		given: "a user"
		def user = new User()
		mockDomain User, [user]

		and: "the user is logged in"
		service.springSecurityService.principal >> [id: user.id]

		expect: "the service returns the logged in user"
		service.currentUser == user
    }

	def "There may be no current user"() {
		given: "there is no logged in user"
		service.springSecurityService.principal >> null

		expect: "the service returns null"
		service.currentUser == null
	}

}
