<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<div class="container">
<h2>Login</h2>
	<c:url value="/authenticate" var="postPath"/>
	<form:form modelAttribute="loginForm" action="${postPath}" method="post">
    <div>
        <form:label path="username">Username: </form:label>
        <form:input type="text" path="username"/>
        <form:errors path="username" cssClass="formError" element="p"/>
	</div> 
	<div>
		<form:label path="password">Password: </form:label>
		<form:input type="password" path="password"/>
        <form:errors path="password" cssClass="formError" element="p"/>
	</div> 
	<div>
		<input type="submit" value="Login"/>
	</div>
	</form:form>
</div>
<%@ include file="footer.jsp" %>