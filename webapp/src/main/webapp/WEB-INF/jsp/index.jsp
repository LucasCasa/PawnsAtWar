<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="/WEB-INF/jsp/custom.tld"%>
<%@ taglib prefix="re" uri="/WEB-INF/jsp/resource.tld"%>

<link rel="stylesheet" href="<c:url value= "/resources/css/style.css" /> ">
</head>

<body>
    <form method="post" action="<c:url value="/login"/>">
        <input type="text" name="id" value="id">
        <input type="submit">
    </form>
</body>
</html>