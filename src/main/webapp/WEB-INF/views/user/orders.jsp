<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <title>All orders</title>
</head>
<body class="container">
<h1 class="text-primary">All user orders page</h1>
<form method="get" action="${pageContext.request.contextPath}/">
    <button type="submit" class="btn-primary">HOME</button>
</form>
<table class="table-striped" border="1">
    <tr>
        <th>ID</th>
        <th>User name</th>
        <th>Information</th>
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
                <a class="text-info" href="${pageContext.request.contextPath}/viewOrder?orderId=${order.orderId}">VIEW</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
