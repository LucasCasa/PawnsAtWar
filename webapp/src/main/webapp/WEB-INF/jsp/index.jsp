<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">

<div id="myCarousel" class="carousel slide" data-ride="carousel" style="display: inline-block">
<table style="max-width: 896px;">
<c:forEach var="row" items="${map}">
<tr>
	<c:forEach var="item" items="${row}">
		<td><te:Terrain clas="img-responsive" id="${item.getType()}" /></td>
	</c:forEach>
</tr>
</c:forEach>
</table>
	<a class="left carousel-control" href="#myCarousel" role="button">
		<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		<span class="sr-only">Left</span>
	</a>
	<a class="up carousel-control" href="#myCarousel" role="button">
		<span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
		<span class="sr-only">Up</span>
	</a>
	<a class="right carousel-control" href="#myCarousel" role="button">
		<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		<span class="sr-only">Right</span>
	</a>
	<a class="down carousel-control" href="#myCarousel" role="button">
		<span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
		<span class="sr-only">Down</span>
	</a>
</div>

<%@ include file="footer.jsp" %>