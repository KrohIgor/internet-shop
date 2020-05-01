<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>
<h4 style="color: red">${errorMsg}</h4>

<form action="${pageContext.request.contextPath}/login" method="post">
    Login: <input type="text" name="login">
    Password: <input type="password" name="pwd">
    <button type="submit">Login</button>
</form>
<p><a href="${pageContext.request.contextPath}/registration">Go to user registration</a></p>
</body>
</html>
