<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <title>Mate Academy!</title>
</head>
<body class="container">
<div>
    <h1 class="bg-primary">Hello mates!</h1>
    <p><a href="${pageContext.request.contextPath}/injectData">Inject test data into the DB</a></p>
    <p><a href="${pageContext.request.contextPath}/products/all">All products for buyers</a></p>
    <p><a href="${pageContext.request.contextPath}/products/allForAdministrator">All products for administrator</a></p>
    <p><a href="${pageContext.request.contextPath}/allProductInShoppingCart">All products in user's shopping cart</a></p>
    <p><a href="${pageContext.request.contextPath}/orders/userOrders">All user orders</a></p>
    <p><a href="${pageContext.request.contextPath}/orders/all">All orders in shop</a></p>
    <p><a href="${pageContext.request.contextPath}/users/all">All users</a></p>
    <p><a href="${pageContext.request.contextPath}/registration">Go to user registration</a></p>
    <p><a href="${pageContext.request.contextPath}/login">Go to user login</a></p>
</div>
<img src="http://biznesvbloge.ru/wp-content/uploads/2015/06/food-shop.jpg"
     class="img-thumbnail">
</body>
</html>
