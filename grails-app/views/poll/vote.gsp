<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<g:set var="entityName" value="${message(code: 'poll.label', default: 'Poll')}"/>
		<title><g:message code="poll.show.label" args="[pollInstance.title]" default="{0}"/></title>
	</head>
	<body>
		<section class="main">
			<g:form controller="poll" action="vote" name="vote">
				<g:hiddenField name="id" value="${pollInstance.id}"/>
				<fieldset class="options">
					<legend><g:message code="poll.options.label" default="Choose:"/></legend>
					<g:hasErrors bean="${voteInstance}">
						<div class="errors">
							<g:renderErrors bean="${voteInstance}" as="list"/>
						</div>
					</g:hasErrors>
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
		</section>
	</body>
</html>
