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
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${voteInstance}">
				<div class="errors">
					<g:renderErrors bean="${voteInstance}" as="list"/>
				</div>
			</g:hasErrors>
			<poll:userHasNotVoted poll="${pollInstance}">
				<g:form controller="poll" action="vote" name="vote">
					<g:hiddenField name="id" value="${pollInstance.id}"/>
					<fieldset class="options">
						<legend><g:message code="poll.options.label" default="Choose:"/></legend>
						<ol id="poll-vote">
							<g:each var="option" in="${pollInstance.options}" status="i">
								<li>
									<label><g:radio value="${i}" name="option" id="option-${i}"/>${option}</label>
								</li>
							</g:each>
						</ol>
					</fieldset>
					<fieldset class="buttons">
						<g:submitButton name="submitVote" value="${message(code: 'button.vote.label', default: 'Vote')}"/>
					</fieldset>
				</g:form>
			</poll:userHasNotVoted>
			<poll:userHasVoted poll="${pollInstance}">
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
			</poll:userHasVoted>
		</section>
	</body>
</html>
