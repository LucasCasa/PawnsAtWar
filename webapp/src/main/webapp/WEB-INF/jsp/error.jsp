<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 9/20/16
  Time: 8:10 PM
  To change this template use File | Settings | File Templates.

--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>


<div id="terrain" class ="container">
    <div class="row">
        <div class="col-md-4"><img src="<c:url value="/resources/images/warrior.png"/>"/></div>
        <div class="col-md-4"> <p >Lo sentimos, pero la pagina que usted busca no esta disponible.</p></div>
        <div class="col-md-6"><button class="myButton" id="gotoDir" onclick="toMap()">Volver a Home</button></div>
    </div>

</div>


<%@ include file="footer.jsp" %>
