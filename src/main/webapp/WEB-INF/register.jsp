<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Register</title>
<link rel="shortcut icon" href="favicon.ico?" type="image/x-icon" />
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
	<li class="nav_item"><a class="nav_link" href="/help">Help</a></li>
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

</body>
</html>