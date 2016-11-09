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
                    <td><spring:message code="messages.subject"/></td>
                    <td><spring:message code="messages.action"/></td>
                </thead>
                <tbody>
                    <c:forEach var="message" items="${messagesReceived}">
                        <tr class="table-row"  data-href="<c:url value="/messages/seeMessage?id=${message.getId()}"/>">
                            <td>${message.getFrom().getName()}</td>
                            <td>${message.getSubject()}</td>
                            <td><form method="post" action="<c:url value="/messages/delete"/>">

                                <input type="hidden" name="id" value="${message.getId()}"/>

                                <input type="submit" class="myButton" value="<spring:message code="messages.delete"/>"/>


                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <h1><spring:message code="messages.createMessage"/></h1>


    <form method="post" action="<c:url value="/messages/sendMessage"/>">

        <div class="form-group">
                <label><spring:message code="messages.username"/></label>
                <input type="username" class="form-control" name="username" aria-describedby="usernameHelp" placeholder=<spring:message code="messages.mailPlaceholder"/>>
                <small id="usernameHelp" class="form-text text-muted"><spring:message code="messages.advise"/></small>
        </div>

        <div class="form-group">
            <label><spring:message code="messages.subject"/></label>
            <input type="subject" class="form-control" name="subject" aria-describedby="subjectHelp" placeholder=<spring:message code="messages.subject"/>>
        </div>

        <div class="form-group">
            <label><spring:message code="messages.message"/></label>
            <textarea class="form-control" name="message" id="exampleTextarea" rows="3"></textarea>
        </div>

            <button type="submit" class="myButton"><spring:message code="messages.submit"/></button>

    </form>

</div>



<%@ include file="footer.jsp" %>
<script src="<c:url value= "/resources/js/messages.js" />"></script>

