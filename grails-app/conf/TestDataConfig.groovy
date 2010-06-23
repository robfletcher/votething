import org.codehaus.groovy.grails.commons.ApplicationHolder

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
			options = ["option 1", "option 2"]
		}
	}
}
