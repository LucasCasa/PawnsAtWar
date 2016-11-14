<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ include file="header.jsp" %>

<div class="container">
    <table style="width: 100%">
        <tr>
    <c:forEach var="troop" items="${troops}" >
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
    <c:forEach var="troop" items="${troops}" >
        <td>
        <h1 class="troop-size"><c:out value="${troop.quantity}"/></h1>
        </td>
    </c:forEach>
        </tr>
    </table>
<c:choose>
    <c:when test="${army.available}">
    <h1 style="text-align: center;"><b><spring:message code="button.attack"/></b></h1><br>
    <form style="text-align: center" method="post" action="<c:url value="/attack"/>">
    <div class="form-inline">
        <div class="form-group">
            <label for="Xval">X:</label>
            <input type="text" class="form-control" name="x" id="Xval" size="3" value="${x}">
        </div>
        <div class="form-group">
            <label for="Yval">Y:</label>
            <input type="text" class="form-control" name="y" id="Yval" size="3" value="${y}">
        </div>
        <input type="hidden" name="armyId" value="${army.idArmy}">
        <input type="submit" class="myButton" value="<spring:message code="button.attack"/>"/>
    </div>
    </form>
        <c:if test="${armySize > 1}">
    <h1 style="text-align: center;"><b><spring:message code="merge" /></b></h1><br>
    <table class="table table-striped">
        <tr>
            <th>X</th>
            <th>Y</th>
            <th><spring:message code="merge" /></th>
        </tr>
        <c:forEach var="other" items="${armies}">
            <c:if test="${other.available && other.idArmy != army.idArmy}">
            <tr>
                <td>
                    <c:out value="${other.position.x}"/>
                </td>
                <td>
                    <c:out value="${other.position.y}"/>
                </td>
                <td>
                    <a href="<c:url value="/merge?f=${army.idArmy}&t=${other.idArmy}"/>"><button class="myButton"><spring:message code="merge" /></button></a>
                </td>
            </tr>
            </c:if>
        </c:forEach>
        </c:if>
    </table>


    </c:when>
    <c:otherwise>
        <h1 style="text-align: center">Ejercito Ocupado</h1>
    </c:otherwise>
</c:choose>
</div>
<%@ include file="footer.jsp" %>