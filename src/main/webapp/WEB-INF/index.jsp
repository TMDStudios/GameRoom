<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Game Room</title>
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

<h1  class="center-text-title" id="welcome"></h1>
<c:if test="${not empty userId}">
	<p>Logged in as: ${host.username} (Host)</p>
</c:if>
<c:if test="${empty userId}">
	<p>Logged in as: ${playerName}</p>
</c:if>

<p>Current Rooms:</p>
<c:forEach var="room" items="${rooms}">
    <p><a href="/rooms/${room.link}">${room.name}</a></p>
</c:forEach>
<p>Players:</p>
<c:forEach var="player" items="${players}">
    <p>${player.name} - ${player.id}</p>
</c:forEach>

<script src="/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>