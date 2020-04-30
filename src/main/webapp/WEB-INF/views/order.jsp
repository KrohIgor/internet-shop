<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
</head>
<body>
<h1>Order ID: ${order.orderId}</h1>
<h2>User: ${order.user.getName()}</h2>
<h2>Products: </h2>
</form>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${order.products}">
        <tr>
            <td>
                <c:out value="${product.productId}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
        </tr>
    </c:forEach>
</table>
<p></p><a href="${pageContext.request.contextPath}/products/all">Return to the list of all products</a></p>
<p></p><a href="${pageContext.request.contextPath}/orders/all">Return to the list of all orders</a></p>
</body>
</html>
