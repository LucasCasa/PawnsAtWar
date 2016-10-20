<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="build" uri="building.tld"%>
<%@ taglib prefix="bi" uri="buildingIcon.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
<link rel="stylesheet" href="<c:url value= "/resources/css/building.css" /> ">

    <div id="terrain" class="container">

    <%@ include file="resourceBar.jsp" %>
        <br>
        <div class="row">
        <c:if test="${owner.name != null}">
        <h3 style="text-align: center;"><spring:message code="buildingBelongs" /><c:out value=" ${owner.name}"/></h3>
        </c:if>
        </div>
        <br>
        <div class="row">
            <div class="col-md-4"> <img class="img-responsive" src="<c:url value="/resources/images/${building.name}.png" /> " /></div>
            <div class="col-md-8">
                <div class ="row">
                <h2 style="color: green">${message}</h2>
                </div>
                <h3><strong>${building.description}</strong></h3>
                    <c:choose>
                        <c:when test = "${user.id == owner.id}">
                            <build:Building info="${building}" level="${level}" point="${p}" locale="${locale}" price="${price}" messageSource="${messageSource}" path="${pageContext.request.contextPath}"/>
                        </c:when>
                        <c:when test="${user.id != owner.id && building.id != 0 && building.id != 5}">
                            <div class="col-md-2" onclick="att()"><a href="<c:url value="/armies?x=${p.getX()}&y=${p.getY()}"/>"><button class="myButton"><spring:message code="button.attack"/></button></a>
                            </div>
                        </c:when>
                    </c:choose>
            </div>
        </div>
    </div>




<%@ include file="footer.jsp" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<c:url value= "/resources/js/building.js" />"></script>