<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">

    <div id="terrain" class="container">

        <te:Terrain clas="img-responsive" id="${terrain.getType()}" />
    </div>

<%@ include file="footer.jsp" %>