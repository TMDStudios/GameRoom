<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Register</title>
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/">Home</a></li>
    <li class="nav_item"><a class="nav_link" href="/rooms/join">Join Room</a></li>
    <li class="nav_item"><a class="nav_link" href="/rooms/new">Create Room</a></li>  
</ul>

<h1>Register</h1>

<form:form action="/register" method="post" modelAttribute="newUser">
	<div class="login-block">
		<div>
			<label>Username:</label>
		</div>
		<div>
			<form:errors path="username" class="text-danger"/>
		</div>
		<div>
			<form:input path="username"/>
		</div>
		
		<div>
			<label>Email (optional):</label>
		</div>
		<div>
			<form:errors path="email" class="text-danger"/>
		</div>
		<div>
			<form:input path="email"/>
		</div>

		<div>
			<label>Password:</label>
		</div>
		<div>
			<form:errors path="password" class="text-danger"/>
		</div>
		<div>
			<form:input path="password" type="password"/>
		</div>
		
		<div>
			<label>Confirm PW:</label>
		</div>
		<div>
			<form:errors path="confirm" class="text-danger"/>
		</div>
		<div>
			<form:input path="confirm" type="password"/>
		</div>

		<div>
			<input class="btn" type="submit" value="Register"/>
		</div>

	</div>
</form:form>

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>