<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>New Game Room</title>
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

<h3 class="center-text-title">New Game Room</h3>

<p class="center-text">${error}</p>
<c:if test="${isHosting}">
	<p id="isHosting"></p>
</c:if>

<form:form action="/rooms/new" method="post" modelAttribute="room">
	<div class="login-block">
		<div>
			<label>Room Name:</label>
		</div>
		<div>
			<form:errors path="name" class="text-danger"/>
		</div>
		<div>
			<form:input class="input" path="name"/>
		</div>
		
		<div>
			<label>Game Type:</label>
			<form:select path="gameType">
                <c:forEach var="gameType" items="${gameTypes}">
                    <option value="${gameType}">${gameType}</option>
                </c:forEach>
            </form:select>
		</div>
		
		<div>
			<label>Room Message:</label>
		</div>
		<div>
			<form:errors path="message" class="text-danger"/>
		</div>
		<div>
			<form:input class="input" path="message"/>
		</div>
		
		<div>
			<label>Private Room: <form:checkbox path="privateRoom"/></label>
		</div>
		
		<div>
			<label>Room Password:</label>
		</div>
		<div>
			<form:errors path="password" class="text-danger"/>
		</div>
		<div>
			<form:input class="input" path="password"/>
		</div>

		<div>
			<input class="btn" type="submit" value="Submit"/>
		</div>
	</div>
</form:form>

<script src="/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>