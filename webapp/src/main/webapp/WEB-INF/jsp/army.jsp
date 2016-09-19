<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ include file="header.jsp" %>

<div class="container">
    <table style="width: 100%">
        <tr>
    <c:forEach var="troop" items="${troops}" >
        <td style="text-align: center">
        <c:choose>
            <c:when test="${troop.getType() == 0}"><img src="<c:url value="/resources/images/warrior.png"/>"/></c:when>
            <c:when test="${troop.getType() == 1}"><img src="<c:url value="/resources/images/archer.png"/>"/></c:when>
            <c:when test="${troop.getType() == 2}"><img src="<c:url value="/resources/images/horseman.png"/>"/></c:when>
            <c:otherwise>undefined</c:otherwise>
        </c:choose>
        </td>
    </c:forEach>
        </tr>
        <tr>
    <c:forEach var="troop" items="${troops}" >
        <td>
        <h1 style=" background-color: rgba(0,0,0,0.7); color: #FF0;margin-left:40%;margin-right:40%;text-align: center"><c:out value="${troop.getQuantity()}"/></h1>
        </td>
    </c:forEach>
        </tr>
    </table>
</div>

<%@ include file="footer.jsp" %>