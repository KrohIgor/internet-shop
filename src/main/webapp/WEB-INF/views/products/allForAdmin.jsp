<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <title>All products</title>
</head>
<body class="container">
<h1 class="text-primary">All products page</h1>
<form method="get" action="${pageContext.request.contextPath}/">
    <button type="submit" class="btn-primary">HOME</button>
</form>
<table class="table-striped" border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Update</th>
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
                <a class="text-warning"
                   href="${pageContext.request.contextPath}/updateProduct?productId=${product.productId}">UPDATE</a>
            </td>
            <td>
                <a class="text-danger"
                   href="${pageContext.request.contextPath}/deleteProduct?productId=${product.productId}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<form method="get" action="${pageContext.request.contextPath}/addProduct">
    <button type="submit" class="btn-success">Add new product</button>
</form>
</body>
</html>
