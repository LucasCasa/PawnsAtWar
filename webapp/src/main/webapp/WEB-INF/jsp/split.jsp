<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="re" uri="/WEB-INF/jsp/resource.tld"%>
<%@ include file="header.jsp" %>

<div class="container">
    <%@ include file="resourceBar.jsp" %>
    <form method="post" action="<c:url value="/split"/>">
    <table style="width: 100%">
        <tr>
    <c:forEach var="troop" items="${army.troops}" >
        <td style="text-align: center">
            <c:choose>
                <c:when test="${troop.type == 0}"><img src="<c:url value="/resources/images/warrior.png"/>"/></c:when>
                <c:when test="${troop.type == 1}"><img src="<c:url value="/resources/images/archer.png"/>"/></c:when>
                <c:when test="${troop.type == 2}"><img src="<c:url value="/resources/images/horseman.png"/>"/></c:when>
                <c:otherwise>undefined</c:otherwise>
            </c:choose>
        </td>
    </c:forEach>
        </tr>
        <tr>
            <c:forEach var="troop" items="${army.troops}" >
                <td>
                    <h1 style=" background-color: rgba(0,0,0,0.7); color: #FF0;margin-left:40%;margin-right:40%;text-align: center"><c:out value="${troop.quantity}"/></h1>
                </td>
            </c:forEach>
        </tr>
        <tr>
            <c:forEach var="troop" items="${army.troops}" >
                <td style="text-align: center">
                    <input type="number" min="0" max="${troop.quantity}" value="0" name="${troop.type}" >
                </td>
            </c:forEach>
        </tr>
    </table>
    <br><br>
        <div class="row">
        <div class="col-md-4 col-md-offset-4 col-xs-12 col-sm-8 col-sm-offset-2">
        <table  class="table table-striped">
            <tr>
                <th></th>
                <th>X</th>
                <th>Y</th>
            </tr>
            <c:forEach var="position" items="${possiblePoints}">
                <tr>
                    <td style="width: 18px">
                        <div class="radio">
                            <label><input type="radio" name="pos" value="${position.x},${position.y}"></label>
                        </div>
                    </td>
                    <td>
                        <c:out value="${position.x}"/>
                    </td>
                    <td>
                        <c:out value="${position.y}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </div>
        </div>
        <div class="row" style="text-align: center">
        <input type="hidden" name="armyId" value="${army.idArmy}">
        <input  type="submit" class="myButton" value="<spring:message code="button.split"/>" />
        </div>
    </form>

</div>
<%@ include file="footer.jsp" %>