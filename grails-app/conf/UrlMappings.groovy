class UrlMappings {
	static mappings = {

		"/poll/$uri" {
			controller = "poll"
			action = "show"
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
