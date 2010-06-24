import org.codehaus.groovy.grails.commons.ApplicationHolder
import votething.auth.User

environments {
	production {
		testDataConfig {
			enabled = false
		}
	}
}

testDataConfig {
	sampleData {
		"votething.auth.User" {
			password = {-> ApplicationHolder.application.mainContext.springSecurityService.encodePassword("password") }
			enabled = true
			accountExpired = false
			accountLocked = false
			passwordExpired = false
		}
		"votething.poll.Poll" {
			creator = {-> User.buildLazy(username: "blackbeard") }
			options = {-> (1..5).collect { "Option $it".toString() } }
		}
	}
}
