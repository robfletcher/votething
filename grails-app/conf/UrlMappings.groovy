class UrlMappings {
	static mappings = {

		name createPoll: "/poll/create" {
			controller = "poll"
			action = "create"
		}

		name showPoll: "/poll/show/$id" {
			controller = "poll"
			action = "show"
		}

		name login: "/login" {
			controller = "login"
		}

		name logout: "/logout" {
			controller = "logout"
		}

		"/$controller/$action?/$id?" {
			constraints {
				// apply constraints here
			}
		}

		"/"(view: "/index")

		"500"(view: '/error')
	}
}
