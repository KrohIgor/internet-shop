<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products in shopping cart</title>
</head>
<body>
<h1>All products in shopping cart page</h1>
<form method="get" action="${pageContext.request.contextPath}/">
    <button type="submit">HOME</button>
</form>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="product" items="${products}">
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
            <td>
                <a href="${pageContext.request.contextPath}/deleteProductFromShoppingCart?productId=${product.productId}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<form method="get" action="${pageContext.request.contextPath}/completeOrder">
    <button type="submit">Complete Order</button>
</form>
<p></p><a href="${pageContext.request.contextPath}/products/all">Return to the list of all products</a></p>
</body>
</html>