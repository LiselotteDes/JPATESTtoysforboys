<%@ page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@taglib prefix='vdab' uri='http://vdab.be/tags' %>
<!DOCTYPE html>
<html lang="nl">
<head>
	<vdab:head title="Toys For Boys"/>
</head>
<body>
	<h1>Unshipped orders</h1>
	<c:if test="${not empty param.fout}">
		<div class="fout">${param.fout}</div>
	</c:if>

	<c:if test="${not empty unshippedOrders}">
		<c:url value="/shiporders" var="url"/>
		<form:form action="${url}" method="post" id="shipform">
			<table class="scrollable">
				<tr>
					<th>ID</th>
					<th>Ordered</th>
					<th>Required</th>
					<th>Customer</th>
					<th>Comments</th>
					<th>Status</th>
					<th>Ship</th>
				</tr>
				<c:forEach items="${unshippedOrders}" var="order">
				<spring:url var="orderurl" value="/orders/{id}"><spring:param name="id" value="${order.id}"/></spring:url>
				<tr>
					<td class="right"><a href="${orderurl}">${order.id}</a></td>
					<td class="right">${order.orderDate}</td>
					<td class="right">${order.requiredDate}</td>
					<td>${order.customer.name}</td>
					<td>${order.comments}</td>
					<td>${order.status.printableName}</td>
					<td><input type="checkbox" name="shipids" value="${order.id}"></td>
				</tr>
				</c:forEach>
			</table>
			<input type="submit" value="Set as shipped" id="shipbutton">
		</form:form>
	</c:if>
	
	<script>
		document.getElementById("shipform").onsubmit = function() {
			document.getElementById("shipbutton").disabled = true;
		};
	</script>
</body>
</html>