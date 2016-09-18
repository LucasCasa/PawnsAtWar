<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
<div class="container">
<div id="Map" class="carousel slide" style="display: inline-block">
<table style="max-width: 537,6px;">
<c:forEach var="row" items="${map}">
<tr>
	<c:forEach var="item" items="${row}">
		<td style="width:76px;height: 76px"><te:Terrain clas="img-responsive" id="${item.getType()}" /></td>
	</c:forEach>
</tr>
</c:forEach>
</table>
	<a class="left carousel-control" href="javascript:location.href= window.location.pathname+'?x=<c:out value="${x - 1}"/>&amp;y=<c:out value="${y}"/>'" role="button">
		<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		<span class="sr-only">Left</span>
	</a>
	<a class="up carousel-control" href="javascript:location.href= window.location.pathname+'?x=<c:out value="${x}"/>&amp;y=<c:out value="${y-1}"/>'" role="button">
		<span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
		<span class="sr-only">Up</span>
	</a>
	<a class="right carousel-control" href="javascript:location.href= window.location.pathname+'?x=<c:out value="${x + 1}"/>&amp;y=<c:out value="${y}"/>'" role="button">
		<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		<span class="sr-only">Right</span>
	</a>
	<a class="down carousel-control" href="javascript:location.href= window.location.pathname+'?x=<c:out value="${x}"/>&amp;y=<c:out value="${y+1}"/>'" role="button">
		<span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
		<span class="sr-only">Down</span>
	</a>
</div>
</div>
<%@ include file="footer.jsp" %>