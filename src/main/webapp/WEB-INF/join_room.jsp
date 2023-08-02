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
<title>Join Game Room</title>
<link rel="shortcut icon" href="favicon.ico?" type="image/x-icon" />
</head>
<body>

<ul class="navbar">
    <li class="nav_item"><a class="nav_link" href="/">Home</a></li>
    <c:if test="${empty userId && empty playerName}">
		<li class="nav_item"><a class="nav_link" href="/join">Join Room</a></li>
	</c:if>
	<c:if test="${empty playerName}">
		<li class="nav_item"><a class="nav_link" href="/new">Create Room</a></li>
	</c:if>
    <c:if test="${not empty userId || not empty playerName}">
		<li class="nav_login"><a class="nav_link" href="/logout">Log Out</a></li>
	</c:if>
	<li class="nav_item"><a class="nav_link" href="/help">Help</a></li>
</ul>

<h3 class="center-text-title">Join Game Room</h3>
<p class="center-text text-danger">${error}</p>

<form action="/join" method="post">
	<div class="login-block">
		<div>
			<label>Room Link:</label>
		</div>
		<div>
			<input class="input" name="roomLink" id="roomLink" value="${roomLink}"/>
		</div>
		
		<div>
			<label>Player Name:</label>
		</div>
		<div>
			<input class="input" name="playerName" id="playerName"/>
		</div>

		<div class="center-text">
			<input class="btn" onclick="checkLanguage(document.getElementById('playerName').value)" type="submit" value="Submit"/>
		</div>
	</div>
</form>

<div class="banner-div">
	<a href="${link}">
		<img
		  class="banner"
		  style="max-width: 100%"
		  src="${banner}?w=600"
		/>
	</a>
</div>

<script src="/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>