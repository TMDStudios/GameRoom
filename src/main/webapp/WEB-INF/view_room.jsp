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
    <li class="nav_item"><a class="nav_link" href="/home">Home</a></li>
    <li class="nav_item"><a class="nav_link" href="#">Join Room</a></li>
    <li class="nav_item"><a class="nav_link" href="/rooms/new">Create Room</a></li>  
</ul>

<h1>${room.name}</h1>
<p>Room link: <a class="nav_link" href="/rooms/${room.link}">http://localhost:8080/rooms/${room.link}</a></p>

<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>