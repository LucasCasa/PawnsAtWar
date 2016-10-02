<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="build" uri="building.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">

    <div id="terrain" class="container">
        <div class="row">
            <div class="col-md-4"> <img class="img-responsive" src="<c:url value="/resources/images/${building.getName()}.png" /> " /></div>
            <div class="col-md-6">
                <p> ${building.getDescription()}</p>

                <div class="row">
                    <c:choose>
                        <c:when test = "${user.getId() == owner}">
                            <div class="col-md-6"><button class="myButton" >Aumentar de nivel</button></div>
                            <c:if test="${building.getId() != 0 && building.getId() != 5}">
                                <div class="col-md-2"><button class="myButton">Demoler</button></div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div class="col-md-2"><button class="myButton">Atacar</button></div>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>
    </div>




<%@ include file="footer.jsp" %>