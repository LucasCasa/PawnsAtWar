<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="/WEB-INF/jsp/custom.tld"%>
<%@ taglib prefix="us" uri="user.tld"%>

<%@ taglib prefix="sprint" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>

<div class="container">
    <table class="table" id="rank_table">
        <thead>
            <th><spring:message code="rankings.position"/></th>
            <th><spring:message code="rankings.player"/></th>
            <th><spring:message code="rankings.score"/></th>
        </thead>
        <tbody>
        <c:forEach var="player" items="${ranks}" varStatus="i">
            <tr>
                <td class="pos">${i.index + 1}</td>
                <td>
                    <us:User user="${player.user}" path="${pageContext.request.contextPath}"/>
                </td>
                <td>${player.score}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<br/>
<br/>

<%@ include file="footer.jsp" %>
