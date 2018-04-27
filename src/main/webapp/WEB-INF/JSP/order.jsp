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
		<dd><spring:eval expression="order.orderDate"/></dd>
		<dt>Required:</dt>
		<dd><spring:eval expression="order.requiredDate"/></dd>
		<dt>Customer:</dt>
		<dd>${order.customer.name}<br>
			${order.customer.address.streetAndNumber}<br>
			${order.customer.address.postalCode} ${order.customer.address.state}<br>
			${order.customer.address.country.name}</dd>
		<c:if test="${not empty order.comments}">
			<dt>Comments:</dt>
			<dd>${order.comments}</dd>
		</c:if>
		<dt>Detail:</dt>
		<dd>
		<table>
			<tr><th>Product</th><th>Price each</th><th>Quantity</th><th>Value</th><th>Deliverable</th></tr>
			<c:forEach items="${order.orderDetails}" var="detail">
			<tr>
				<td class="right">${detail.product.name}</td>
				<td class="right"><spring:eval expression="detail.priceEach"/></td>
				<td class="right">${detail.quantityOrdered}</td>
				<td><spring:eval expression="detail.value"/></td>
				<td class="center">
				<c:choose>
					<c:when test="${detail.deliverable}"><span class="green">&amp;check;</span></c:when>
					<c:otherwise><span class="red">&amp;cross;</span></c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
		</table>
		</dd>
		<dt>Value:</dt>
		<dd><spring:eval expression="order.value"/></dd>
	</dl>
</body>
</html>