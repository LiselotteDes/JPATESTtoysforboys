<%@ page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="nl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Toys For Boys</title>
</head>
<body>
	<h1>Unshipped orders</h1>
	<c:if test="${not empty param.fout}">
		<div class="fout">${param.fout}</div>
	</c:if>
	<table>
	
	</table>

</body>
</html>