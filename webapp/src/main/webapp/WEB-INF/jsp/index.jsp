<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>




<div class="container">
	<div class="resBar">
		<table>
			<tbody>
				<tr>
					<c:forEach var="res" items="${resList}">
					<td>
						<re:Resource type="${res.getType()}" amount="${res.getQuantity()}"/>
					</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="form-inline">
		<div class="form-group">
			<label for="Xval">X:</label>
			<input type="text" class="form-control" id="Xval" size="3">
		</div>
		<div class="form-group">
			<label for="Yval">Y:</label>
			<input type="text" class="form-control" id="Yval" size="3">

		</div>
		<button class="myButton" id="gotoDir" onclick="redir()">IR


		</button>

	</div>
	<br>

</div>

<div>
	<div class="row">
		<div class="especial"></div>
		<div class="col-md-9">
			<div id="Map" class="carousel slide" style="float:left;display: inline-block">

				<table style="max-width: 537,6px;">
					<c:forEach var="row" items="${map}">
						<tr>
							<c:forEach var="item"  items="${row}">
								<td style="width:76px;height: 76px" onmouseover="describe()" onmouseleave="deleteDescription()"><a href="<c:url value="/building?x=${item.getPosition().getX()}&y=${item.getPosition().getY()}" />" ><te:Terrain clas="img-responsive" id="${item.getType()}" /></a></td>
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

			<div id="elemDescription" class="col-md-4">

			</div>

		</div>

	</div>

<h1>Tu User es: ${user.getId()}</h1>


</div>
<%@ include file="footer.jsp" %>
