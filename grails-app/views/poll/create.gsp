<!DOCTYPE html>
<bean:requiredIndicator> required</bean:requiredIndicator>
<bean:inputTemplate>${label}${field}<g:if test="${errors}">${errors}</g:if></bean:inputTemplate>
<bean:labelTemplate><label for="${fieldId}" class="${errorClassToUse}${required}">${label}</label></bean:labelTemplate>
<bean:errorTemplate><span class="errorMessage">${message.encodeAsHTML()}</span></bean:errorTemplate>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<g:set var="entityName" value="${message(code: 'poll.label', default: 'Poll')}"/>
		<title><g:message code="default.create.label" args="[entityName]"/></title>
	</head>
	<body>
		<section class="main">
			<g:form action="save" method="post">
				<bean:withBean beanName="pollInstance">
					<fieldset>
						<legend><g:message code="createPoll.properties.label" default="Poll"/></legend>
						<ol>
							<li><bean:field property="title"/></li>
						</ol>
					</fieldset>
					<fieldset id="options">
						<legend><g:message code="createPoll.options.label" default="Options"/></legend>
						<ol>
							<g:set var="range" value="${pollInstance.options ? pollInstance.optionRange : 0..<2}"/>
							<g:each var="i" in="${range}">
								<li>
									<bean:field property="options" id="options_${i}" label="" showErrors="false" value="${pollInstance.options[i] ?: ''}"/>
									<a class="removeOption" href="#"><g:message code="button.option.remove.label" default="Remove"/></a>
								</li>
							</g:each>
						</ol>
						<a class="addOption" href="#"><g:message code="button.option.add.label" default="Add"/></a>
						<g:hasErrors bean="${pollInstance}" field="options">
							<g:eachError bean="${pollInstance}" field="options">
								<span class="errorMessage"><g:message error="${it}"/></span>
							</g:eachError>
						</g:hasErrors>
					</fieldset>
				</bean:withBean>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
				</fieldset>
			</g:form>
		</section>
	</body>
</html>
