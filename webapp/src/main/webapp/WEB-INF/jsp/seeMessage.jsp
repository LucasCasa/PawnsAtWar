<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="build" uri="building.tld"%>
<%@ taglib prefix="bi" uri="buildingIcon.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
<link rel="stylesheet" href="<c:url value= "/resources/css/building.css" /> ">

<div class="container">

        <h1><spring:message code="messages.description"/></h1>
        <textarea class="form-control" name="message"  id="exampleTextarea" rows="6" maxlength="1024">
            <spring:message code="messages.mailFrom"/> ${from}
            <spring:message code="messages.mailSubject"/> ${subject}
            <spring:message code="messages.mailContent"/> ${message}
        </textarea>

    <br>
    <br>
    <h1><spring:message code="messages.answerMessage"/></h1>


    <form method="post" action="<c:url value="/messages/sendMessage"/>">

        <div class="form-group">
            <label><spring:message code="messages.username"/></label>
            <input type="username" class="form-control" name="username"  value="${from}" aria-describedby="usernameHelp" placeholder=<spring:message code="messages.mailPlaceholder"/>>
        </div>

        <div class="form-group">
            <label><spring:message code="messages.subject"/></label>
            <input type="subject" class="form-control" name="subject" value="RE ${subject}" aria-describedby="subjectHelp" placeholder=<spring:message code="messages.subject"/>>
        </div>

        <div class="form-group">
            <label><spring:message code="messages.message"/></label>

            <textarea class="form-control" name="message" onkeyup="textCounter(this,'counter',1024);"  id="exampleTextarea" rows="3" maxlength="1024"></textarea>
            <label><spring:message code="messages.wordCount"/></label><input disabled  maxlength="3" size="3" value="1024"   id="counter">
        </div>

        <button type="submit" class="myButton"><spring:message code="messages.respond"/></button>

    </form>
</div>
<br/>
<br/>

<%@ include file="footer.jsp" %>
<script src="<c:url value= "/resources/js/messages.js" />"></script>