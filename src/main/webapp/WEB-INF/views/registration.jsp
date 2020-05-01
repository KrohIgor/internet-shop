<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <title>Registration</title>
</head>
<body class="container">
<h1 class="text-primary">Please provide your user details</h1>
<h4 style="color:red">${message}</h4>
<div>
    <form role="form" method="post" action="${pageContext.request.contextPath}/registration">
        <div class="form-group">
            <label>Name: </label>
            <input class="form-control" type="text" name="name" placeholder="Input name">
        </div>
        <div class="form-group">
            <label>Login: </label>
            <input class="form-control" type="text" name="login" placeholder="Input login">
        </div>
        <div class="form-group">
            <label>Password: </label>
            <input class="form-control" type="password" name="pwd" placeholder="Input password">
        </div>
        <div class="form-group">
            <label>Repeat password: </label>
            <input class="form-control" type="password" name="pwd-repeat" placeholder="Repeat password">
        </div>
        <button type="submit" class="btn-success">Register</button>
    </form>
</div>
</body>
</html>
