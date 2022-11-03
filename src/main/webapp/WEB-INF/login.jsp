<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Log In</title>
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/">Home</a></li>
    <c:if test="${empty userId && empty playerName}">
		<li class="nav_item"><a class="nav_link" href="/rooms/join">Join Room</a></li>
	</c:if>
	<c:if test="${empty playerName}">
		<li class="nav_item"><a class="nav_link" href="/rooms/new">Create Room</a></li>
	</c:if>
    <c:if test="${not empty userId || not empty playerName}">
		<li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
	</c:if>
</ul>

<h1>Log In</h1>
<p>You must be logged in to create a room</p>

<form:form action="/login" method="post" modelAttribute="newLogin">
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
			<label>Password:</label>
		</div>
		<div>
			<form:errors path="password" class="text-danger"/>
		</div>
		<div>
			<form:input path="password" type="password"/>
		</div>

		<div>
			<input class="btn" type="submit" value="Log In"/>
		</div>
		
		<br>
		
		<div>
			<p><a class="nav_link" href="#" onclick="resetPassword()">Reset Password</a></p>
			<p><small>You can only reset your password if you entered an email when you created your account.</small></p>
		</div>
		
		<br>

	</div>
</form:form>


<hr>
<p>Don't have an account?</p>
<p><a href="/register">Sign up</a></p>

<script type="text/javascript" src="../js/app.js"></script>
<script type="text/javascript" src="../js/passwordReset.js"></script>

</body>
</html>