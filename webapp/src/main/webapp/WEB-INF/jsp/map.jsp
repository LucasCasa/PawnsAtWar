<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="/WEB-INF/jsp/custom.tld"%>
<%@ taglib prefix="re" uri="/WEB-INF/jsp/resource.tld"%>
<%@ include file="header.jsp" %>




<div class="container">

	<div>
		<br>
		<div class="row">
			<div class="especial"></div>
			<div class="col-md-8 col-sm-9">
				<div id="Map" range="${range}" class="carousel slide" style="display: inline-block">

					<table style="max-width: 537,6px;">
						<tr style="background-color:#000;text-align: center;color: #FFF;">
							<td></td>
							<c:forEach var="i" items="${map}" varStatus="loop">
							<td><c:out value="${map.get(0).get(0).getPosition().getX() + loop.index}"/></td>
							</c:forEach>
						</tr>
						<c:forEach var="row" items="${map}">
							<tr>
								<td style="background-color:#000;text-align: center;color: #FFF;"><c:out value="${row.get(0).getPosition().getY()}"/></td>
								<c:forEach var="item" items="${row}">
								<td style="width:76px;height: 76px" onmouseover="describe()" onmouseleave="deleteDescription()"><a href="<c:url value="/building?x=${item.getPosition().getX()}&y=${item.getPosition().getY()}" />" ><te:Terrain clas="img-responsive" userid="${user.getId()}" ownerid="${item.getUser().getId()}" id="${item.getType()}" path="${pageContext.request.contextPath}" /></a></td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>

					<a style="left:-15px;" class="left carousel-control" href="javascript:move(<c:out value="${x-1}"/>,<c:out value="${y}"/>);" role="button">
					<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Left</span>
					</a>
					<a style="top:-15px;" class="up carousel-control" href="javascript:move(<c:out value="${x}"/>,<c:out value="${y-1}"/>);" role="button">
					<span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
					<span class="sr-only">Up</span>
					</a>
					<a style="right:-15px;"class="right carousel-control" href="javascript:move(<c:out value="${x+1}"/>,<c:out value="${y}"/>);" role="button">
					<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Right</span>
					</a>
					<a style="bottom:-15px;" class="down carousel-control" href="javascript:move(<c:out value="${x}"/>,<c:out value="${y+1}"/>);" role="button">
					<span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
					<span class="sr-only">Down</span>
					</a>
				</div>
			</div>
			<div class="col-md-4">
				<%@ include file="resourceBar.jsp" %>
				<br>
				<div class="form-inline" >
					<div class="form-group">
						<label for="Xval">X:</label>
						<input type="text" class="form-control" id="Xval" size="3">

					</div>
					<div class="form-group">
						<label for="Yval">Y:</label>
						<input type="text" class="form-control" id="Yval" size="3">

					</div>
					<button class="myButton" id="gotoDir" onclick="redir()"><spring:message code="button.go" text="IR"/> </button>
					<div id="error" style="display:none; color:#FF0000"><spring:message code="labelError.map"/> </div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>
