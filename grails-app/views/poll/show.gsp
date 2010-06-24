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
			<fieldset class="options">
				<legend><g:message code="default.options.label" default="Choose:"/></legend>
				<h2>${pollInstance.options}</h2>
				<ol>
					<g:each var="option" in="${pollInstance.options}" status="i">
						<li>
							<label><g:radio value="${i}" name="option"/>${option}</label>
						</li>
					</g:each>
				</ol>
			</fieldset>
		</div>
	</body>
</html>
