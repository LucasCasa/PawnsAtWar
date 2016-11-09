<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="/WEB-INF/jsp/custom.tld"%>
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
        <c:forEach var="player" items="${players}" varStatus="i">
            <tr>
                <td class="pos"></td>
                <td><a href="<c:url value="/map?x=${castles.get(i.index).x}&y=${castles.get(i.index).y}"/>">${player.getName()}</a></td>
                <td>${scores.get(i.index)}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<br/>
<br/>

<%@ include file="footer.jsp" %>
<script src="<c:url value= "/resources/js/ranking.js" />"></script>