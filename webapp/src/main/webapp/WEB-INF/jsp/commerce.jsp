<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<%@ include file="resourceBar.jsp" %>

<div class="container">
	<c:if test="${insuficientAmount}">
		<div class="error">No tienes suficientes Recursos</div>
	</c:if>

	<table class="table">
		<thead>
			<td>Usuario</td>
			<td>Ofrece</td>
			<td>Recibe</td>
			<td>Aceptar</td>
		</thead>
		<tbody>
			<c:forEach var="offer" items="${tradeList}">
				<tr>
					<td>${offer.getOwner().getName()}</td>
					<td><re:Resource rate="0" type="${offer.getOffer().getType()}" amount="${offer.getOffer().getQuantity()}"/></td>
					<td><re:Resource rate="0" type="${offer.getReceives().getType()}" amount="${offer.getReceives().getQuantity()}"/></td>
					<td><form method="post" action="<c:url value="/commerce/acceptTrade"/>">
	                    <input type="hidden" name="id" value="${offer.getId()}"/>
	                    <input type="submit" class="myButton" value="Aceptar"/>
                    </form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h1>Mis Ofertas</h1>
	<table class="table">
		<thead>
			<td>Usuario</td>
			<td>Ofrece</td>
			<td>Recibe</td>
			<td>Remover</td>
		</thead>
		<tbody>
			<c:forEach var="offer" items="${myTrades}">
				<tr>
					<td>${offer.getOwner().getName()}</td>
					<td><re:Resource rate="0" type="${offer.getOffer().getType()}" amount="${offer.getOffer().getQuantity()}"/></td>
					<td><re:Resource rate="0" type="${offer.getReceives().getType()}" amount="${offer.getReceives().getQuantity()}"/></td>
					<td><form method="post" action="<c:url value="/commerce/delete"/>">
	                    <input type="hidden" name="id" value="${offer.getId()}"/>
	                    <input type="submit" class="myButton" value="Remover"/>
                    </form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="<c:url value="/commerce/create"/>"><button class="myButton">Realizar nueva Oferta</button></a>

</div>

<%@ include file="footer.jsp" %>
