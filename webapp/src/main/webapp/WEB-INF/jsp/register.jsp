  <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
	<div class="container">
	<h2>Registrar</h2>
	<c:url value="/create" var="postPath"/>
	<form:form modelAttribute="registerForm" action="${postPath}" method="post">

    <div>
        <form:label path="username">Usuario: </form:label>
        <form:input type="text" path="username"/>
        <form:errors path="username" cssClass="formError" element="p"/>
	</div> 
	<div>
		<form:label path="password">Contrasena: </form:label>
		<form:input type="password" path="password"/>
        <form:errors path="password" cssClass="formError" element="p"/>
	</div> 
	<div>
        <form:label path="repeatPassword">Repetir contrasena: </form:label>
        <form:input type="password" path="repeatPassword"/>
		<form:errors path="repeatPassword" cssClass="formError" element="p"/>
	</div>
	<div>
		<form:label path="email">Email: </form:label>
		<form:input type="email" path="email"/>
        <form:errors path="email" cssClass="formError" element="p"/>
	</div> 
	<div>
		<input type="submit" value="Registrarse!!"/>
	</div>
	<div>
		<a href="<c:url value="/login"/>">Posee cuenta? Ingrese</a>
	</div>
</form:form>
</div>
<%@ include file="footer.jsp" %>