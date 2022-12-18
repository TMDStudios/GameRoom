<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Game Room</title>
<link rel="shortcut icon" href="favicon.ico?" type="image/x-icon" />
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
				<span>Game Type: <span  id="gameType">${room.gameType}</span></span>
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
				<c:if test="${room.gameType=='Guess the Flag'}">
					<div class="countryBtnContainer">
				    	<p class="center-text" id="countryAnswer">Choose a country</p>
				    	<span id="countryBtns">
				    	</span>
				    	<br>
				    	<input class="guessInput" type="text" id="guess" placeholder="Enter your guess here...">
				    </div>
				</c:if>
				<c:if test="${room.gameType!='Guess the Flag'}">
					<form id="guessForm">
					    <div>
					        <input class="guessInput" type="text" id="guess" placeholder="Enter your guess here...">
					    </div>
					</form>	
				</c:if>
			</c:if>
		
		<c:if test="${not empty userId}">
			<hr>
				<div id="guesses" style="height: 150px;"></div>
			<hr>
			
			<c:if test="${room.gameType=='Emoji Game'}">
				<div class="emojiControls">
					<span>
		            	<select id="emojiPicker" onchange="addPreset(this.value)">
							<c:forEach var="set" items="${preset}">
								<option value="${set[0]}">${set[1]}</option>
							</c:forEach>
						</select>
					</span>
					<span>
		            	<select id="emojiPicker" onchange="showGroup(this.value)">
							<c:forEach var="customItem" items="${custom}">
								<option value="${customItem[0]}">${customItem[1]}</option>
							</c:forEach>
						</select>
					</span>
					<span>
						<button onclick="clearEmojis()" type="button">Clear Emojis</button>
					</span>
					<span>
						<button onclick="handleMultiplier()" type="button">Multiplier</button>
					</span>
				</div>
				<div id="emojiGroup"></div>
				<hr>
				<p id="currentEmojis"></p>
				<button id="emojiBtn" onclick="sendEmojis()" type="button" id="send">Send Emojis</button>
			</c:if>
			<c:if test="${room.gameType=='Review'}">
				<form id="reviewQuestionForm">
				    <div>
				    	<span>
				    		<input class="hostInput" type="text" id="reviewQuestion" placeholder="Enter Question">
				    		<button class='multiplierBtn' onclick="handleMultiplier()" type="button">Multiplier</button>
				    	</span>
				    </div>
				</form>
			</c:if>
			<c:if test="${room.gameType=='Guess the Flag'}">
				<form id="flagForm">
				    <div>
				    	<p class="currentFlag">Current Flag: <span id="currentFlag"></span></p>
				    	<span>
				    		<input class="hostInput" type="text" id="flagSearch" placeholder="Search Country">
				    		<button class='multiplierBtn' onclick="handleMultiplier()" type="button">Multiplier</button>
				    	</span>
				    </div>
				    <div id="countries"></div>
				</form>
			</c:if>
		</c:if>
		
		<hr>
		<h3 class="messagesTitle">${room.name} Chat</h3>
		<form id="messageForm">
		    <div>
		        <input class="messageInput" type="text" id="message" placeholder="Enter your message here...">
		    </div>
		</form>
		<div id="messages"></div>
		<c:if test="${room.gameType=='Guess the Flag'}">
			<p class="backLink">Flags courtesy of <a href="https://flagpedia.net">flagpedia.net</a></p>
		</c:if>
		
	    </div>
	</div>
</div>

<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script type="text/javascript" src="../js/websocket.js"></script>

</body>
</html>