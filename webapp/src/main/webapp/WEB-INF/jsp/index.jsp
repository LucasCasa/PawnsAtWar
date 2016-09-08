<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="header.jsp" %>
<table>
<c:forEach var="row" items="${map}">
<tr>
	<c:forEach var="item" items="${row}">
	<!-- ESTO ES MAS ASQUEROSO TODAVIA -->
	<c:if test="${item == 1}">
		<td><img src="resources/images/castle.png"></td>
	</c:if>
	<c:if test="${item == 0}">
		<td><img src="resources/images/terrain.png"></td>
	</c:if>
	</c:forEach>
</tr>
</c:forEach>
</table>
<c:out value="TOMA PUTO" /> 

<%@ include file="footer.jsp" %>