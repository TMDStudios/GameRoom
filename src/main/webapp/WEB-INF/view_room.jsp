<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div class="row">
		<div class="flex-1">
			<div class="playersTitle">
	            <p>Players</p>
	        </div>
	        <hr class="smallHr">
	        <div class="box">
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
				<span>Room link: <a href="/rooms/${room.link}"><span id="roomLink">${link}</span></a></span>
				<c:if test="${room.privateRoom}">
					<span>Room password: ${room.password}</span>
				</c:if>
			</div>
			<hr class="smallHr">
			<div class="emojiDiv">
				<p id="emojiTitle"></p>
				<p id="currentEmojiGroup">Waiting for host...</p>
			</div>
			<c:if test="${not empty playerName}">
				<hr>
				<form id="guessForm">
				    <div>
				        <input class="messageInput" type="text" id="guess" placeholder="Enter your guess here...">
				    </div>
				</form>	
			</c:if>
		
		<c:if test="${not empty userId}">
			<hr>
				<div id="guesses" style="height: 150px;"></div>
			<hr>
			<c:if test="${room.gameType=='Emoji Game'}">
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
				            	<button onclick="handleMultiplier()" type="button">Multiplier</button>
				            </td>
				        </tr>
				    </tbody>
				</table>
				<div id="emojiGroup"></div>
				<hr>
				<p id="currentEmojis"></p>
				<button id="emojiBtn" onclick="sendEmojis()" type="button" id="send">Send Emojis</button>
			</c:if>
			<c:if test="${room.gameType=='Review'}">
				<form id="reviewQuestionForm">
				    <div>
				    	<span>
				    		<input class="reviewQuestionInput" type="text" id="reviewQuestion" placeholder="Enter Question">
				    		<button class='multiplierBtn' onclick="handleMultiplier()" type="button">Multiplier</button>
				    	</span>
				    </div>
				</form>
			</c:if>
		</c:if>
		
		<hr>
		<form id="messageForm">
		    <div>
		        <input class="messageInput" type="text" id="message" placeholder="Enter your message here...">
		    </div>
		</form>
		<div id="messages"></div>
		
	    </div>
	</div>
</div>

<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script type="text/javascript" src="../js/websocket.js"></script>

</body>
</html>