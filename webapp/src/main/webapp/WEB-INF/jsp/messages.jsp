<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="build" uri="building.tld"%>
<%@ taglib prefix="bi" uri="buildingIcon.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
<link rel="stylesheet" href="<c:url value= "/resources/css/building.css" /> ">


<div class="container">

    <h1><spring:message code="messages.messagesReceived"/></h1>
    <c:choose>
        <c:when test="${mReceivdedListSize == 0}">
            <div class="row">
                <h2 style="text-align: center"><spring:message code="noMessages" /></h2>
            </div>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                    <td><spring:message code="messages.user"/></td>
                    <td><spring:message code="messages.description"/></td>
                </thead>
                <tbody>
                    <c:forEach var="message" items="${messagesReceived}">
                        <tr>
                            <td><br><br>${message.getFrom().getName()}</td>
                            <td>${message.getMessage()}</td>
                            <td>
                                <input type="hidden" name="id" value="${offer.getId()}"/>
                                <input type="submit" class="myButton" value="<spring:message code="commerce.accept"/>"/>
                            </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <h1><spring:message code="messages.createMessage"/></h1>

    <form>
        <div class="form-group">
            <label for="exampleInputEmail1"><spring:message code="messages.mail"/></label>
            <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder=<spring:message code="messages.mailPlaceholder"/>>
            <small id="emailHelp" class="form-text text-muted"><spring:message code="messages.advise"/></small>
        </div>

        <div class="form-group">

            <textarea class="form-control" id="exampleTextarea" rows="3"></textarea>
        </div>

        <button type="submit" class="myButton"><spring:message code="messages.submit"/></button>
    </form>
</div>



<%@ include file="footer.jsp" %>
