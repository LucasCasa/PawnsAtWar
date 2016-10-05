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

                <div class="row">
                    <c:choose>
                        <c:when test = "${user.getId() == owner}">
                            <c:if test = "${building.getId() == 0 || building.getId() == 5}">
                                <div class="col-md-2"><button class="myButton" id="buildBtn">Construir</button></div>

                                <div class="container" id="buildList" style="display: none;">
                                    <table id="buildListTable">
                                        <thead>
                                            <td>Edificio</td>
                                            <td>Costo</td>
                                            <td>Construir</td>
                                        </thead>
                                        <tbody>
                                            <c:if test = "${building.getId()== 0}">
                                                <c:forEach var="b" items="${plainTerrainBuildings}">
                                                    <tr>
                                                        <td><bi:BuildingIcon id="${b}"/></td>
                                                        <td><re:Resource type="1" amount="1000"/></td>
                                                        <td>
                                                        <form method="post" action="<c:url value="/build"/>">
                                                        <input type="hidden" name="x" value="${point.getX()}"/>
                                                        <input type="hidden" name="y" value="${point.getY()}"/>
                                                        <input type="hidden" name="type" value="${b}"/>
                                                        <input type="submit" class="myButton" value="OK"/>
                                                        </form>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>

                            </c:if>
                            <c:if test="${building.getId() != 0 && building.getId() != 5}">
                                <div class="col-md-2"><button class="myButton">Demoler</button></div>
                                <div class="col-md-6"><button class="myButton" >Aumentar de nivel</button></div>
                            </c:if>
                        </c:when>
                        <c:when test="${user.getId() != owner && building.getId() != 0 && building.getId() != 5}">
                            <div class="col-md-2" onclick="att()"><a href="<c:url value="/armies?x=${point.getX()}&y=${point.getY()}"/>"><button class="myButton">Atacar</button></a>
                            </div>
                        </c:when>
                    </c:choose>
                </div>

            </div>
        </div>
    </div>




<%@ include file="footer.jsp" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<c:url value= "/resources/js/building.js" />"></script>