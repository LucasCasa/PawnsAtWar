<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/css?family=Cinzel:400,700" rel="stylesheet">
	<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
</head>

<body style="background-image:url(/webapp/resources/images/back2.jpg);">
<br><br>
<div class="container">
		<h2 class="pawtitle"><spring:message code="welcome.message"/></h2>
		<div class="row">
			<div class="col-md-6">
				<h2><spring:message code="user.login"/></h2>
				<c:url value="/authenticate" var="postPath"/>
				<form:form modelAttribute="loginForm" cssClass="form-horizontal" action="${postPath}" method="post">
					<div class="form-group">
						<form:label cssClass="col-md-3" path="username">
							<spring:message code="user.username"/>
						</form:label>
						<form:input cssClass="col-md-4" type="text" path="username"/>
						<form:errors path="username" cssClass="formError" element="label cssClass=col-md-2 style=color:red"/>
					</div>
					<div class="form-group">
						<form:label cssClass="col-md-3" path="password">
							<spring:message code="user.password"/>
						</form:label>
						<form:input cssClass="col-md-4" type="password" path="password"/>
						<form:errors path="password" cssClass="formError" element="label cssClass=col-md-2 style=color:red"/>
					</div>
					<div>
						<input type="submit" value="<spring:message code="button.login"/>"/>
					</div>
				</form:form>
			</div>


			<div class="col-md-6">
				<h2><spring:message code="user.signup"/></h2>
				<c:url value="/create" var="postPath"/>
				<form:form modelAttribute="registerForm" cssClass="form-horizontal" action="${postPath}" method="post">

					<div class="form-group">
						<form:label cssClass="col-md-4" path="username">
							<spring:message code="user.username"/>
						</form:label>
						<form:input cssClass="col-md-6" type="text" path="username"/>
						<form:errors path="username" cssClass="has-error" element="label cssClass=col-md-3 style=color:red"/>
					</div >
					<div class="form-group">
						<form:label cssClass="col-md-4" path="password">
						<spring:message code="user.password"/>
						</form:label>
						<form:input cssClass="col-md-6" type="password" path="password"/>
						<form:errors path="password" cssClass="has-error" element="label cssClass=col-md-2 style=color:red"/>
					</div>
					<div class="form-group">
						<form:label cssClass="col-md-4" path="repeatPassword">
						<spring:message code="user.repPassword"/> 
						</form:label>
						<form:input cssClass="col-md-6" type="password" path="repeatPassword"/>
						<form:errors path="repeatPassword" cssClass="has-error" element="label cssClass=col-md-2 style=color:red"/>
					</div>
					<div class="form-group">
						<form:label cssClass="col-md-4" path="email">
						<spring:message code="user.email"/> 
						</form:label>
						<form:input cssClass="col-md-6" type="email" path="email"/>
						<form:errors path="email" cssClass="has-error" element="label cssClass=col-md-2 style=color:red"/>
					</div>
					<div class="form-group">
						<input type="submit" value="<spring:message code="button.register"/>"/>
					</div>


				</form:form>
			</div>
		</div>
<%@ include file="footer.jsp" %>