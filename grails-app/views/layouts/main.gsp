<!DOCTYPE html>
<html>
	<head>
		<title><g:layoutTitle default="Grails"/></title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
		<g:layoutHead/>
		<g:javascript library="application"/>
	</head>
	<body>
		<div id="spinner" class="spinner" style="display:none;">
			<img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/>
		</div>
		<div id="grailsLogo" class="logo"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails" border="0"/></a></div>
		<div id="login">
			<sec:ifLoggedIn>
				<span class="message"><g:message code="welcome.message" args="[sec.username()]" default="Welcome back {0}"/></span>
				<g:link controller="logout"><g:message code="link.logout" default="Log out"/></g:link>
			</sec:ifLoggedIn>
			<sec:ifNotLoggedIn>
				<g:link controller="login"><g:message code="link.login" default="Log in here"/></g:link>
			</sec:ifNotLoggedIn>
		</div>
		<g:layoutBody/>
	</body>
</html>