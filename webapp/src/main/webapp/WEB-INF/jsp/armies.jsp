<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ include file="header.jsp" %>

<div class="container">
    <table class="table table-striped">
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>Disponible</th>
            <th>Dirigir</th>
        </tr>
        <c:forEach var="army" items="${armies}">
        <tr>
           <td>
                <c:out value="${army.getPosition().getX()}"/>
           </td>
            <td>
                <c:out value="${army.getPosition().getY()}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${army.isAvailable()}">
                        <c:out value="Si"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="No"/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <button class="myButton" onclick="javascript:location.href= window.location.pathname + '/<c:out value="${army.getIdArmy()}" />' ">+ Info</button>
            </td>
        </tr>
            </c:forEach>
    </table>

</div>

<%@ include file="footer.jsp" %>