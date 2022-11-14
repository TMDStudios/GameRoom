<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Game Room</title>
</head>
<body>
<div class="container">
	<div>
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
	</div>
	<div class="row">
		<div class="flex-1">
	        <div class="box">
	            <h3>Players</h3>
				<div id="playerDiv"></div>
	        </div>
	    </div>
	    <div class="flex-3">
	        <div class="roomInfo">
				<span>Room Name: ${room.name}</span>
				<span>Game Type: ${room.gameType}</span>
				<span>
					<c:if test="${empty playerName}">
						<span>Logged in as: <span id="sender">${host} (Host)</span></span>
					</c:if>
					<c:if test="${not empty playerName}">
						<span>Logged in as:  <span id="sender">${playerName}</span></span>
					</c:if>
				</span>
			</div>
			<div class="roomInfo">
				<span>Room link: <a href="/rooms/${room.link}">${link}</a></span>
				<c:if test="${room.privateRoom}">
					<span>Room password: ${room.password}</span>
				</c:if>
			</div>
			<div class="emojiDiv">
				<p class="emojiTitle">Current Emoji Group</p>
				<p id="currentEmojiGroup">Waiting for host...</p>
			</div>
			<c:if test="${not empty playerName}">
			<hr>
			<form id="guessForm">
			    <div>
			        <input class="messageInput" type="text" id="guess" placeholder="Enter your guess here...">
			    </div>
			</form>
			<hr>
		</c:if>
		
		<c:if test="${not empty userId}">
			<hr>
			<iframe id="hostFrame" src="/guesses/" title="Host Iframe"></iframe>
			<hr>
			<table class="emojiTable">
			    <tbody>
			    	<tr>
			            <td class="emojiControls">
			            	Preset
			            	<select id="emojiPicker" onchange="addEmoji(this.value)">
								<c:forEach var="set" items="${preset}">
									<option value="${set[0]}">${set[1]}</option>
								</c:forEach>
							</select>
			            </td>
			            <td class="emojiControls">
			            	Custom
			            	<select id="emojiPicker" onchange="showGroup(this.value)">
								<c:forEach var="customItem" items="${custom}">
									<option value="${customItem.value}">${customItem.key}</option>
								</c:forEach>
							</select>
			            </td>
			            <td class="emojiButtons">
			            	<button onclick="clearEmojis()" type="button">Clear Emojis</button>
			            </td>
			            <td class="emojiButtons">
			            	<button onclick="nextRound()" type="button">Next Round</button>
			            </td>
			        </tr>
			    </tbody>
			</table>
			<p id="emojiGroup"></p>
			<hr>
			<p id="currentEmojis"></p>
			<button id="emojiBtn" onclick="sendEmojis()" type="button" id="send">Send Emojis</button>
		</c:if>
		
		<hr>
		<form id="messageForm">
		    <div>
		        <input class="messageInput" type="text" id="message" placeholder="Enter your message here...">
		    </div>
		</form>
		<iframe id="messagesFrame" src="/room-messages/" title="Room Messages Iframe"></iframe>
	    </div>
	</div>
</div>

<script type="text/javascript" src="../js/websocket.js"></script>

</body>
</html>