<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Help</title>
</head>
<body>

<div class="container">
	<div id="navbar">
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
	</div>
	<div class="helpBlock">
		<p>Thank you for trying Game Room. The purpose of this website is to provide 
		educators with a platform to play games and/or review materials with their students. Use the links on the left side to navigate the helps section.
		</p>
	</div>
	<hr>
	<div class="row">
		<div class="flex-1">
			<div class="helpItem">
				<p class="helpLinkContainer"><a class="helpLink" href="#newRoom">Create a new room</a></p>
				<p class="helpLinkContainer"><a class="helpLink" href="#joinRoom">Join an existing room</a></p>
				<p class="helpLinkContainer"><a class="helpLink" href="#gameTypes">Game Types</a></p>
				<p class="helpLinkContainer"><a class="helpLink" href="#emojiGame">Emoji Game</a></p>
				<p class="helpLinkContainer"><a class="helpLink" href="#reviewGame">Review</a></p>
				<p class="helpLinkContainer"><a class="helpLink" href="#general">General</a></p>
			</div>
	    </div>
	    <div class="flex-3-help">
	        <div class="helpItem">
	        	<h3 id="newRoom" class="helpHeader">Create a new room</h3>
				<p class="helpText">In order to create a new room, you will have to sign up as a host. Once you have created an account, you can create as many rooms as you wish. 
				However, creating a new room will reset all scores. This means that you can only have one active room at a time.</p>
				<p class="helpText">Once a new room is created, a unique link will be generated. This link can be shared with anyone you would like to invite to your room.</p>
				<p class="helpText">You also have the option to create a password-protected room. The password will then be required in order to join the room.</p>
	        </div>
	        <div class="helpItem">
	        	<h3 id="joinRoom" class="helpHeader">Join an existing room</h3>
				<p class="helpText">To join an existing room, no signup is required. There are two ways to join a room.</p>
				<p class="helpText">&nbsp;&nbsp;1. You can use a link that has been sent to you<br>&nbsp;&nbsp;2. You can manually enter the room link</p>
				<p class="helpText">Everyone who joins a room is considered a player. A player name is required to join. The benefit of not having to sign up 
				does come with a drawback. Players who join a room and log out, will have to use a different player name to join again.</p>
				<p class="helpText">Player scores can be adjusted manually by the host in order to get around any login problems players may encounter.</p>
	        </div>
	        <div class="helpItem">
	        	<h3 id="gameTypes" class="helpHeader">Game Types</h3>
				<p class="helpText">
					There are currently two game types in Game Room</p>
					<p class="helpText">&nbsp;&nbsp;1. <a class="helpLink" href="#emojiGame">Emoji Game</a><br>
					&nbsp;&nbsp;2. <a class="helpLink" href="#reviewGame">Review</a></p>
	        </div>
	        <div class="helpItem">
	        	<h3 id="emojiGame" class="helpHeader">Emoji Game</h3>
				<p class="helpText">The idea behind the Emoji Game is for the host to use emojis to represent a Movie or TV show. Each player will then be able to submit 
				one answer.</p> 
				<p class="helpText">The host has the option to use presets or create their own emoji lists.</p>
				<p class="helpText">The host can control which answers are acceptable by ticking the checkbox provided.</p>
				<p class="helpText">The host can untick a ticked checkbox to remove any points given by accident.</p>
	        </div>
	        <div class="helpItem">
	        	<h3 id="reviewGame" class="helpHeader">Review</h3>
				<p class="helpText">The Review 'game' allows hosts to post questions to all players. Each player will then be able to submit one answer.</p>
				<p class="helpText">The host can control which answers are acceptable by ticking the checkbox provided.</p>
				<p class="helpText">The host can untick a ticked checkbox to remove any points given by accident.</p>
	        </div>
	        <div class="helpItem">
	        	<h3 id="general" class="helpHeader">General</h3>
				<p class="helpText">Each game type allows the host to activate double points by clicking on the 'Multiplier' button.</p> 
				<p class="helpText">There is currently no time limit. The host is free to use their discretion. This feature may be added in the future.</p>
				<p class="helpText">The host has the ability to modify scores by clicking on the player name and entering the desired score.</p>
				<p class="helpText">The host and the players can communicate in the chat. A message will be displayed when a new player joins.</p>
				<p class="helpText">If a player logs out, they can only rejoin with a new name.</p>
	        </div>
		</div>
	</div>
</div>

<script type="text/javascript" src="../js/passwordReset.js"></script>

</body>
</html>