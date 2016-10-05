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
<c:choose>
    <c:when test="${army.isAvailable()}">
    <h1 style="text-align: center;"><b>Atacar</b></h1><br>
<form style="text-align: center" method="post" action="<c:url value="/attack"/>">
<div class="form-inline">
    <div class="form-group">
        <label for="Xval">X:</label>
        <input type="text" class="form-control" name="x" id="Xval" size="3">
    </div>
    <div class="form-group">
        <label for="Yval">Y:</label>
        <input type="text" class="form-control" name="y" id="Yval" size="3">
    </div>
    <input type="submit" class="myButton" value="Atacar"/>
</div>
</form>
    </c:when>
    <c:otherwise>
        <h1 style="text-align: center">Ejercito Ocupado</h1>
    </c:otherwise>
</c:choose>
<%@ include file="footer.jsp" %>