<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="/WEB-INF/jsp/custom.tld"%>
<%@ taglib prefix="sprint" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>

<div class="container">
    <table class="table table-striped">
        <tr>
            <th>X</th>
            <th>Y</th>
            <th><spring:message code="table.available" text="Disponible"/> </th>
            <th colspan="2"><spring:message code="table.manage"/></th>
        </tr>
        <c:forEach var="army" items="${armies}">
        <tr>
           <td>
                <c:out value="${army.position.x}"/>
           </td>
            <td>
                <c:out value="${army.position.y}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${army.available}">
                        <spring:message code="yes"/>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="no"/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <button class="myButton" onclick="javascript:location.href= window.location.pathname + '/<c:out value="${army.idArmy}?x=${x}&y=${y}" />' "><spring:message code="button.attack" /></button>
            </td>
            <td>
                <button class="myButton" onclick="javascript:location.href= window.location.pathname + '/<c:out value="${army.idArmy}/split" />' "><spring:message code="button.split" /></button>
            </td>
        </tr>
            </c:forEach>
    </table>

</div>

<%@ include file="footer.jsp" %>