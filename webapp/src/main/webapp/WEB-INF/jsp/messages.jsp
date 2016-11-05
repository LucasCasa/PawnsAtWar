<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="build" uri="building.tld"%>
<%@ taglib prefix="bi" uri="buildingIcon.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
<link rel="stylesheet" href="<c:url value= "/resources/css/building.css" /> ">


<div class="container">
    <h1>ESTA ES LA PAGINA DE MENSAJERIA</h1>

    <table class="table">
        <thead>
        <td><spring:message code="messages.user"/></td>
        <td><spring:message code="messages.description"/></td>
        </thead>
        <tbody>
        <c:forEach var="offer" items="${tradeList}">
            <tr>
                <td><br><br>${offer.getOwner().getName()}</td>
                <td><re:Resource rate="0" type="${offer.getOffer().getType()}" amount="${offer.getOffer().getQuantity()}" path="${pageContext.request.contextPath}"/></td>
                <td><re:Resource rate="0" type="${offer.getReceives().getType()}" amount="${offer.getReceives().getQuantity()}" path="${pageContext.request.contextPath}"/></td>
                <td><br><form method="post" action="<c:url value="/commerce/acceptTrade"/>">
                    <input type="hidden" name="id" value="${offer.getId()}"/>
                    <input type="submit" class="myButton" value="<spring:message code="commerce.accept"/>"/>
                </form></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>



<%@ include file="footer.jsp" %>
