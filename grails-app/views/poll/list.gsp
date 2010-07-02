<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<g:set var="entityName" value="${message(code: 'poll.label', default: 'Poll')}"/>
		<title><g:message code="default.list.label" args="[entityName]"/></title>
	</head>
	<body>
		<section class="main">
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="title" title="${message(code: 'poll.title.label', default: 'Title')}"/>
						<g:sortableColumn property="dateCreated" title="${message(code: 'poll.dateCreated.label', default: 'Date Created')}"/>
						<th><g:message code="poll.creator.label" default="Creator"/></th>
						<g:sortableColumn property="voteCount" title="${message(code: 'poll.voteCount.label', default: 'Votes')}"/>
					</tr>
				</thead>
				<tbody>
					<g:each in="${pollInstanceList}" status="i" var="pollInstance">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td><link:showPoll id="${pollInstance.id}">${fieldValue(bean: pollInstance, field: "title")}</link:showPoll></td>
							<td>${fieldValue(bean: pollInstance, field: "dateCreated")}</td>
							<td>${fieldValue(bean: pollInstance, field: "creator")}</td>
							<td>${fieldValue(bean: pollInstance, field: "voteCount")}</td>
						</tr>
					</g:each>
				</tbody>
			</table>
			<div class="paginateButtons">
				<g:paginate total="${pollInstanceTotal}"/>
			</div>
		</section>
	</body>
</html>
