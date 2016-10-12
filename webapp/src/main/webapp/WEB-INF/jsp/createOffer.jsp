<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<%@ include file="resourceBar.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/createOffer.css" /> ">

<div class="container">
	<c:if test="${insuficientAmount}">
		<div class="error">No tienes suficientes Recursos</div>
	</c:if>
	<table class="table">
		<thead>
			<td>Ofrecer</td>
			<td>Recibir</td>
			<td>Crear</td>
		</thead>
		<tbody>
			<form method="post" action="<c:url value="/commerce/create/submit"/>">
				<tr>
					<td>
					<c:forEach var="res" items="${resList}">
						  <input type="radio" name="giveType" value="${res.getType()}"><re:Resource rate="0" type="${res.getType()}" amount=""/></input>
					</c:forEach>
					Cantidad:
					<input type="number" name="giveQty" min="1">
					<label>
					</td>
					<td>
					<c:forEach var="res" items="${resList}">
						  <input type="radio" name="getType" value="${res.getType()}"><re:Resource rate="0" type="${res.getType()}" amount=""/></input>
					</c:forEach>
					Cantidad:
					<input type="number" name="getQty" min="1">
					</td>
					<td>
						<input id="register" type="submit" class="myButton" value="Crear"/>
					</td>
				</tr>
			</form>
		</tbody>
	</table>
</div>

<%@ include file="footer.jsp" %>
<script src="<c:url value= "/resources/js/createOffer.js" />"></script>
