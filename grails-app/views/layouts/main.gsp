<!DOCTYPE html>
<html class="no-js">
	<head>
		<title><g:layoutTitle default="Grails"/></title>
		<meta charset="UTF-8"/>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'votething.css')}"/>
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
		<g:layoutHead/>
		<!--[if IE]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<g:javascript library="jquery"/>
		<g:javascript src="modernizr/modernizr-1.5.min.js"/>
		<g:javascript library="application"/>
	</head>
	<body>
		<article>
			<header>
				<nav>
					<a href="${createLink(uri: '/')}" class="logo" title="${message(code: 'default.home.label')}">
						<img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails">
					</a>
					<sec:ifLoggedIn>
						<span class="loggedInMessage"><g:message code="welcome.message" args="[sec.username()]" default="Welcome back {0}"/></span>
						<link:createPoll><g:message code="poll.create.label" default="Create new poll"/></link:createPoll>
						<link:logout><g:message code="link.logout" default="Log out"/></link:logout>
					</sec:ifLoggedIn>
					<sec:ifNotLoggedIn>
						<link:login><g:message code="link.login" default="Log in here"/></link:login>
					</sec:ifNotLoggedIn>
				</nav>
				<h1><g:layoutTitle/></h1>
			</header>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:layoutBody/>
		</article>
	</body>
</html>