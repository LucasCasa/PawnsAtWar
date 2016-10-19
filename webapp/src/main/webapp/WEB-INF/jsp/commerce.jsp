<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>

<div class="container">
	<%@ include file="resourceBar.jsp" %>
	<c:if test="${insuficientAmount}">
		<div class="error"><spring:message code="commerce.error"/></div>
	</c:if>
	<br/>
	<c:choose>
		<c:when test="${tradeListSize == 0}">
			<h2 style="text-align: center"><spring:message code="noOffers" /></h2>
		</c:when>
		<c:otherwise>
			<table class="table">
				<thead>
				<td><spring:message code="commerce.user"/></td>
				<td><spring:message code="commerce.offer"/></td>
				<td><spring:message code="commerce.receive"/></td>
				<td><spring:message code="commerce.accept"/></td>
				</thead>
				<tbody>
				<c:forEach var="offer" items="${tradeList}">
					<tr>
						<td>${offer.getOwner().getName()}</td>
						<td><re:Resource rate="0" type="${offer.getOffer().getType()}" amount="${offer.getOffer().getQuantity()}" path="${pageContext.request.contextPath}"/></td>
						<td><re:Resource rate="0" type="${offer.getReceives().getType()}" amount="${offer.getReceives().getQuantity()}" path="${pageContext.request.contextPath}"/></td>
						<td><form method="post" action="<c:url value="/commerce/acceptTrade"/>">
							<input type="hidden" name="id" value="${offer.getId()}"/>
							<input type="submit" class="myButton" value="<spring:message code="commerce.accept"/>"/>
						</form></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>

	<h1><spring:message code="commerce.myOffers"/></h1>
	<c:choose>
		<c:when test="${myTradesSize == 0}">
			<h2 style="text-align: center"><spring:message code="noMyOffers" /></h2>
		</c:when>
		<c:otherwise>
			<table class="table">
				<thead>
				<td><spring:message code="commerce.user"/></td>
				<td><spring:message code="commerce.offer"/></td>
				<td><spring:message code="commerce.receive"/></td>
				<td><spring:message code="commerce.accept"/></td>
				</thead>
				<tbody>
				<c:forEach var="offer" items="${myTrades}">
					<tr>
						<td>${offer.getOwner().getName()}</td>
						<td><re:Resource rate="0" type="${offer.getOffer().getType()}" amount="${offer.getOffer().getQuantity()}" path="${pageContext.request.contextPath}"/></td>
						<td><re:Resource rate="0" type="${offer.getReceives().getType()}" amount="${offer.getReceives().getQuantity()}" path="${pageContext.request.contextPath}"/></td>
						<td><form method="post" action="<c:url value="/commerce/delete"/>">
							<input type="hidden" name="id" value="${offer.getId()}"/>
							<input type="submit" class="myButton" value="<spring:message code="commerce.remove"/>"/>
						</form></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>

	<a href="<c:url value="/commerce/create"/>"><button class="myButton"><spring:message code="commerce.makeNewOffer"/></button></a>

</div>
<br/>
<%@ include file="footer.jsp" %>
