<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="build" uri="building.tld"%>
<%@ taglib prefix="bi" uri="buildingIcon.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
<link rel="stylesheet" href="<c:url value= "/resources/css/building.css" /> ">

<div class="container">

    <h1>De: ${from}</h1>
    <h1>Asunto: ${subject}</h1>
    <h1>Mensaje: ${message}</h1>


    <br>
    <br>
    <h1><spring:message code="messages.answerMessage"/></h1>


    <form method="post" action="<c:url value="/messages/sendMessage"/>">

        <div class="form-group">
            <label><spring:message code="messages.username"/></label>
            <input type="username" class="form-control" name="username"  value="${from}" aria-describedby="usernameHelp" placeholder=<spring:message code="messages.mailPlaceholder"/>>
            <small id="usernameHelp" class="form-text text-muted"><spring:message code="messages.advise"/></small>
        </div>

        <div class="form-group">
            <label><spring:message code="messages.subject"/></label>
            <input type="subject" class="form-control" name="subject" value="Respuesta ${subject}" aria-describedby="subjectHelp" placeholder=<spring:message code="messages.subject"/>>
        </div>

        <div class="form-group">
            <label><spring:message code="messages.message"/></label>
            <textarea class="form-control" name="message" id="exampleTextarea" rows="3"></textarea>
        </div>

        <button type="submit" class="myButton"><spring:message code="messages.respond"/></button>

    </form>
</div>


<%@ include file="footer.jsp" %>