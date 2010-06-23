import votething.auth.Role

class BootStrap {

     def init = { servletContext ->
		 def userRole = Role.findByAuthority(Role.USER)
		 if (!userRole) {
			 userRole = new Role(authority: Role.USER)
			 userRole.save(failOnError: true)
		 }
     }

     def destroy = {
     }

} 