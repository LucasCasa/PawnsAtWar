<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ include file="header.jsp" %>

<table>
	<tr>
	<div class="btn-group btn-group-justified" role="group" aria-label="...">
		<div class="btn-group" role="group">
			<button type="button" class="btn btn-default">UP</button>
		</div>
	</div>
	</tr>
<c:forEach var="row" items="${map}">
<tr>
	<c:forEach var="item" items="${row}">
		<td><te:Terrain clas="img-responsive" id="${item}" /></td>
	</c:forEach>
</tr>
</c:forEach>
</table>

<c:out value="TOMA PUTO" />

<%@ include file="footer.jsp" %>