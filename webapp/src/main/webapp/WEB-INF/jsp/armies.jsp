<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="/WEB-INF/jsp/custom.tld"%>
<%@ taglib prefix="sprint" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>

<div class="container">
    <c:choose>
        <c:when test="${size == 0}">
            <h1 style="text-align: center"><spring:message code="noArmy" /></h1>
        </c:when>
        <c:otherwise>
            <table class="table table-striped">
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th><spring:message code="warrior" text="Disponible"/></th>
                    <th><spring:message code="archer" text="Disponible"/></th>
                    <th><spring:message code="horseman" text="Disponible"/></th>
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
                            <c:forEach var="troop" items="${army.troops}">
                                <c:if test="${troop.type == 0 && troop.quantity != 0}">
                                    <c:out value="${troop.quantity}" />
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="troop" items="${army.troops}">
                                <c:if test="${troop.type == 1 && troop.quantity != 0}">
                                    <c:out value="${troop.quantity}" />
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="troop" items="${army.troops}">
                                <c:if test="${troop.type == 2 && troop.quantity != 0}">
                                    <c:out value="${troop.quantity}" />
                                </c:if>
                            </c:forEach>
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
                            <c:if test="${army.available}">
                            <button class="myButton" onclick="javascript:location.href= window.location.pathname + '/<c:out value="${army.idArmy}?x=${x}&y=${y}" />' "><spring:message code="button.attack" /></button>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${army.available}">
                            <button class="myButton" onclick="javascript:location.href= window.location.pathname + '/<c:out value="${army.idArmy}/split" />' "><spring:message code="button.split" /></button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>


</div>

<%@ include file="footer.jsp" %>