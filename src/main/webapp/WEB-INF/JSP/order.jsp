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
	<h1>Order ${order.id}</h1>
	<dl>
		<dt>Ordered:</dt>
		<dd>${order.orderDate}</dd>
		<dt>Required:</dt>
		<dd>${order.requiredDate}</dd>
		<dt>Customer:</dt>
		<dd>${order.customer.name}<br>
			${order.customer.address.streetAndNumber}<br>
			${order.customer.address.postalCode} ${order.customer.address.state}<br>
			${order.customer.address.country.name}</dd>
		<dt>Comments:</dt>
		<dd>${order.comments}</dd>
		<dt>Detail:</dt>
		<dd>
		<table>
			<tr><th>Product</th><th>Price each</th><th>Quantity</th><th>Value</th><th>Deliverable</th></tr>
			<c:forEach items="${order.orderDetails}" var="detail">
			<tr>
				<td class="right">${detail.product.name}</td>
				<td class="right">${detail.priceEach}</td>
				<td class="right">${detail.quantityOrdered}</td>
				<td>${detail.value}</td>
				<td class="center">
				<c:choose>
					<c:when test="${detail.deliverable}">&check;</c:when>
					<c:otherwise>&cross;</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
		</table>
		</dd>
		<dt>Value:</dt>
		<dd>${order.value}</dd>
	</dl>
</body>
</html>