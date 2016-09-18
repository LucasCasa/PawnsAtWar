<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="build" uri="building.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">

    <div id="terrain" class="container">

        <build:Building clas="img-responsive" info="${building}" />
    </div>

<%@ include file="footer.jsp" %>