<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mate Academy!</title>
</head>
<body>
<h1>Hello mates!</h1>
<p><a href="${pageContext.request.contextPath}/injectData">Inject test data into the DB</a></p>
<p><a href="${pageContext.request.contextPath}/products/all">All products for buyers</a></p>
<p><a href="${pageContext.request.contextPath}/products/allForAdministrator">All products for administrator</a></p>
<p><a href="${pageContext.request.contextPath}/orders/all">All orders</a></p>
<p><a href="${pageContext.request.contextPath}/users/all">All users</a></p>
<p><a href="${pageContext.request.contextPath}/registration">Go to user registration</a></p>
</body>
</html>
