<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title><g:message code="login.label" default="Login"/></title>
	</head>
	<body>
		<section class="main">
			<form action="${postUrl}" method="POST" id="loginForm" autocomplete="off">
				<fieldset>
					<ol>
						<li>
							<label for="username"><g:message code="label.username" default="Username"/></label>
							<input type="text" name="j_username" id="username" autofocus />
						</li>
						<li>
							<label for="password"><g:message code="label.password" default="Password"/></label>
							<input type="password" name="j_password" id="password"/>
						</li>
						<li>
							<label for="remember_me"><g:message code="label.remember.me" default="Remember me"/></label>
							<input type="checkbox" name="${rememberMeParameter}" id="remember_me" <g:if test="${hasCookie}">checked="checked"</g:if>/>
						</li>
					</ol>
				</fieldset>
				<fieldset class="buttons">
					<input type="submit" value="${message(code: 'button.label.login', default: 'Login')}"/>
				</fieldset>
			</form>
		</section>
		<g:javascript>
			if (!Modernizr.input.autofocus) $("input#username").focus();
		</g:javascript>
	</body>
</html>