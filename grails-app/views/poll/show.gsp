<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<g:set var="entityName" value="${message(code: 'poll.label', default: 'Poll')}"/>
		<title><g:message code="poll.show.label" args="[pollInstance.title]" default="{0}"/></title>
	</head>
	<body>
		<div class="body">
			<h1><g:message code="poll.show.label" args="[pollInstance.title]" default="{0}"/></h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:form controller="poll" action="vote" useToken="true">
				<g:hiddenField name="id" value="${pollInstance.id}"/>
				<fieldset class="options">
					<legend><g:message code="default.options.label" default="Choose:"/></legend>
					<h2>${pollInstance.options}</h2>
					<ol>
						<g:each var="option" in="${pollInstance.options}" status="i">
							<li>
								<label><g:radio value="${i}" name="option" id="option-${i}"/>${option}</label>
							</li>
						</g:each>
					</ol>
					<g:submitButton name="submitVote" value="${message(code: 'button.vote.label', default: 'Vote')}"/>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
