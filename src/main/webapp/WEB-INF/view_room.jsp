<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
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
</ul>

<h1>${room.name}</h1>
<c:if test="${room.privateRoom}">
	<p>Room password: ${room.password}</p>
</c:if>
<p>Game Type: ${room.gameType}</p>
<p>Room link: <a class="nav_link" href="/rooms/${room.link}">${link}</a></p>
<h3>Players:</h3>
<c:forEach var="player" items="${room.players}">
  		<p>${player.name} - ${player.id}</p>
</c:forEach>

<hr>

<form class="form-inline">
    <div class="form-group">
        <label for="message">Message</label>
        <input type="text" id="message" class="form-control" placeholder="Enter your message here...">
    </div>
    <button id="send" class="btn btn-default" type="button">Send</button>
</form>

<div id="messages">
</div>


<script type="text/javascript" src="../js/websocket.js"></script>

</body>
</html>