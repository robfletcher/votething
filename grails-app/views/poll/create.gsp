<%@ page import="votething.poll.Poll" %>
<bean:requiredIndicator> required</bean:requiredIndicator>
<bean:inputTemplate><li>${label}${field}<g:if test="${errors}"><span class="errorMessage">${errors}</span></g:if></li></bean:inputTemplate>
<bean:labelTemplate><label for="${fieldId}" class="${errorClassToUse}${required}">${label}</label></bean:labelTemplate>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<g:set var="entityName" value="${message(code: 'poll.label', default: 'Poll')}"/>
		<title><g:message code="default.create.label" args="[entityName]"/></title>
	</head>
	<body>
		<div class="nav">
			<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
		</div>
		<div class="body">
			<h1><g:message code="default.create.label" args="[entityName]"/></h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${pollInstance}">
				<div class="errors">
					<g:renderErrors bean="${pollInstance}" as="list"/>
				</div>
			</g:hasErrors>
			<g:form action="save" method="post">
				<fieldset>
					<bean:withBean beanName="pollInstance">
						<ol>
							<bean:field property="title"/>
							<g:set var="range" value="${pollInstance.options ? pollInstance.optionRange : 0..<2}"/>
							<g:each var="i" in="${range}">
								<bean:field property="options[$i]"/>
							</g:each>
						</ol>
					</bean:withBean>
				</fieldset>
				<div class="buttons">
					<span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
				</div>
			</g:form>
		</div>
	</body>
</html>
