<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="custom.tld"%>
<%@ taglib prefix="re" uri="resource.tld"%>
<%@ include file="header.jsp" %>

<% Integer username = (Integer)request.getAttribute("id"); %>
<% session.setAttribute( "user", username); %>
<h1>Login Exitoso</h1>

<%@ include file="footer.jsp" %>