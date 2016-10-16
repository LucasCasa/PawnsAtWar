<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>
<div class="container">
    <h1><c:out value="${result}"/></h1>

    <table style="width: 100%">
        <thead>
            <th colspan="2"><h2><b>Atacante</b></h2></th>
            <th colspan="2"><h2><b>Defensor</b></h2></th>
        </thead>
        <tbody>
            <tr>
                <td>
                    <img src="<c:url value="/resources/images/warrior.png"/>"/>
                </td>
                <td>
                    <h3>Original: ${a0b}</h3>
                    <h3>Muertos: ${a0l}</h3>
                    <h3>Sobrevivientes: ${a0b - a0l}</h3>
                </td>
                <td>
                    <img src="<c:url value="/resources/images/warrior.png"/>"/>
                </td>
                <td>
                    <h3>Original: ${d0b}</h3>
                    <h3>Muertos: ${d0l}</h3>
                    <h3>Sobrevivientes: ${d0b - d0l}</h3>
                </td>
            </tr>
            <tr>
                <td>
                    <img src="<c:url value="/resources/images/archer.png"/>"/>
                </td>
                <td>
                    <h3>Original: ${a1b}</h3>
                    <h3>Muertos: ${a1l}</h3>
                    <h3>Sobrevivientes: ${a1b - a1l}</h3>
                </td>
                <td>
                    <img src="<c:url value="/resources/images/archer.png"/>"/>
                </td>
                <td>
                    <h3>Original: ${d1b}</h3>
                    <h3>Muertos: ${d1l}</h3>
                    <h3>Sobrevivientes: ${d1b - d1l}</h3>
                </td>
            </tr>
            <tr>
                <td>
                    <img src="<c:url value="/resources/images/horseman.png"/>"/>
                </td>
                <td>
                    <h3>Original: ${a2b}</h3>
                    <h3>Muertos: ${a2l}</h3>
                    <h3>Sobrevivientes: ${a2b - a2l}</h3>
                </td>
                <td>
                    <img src="<c:url value="/resources/images/horseman.png"/>"/>
                </td>
                <td>
                    <h3>Original: ${d2b}</h3>
                    <h3>Muertos: ${d2l}</h3>
                    <h3>Sobrevivientes: ${d2b - d2l}</h3>
                </td>
            </tr>
        </tbody>



    </table>
</div>
<%@ include file="footer.jsp" %>