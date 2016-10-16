<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<div class="container">
	<div class="container">
		<h2 >Bienvenido a Pawns at War</h2>
		<div class="row">
			<div class="col-md-6">
				<h2>Inicia Sesion</h2>

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


			<div class="col-md-6">
				<h2>Eres nuevo? Registrate!</h2>
				<c:url value="/create" var="postPath"/>
				<form:form modelAttribute="registerForm" action="${postPath}" method="post">

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
						<form:label path="repeatPassword">Repeat password: </form:label>
						<form:input type="password" path="repeatPassword"/>
						<form:errors path="repeatPassword" cssClass="formError" element="p"/>
					</div>
					<div>
						<form:label path="email">Email: </form:label>
						<form:input type="email" path="email"/>
						<form:errors path="email" cssClass="formError" element="p"/>
					</div>
					<div>
						<input type="submit" value="Register!!"/>
					</div>


				</form:form>
			</div>
		</div>


	</div>
<%@ include file="footer.jsp" %>