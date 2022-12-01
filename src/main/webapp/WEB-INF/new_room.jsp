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
</ul>

<h3>New Game Room</h3>

<p>${error}</p>
<c:if test="${isHosting}">
	<p id="isHosting"></p>
</c:if>

<form:form action="/rooms/new" method="post" modelAttribute="room">
	<table>
	    <thead>
	    	<tr>
	            <td class="float-left">Room Name:</td>
	            <td class="float-left">
	            	<form:errors path="name" class="text-danger"/><br>
					<form:input class="input" path="name"/>
	            </td>
	        </tr>
	        <tr>
	            <td class="float-left">Game Type:</td>
	            <td class="float-left">
	            	<form:select path="gameType">
		                <c:forEach var="gameType" items="${gameTypes}">
		                    <option value="${gameType}">${gameType}</option>
		                </c:forEach>
		            </form:select>
	            </td>
	        </tr>
	        <tr>
	            <td class="float-left">Room Message:</td>
	             <td class="float-left">
	            	<form:errors path="message" class="text-danger"/><br>
					<form:textarea rows="4" class="input" path="message"/>
	            </td>
	        </tr>
	        <tr>
	            <td class="float-left">Private Room:</td>
	             <td class="float-left">
	            	<form:checkbox path="privateRoom"/>
	            </td>
	        </tr>
	        <tr>
	            <td class="float-left">Room Password:</td>
	             <td class="float-left">
	            	<form:errors path="password" class="text-danger"/><br>
					<form:input class="input" path="password"/>
	            </td>
	        </tr>
	        <tr>
	        	<td colspan=2><input class="input" class="button" type="submit" value="Submit"/></td>
	        </tr>
	    </thead>
	</table>
</form:form>

<script src="/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/app.js"></script>

</body>
</html>