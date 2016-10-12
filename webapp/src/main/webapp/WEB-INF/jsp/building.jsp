<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="build" uri="building.tld"%>
<%@ taglib prefix="bi" uri="buildingIcon.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
<link rel="stylesheet" href="<c:url value= "/resources/css/building.css" /> ">

    <div id="terrain" class="container">

    <%@ include file="resourceBar.jsp" %>
        <div class="row">
            <div class="col-md-4"> <img class="img-responsive" src="<c:url value="/resources/images/${building.getName()}.png" /> " /></div>
            <div class="col-md-6">
                <p> ${building.getDescription()}</p>


                    <c:choose>
                        <c:when test = "${user.getId() == owner}">
                            <build:Building info="${building}" point="${p}" build="${pageContext.request.contextPath}/build"/>
                        </c:when>
                        <c:when test="${user.getId() != owner && building.getId() != 0 && building.getId() != 5}">
                            <div class="col-md-2" onclick="att()"><a href="<c:url value="/armies?x=${p.getX()}&y=${p.getY()}"/>"><button class="myButton">Atacar</button></a>
                            </div>
                        </c:when>
                    </c:choose>
            </div>
        </div>
    </div>




<%@ include file="footer.jsp" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<c:url value= "/resources/js/building.js" />"></script>