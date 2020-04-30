<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
<form method="get" action="${pageContext.request.contextPath}/">
    <button type="submit">HOME</button>
</form>
<h1>Please provide your product details</h1>
<h4 style="color:red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/addProduct">
    Name: <input type="text" name="name">
    Price: <input type="text" name="price">

    <button type="submit">Add</button>
</form>
</body>
</html>
