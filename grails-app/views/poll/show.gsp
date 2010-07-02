<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<g:set var="entityName" value="${message(code: 'poll.label', default: 'Poll')}"/>
		<title><g:message code="poll.show.label" args="[pollInstance.title]" default="{0}"/></title>
	</head>
	<body>
		<section class="main">
			<h2><g:message code="poll.results.label" default="Results"/></h2>
			<ol id="poll-result">
				<poll:eachOption poll="${pollInstance}">
					<li style="width: ${pct}%">
						<g:set var="tooltip"><g:message code="poll.votes.label" args="[votes]" default="{0} votes"/></g:set>
						<span title="${tooltip}" class="option">${option}</span>
						<span class="votes">(${tooltip})</span>
					</li>
				</poll:eachOption>
			</ol>
		</section>
	</body>
</html>
