<link rel="stylesheet" href="<c:url value= "/resources/css/resourceBar.css" /> ">

<div class="resBar">
	<table>
		<tbody>
			<tr>
				<c:forEach var="res" items="${resList}" varStatus="loop">
				<td>
					<re:Resource rate="${ratesList.get(loop.index)}" type="${res.type}" amount="${res.quantity}" path="${pageContext.request.contextPath}"/>
				</td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<c:url value= "/resources/js/resBar.js" />"></script>