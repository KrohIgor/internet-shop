<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<h1>All users page</h1>
<form method="get" action="${pageContext.request.contextPath}/">
    <button type="submit">HOME</button>
</form>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Name</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.userId}"/>
            </td>
            <td>
                <c:out value="${user.login}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/deleteUser?userId=${user.userId}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
