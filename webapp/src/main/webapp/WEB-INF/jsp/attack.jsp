<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<div class="container">
    <h1><c:out value="${result}"/></h1>

    <table style="width: 100%">
        <thead>
            <th colspan="2"><h2><b><spring:message code="attacker"/></b></h2></th>
            <th colspan="2"><h2><b><spring:message code="defender"/></b></h2></th>
        </thead>
        <tbody>
            <tr>
                <td>
                    <img src="<c:url value="/resources/images/warrior.png"/>"/>
                </td>
                <td>
                    <h3><spring:message code="original"/> ${a0b}</h3>
                    <h3><spring:message code="killed"/> ${a0l}</h3>
                    <h3><spring:message code="survivors"/> ${a0b - a0l}</h3>
                </td>
                <td>
                    <img src="<c:url value="/resources/images/warrior.png"/>"/>
                </td>
                <td>
                    <h3><spring:message code="original"/> ${d0b}</h3>
                    <h3><spring:message code="killed"/> ${d0l}</h3>
                    <h3><spring:message code="survivors"/> ${d0b - d0l}</h3>
                </td>
            </tr>
            <tr>
                <td>
                    <img src="<c:url value="/resources/images/archer.png"/>"/>
                </td>
                <td>
                    <h3><spring:message code="original"/> ${a1b}</h3>
                    <h3><spring:message code="killed"/> ${a1l}</h3>
                    <h3><spring:message code="survivors"/> ${a1b - a1l}</h3>
                </td>
                <td>
                    <img src="<c:url value="/resources/images/archer.png"/>"/>
                </td>
                <td>
                    <h3><spring:message code="original"/> ${d1b}</h3>
                    <h3><spring:message code="killed"/> ${d1l}</h3>
                    <h3><spring:message code="survivors"/> ${d1b - d1l}</h3>
                </td>
            </tr>
            <tr>
                <td>
                    <img src="<c:url value="/resources/images/horseman.png"/>"/>
                </td>
                <td>
                    <h3><spring:message code="original"/> ${a2b}</h3>
                    <h3><spring:message code="killed"/> ${a2l}</h3>
                    <h3><spring:message code="survivors"/> ${a2b - a2l}</h3>
                </td>
                <td>
                    <img src="<c:url value="/resources/images/horseman.png"/>"/>
                </td>
                <td>
                    <h3><spring:message code="original"/> ${d2b}</h3>
                    <h3><spring:message code="killed"/> ${d2l}</h3>
                    <h3><spring:message code="survivors"/> ${d2b - d2l}</h3>
                </td>
            </tr>
        </tbody>



    </table>
</div>
<%@ include file="footer.jsp" %>