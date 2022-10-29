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
    <li class="nav_item"><a class="nav_link" href="/rooms/join">Join Room</a></li>
    <li class="nav_item"><a class="nav_link" href="/rooms/new">Create Room</a></li>  
</ul>

<h1>Welcome to Game Room</h1>
<p>Logged in as: ${playerName}</p>
<p>Current Rooms:</p>
<c:forEach var="room" items="${rooms}">
    <p><a href="/rooms/${room.link}">${room.name}</a></p>
</c:forEach>
<p>Players:</p>
<c:forEach var="player" items="${players}">
    <p>${player.name} - ${player.id}</p>
</c:forEach>

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>