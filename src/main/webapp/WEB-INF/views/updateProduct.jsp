<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <title>Update Product</title>
</head>
<body class="container">
<form method="get" action="${pageContext.request.contextPath}/">
    <button type="submit" class="btn-primary">HOME</button>
</form>
<h1 class="text-primary">Please update your product details</h1>
<h4 style="color:red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/updateProduct?productId=${product.productId}">
    Name: <input type="text" name="name" value="${product.name}">
    Price: <input type="text" name="price" value="${product.price}">

    <button type="submit" class="btn-success">Update</button>
</form>
</body>
</html>
