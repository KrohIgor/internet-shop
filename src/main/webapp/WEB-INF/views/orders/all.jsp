<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<h1>All orders page</h1>
<form method="get" action="${pageContext.request.contextPath}/">
    <button type="submit">HOME</button>
</form>
<table border="1">
    <tr>
        <th>ID</th>
        <th>User name</th>
        <th>Information</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.orderId}"/>
            </td>
            <td>
                <c:out value="${order.user.name}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/viewOrder?orderId=${order.orderId}">VIEW</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/deleteOrder?orderId=${order.orderId}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
