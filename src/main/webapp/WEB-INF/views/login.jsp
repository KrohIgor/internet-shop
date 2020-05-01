<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <title>Login</title>
</head>
<body class="container">
<h1 class="text-primary">Login page</h1>
<h4 style="color: red">${errorMsg}</h4>
<div>
    <form role="form" action="${pageContext.request.contextPath}/login" method="post">
        <div>
            <label>Login: </label>
            <input class="form-control" type="text" name="login">
        </div>
        <div>
            <label>Password: </label>
            <input class="form-control" type="password" name="pwd">
        </div>
        <button type="submit" class="btn-success">Login</button>
    </form>
</div>
<p><a href="${pageContext.request.contextPath}/registration">Go to user registration</a></p>
</body>
</html>
