var stompClient = null;
var ready = false;
const playerMap = new Map();

$(document).ready(function() {
    console.log("Messages are live");
    connect();
    
    $("#send").click(function() {
        sendMessage();
    });
});

function connect() {
    var socket = new SockJS('/room-messages');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connection: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
        stompClient.subscribe('/topic/emojis', function (emojiMessage) {
            showEmojis(JSON.parse(emojiMessage.body).content);
        });
        stompClient.subscribe('/topic/guesses', function (guess) {
            showGuess(JSON.parse(guess.body).content);
        });
        stompClient.subscribe('/topic/players', function (playerChange) {
            updatePlayers(JSON.parse(playerChange.body).content);
        });
    });
}

function showMessage(message) {
    $("#messages").append("<p>" + message + "</p>");
    window.scrollTo(0,document.body.scrollHeight);
}

function showEmojis(emojis) {
	try {
  		document.getElementById("guess").disabled = false;
  		document.getElementById("guess").style.color = "black";
  		document.getElementById("guess").value = "";
	}catch(error) {
	  	console.log("TypeError Warning");
	}
	$("#currentEmojiGroup").empty();
    $("#currentEmojiGroup").append(emojis);
}

function showGuess(guess) {
	end = guess.indexOf(":");
	player = guess.substring(0,end);
    $("#guesses").append("<p><input type=\"checkbox\" id='"+player+"' onclick=\"handleCheck('"+player+"')\"/>" + guess + "</p>");
    window.scrollTo(0,document.body.scrollHeight);
}

/*REMOVE THIS??*/ 
function nextRound() {
	showPlayers();
}

function showPlayers() {
	$("#playerDiv").empty();
	playerMap.forEach(function(value, key) {
		$("#playerDiv").append("<p>" + key + ":" + value + "</p>");
	});
}

function updatePlayers(message) {
	end = message.indexOf(":");
	playerName = message.substring(0,end);
	playerScore = message.substring(end+1,message.length);
	playerMap.set(playerName, playerScore);
	showPlayers();
}

function handleCheck(player) {
	var checkBox = document.getElementById(player);
	playerScore = playerMap.get(player);
	if(playerScore===undefined){playerScore=0;}
	convertedScore = parseInt(playerScore);
	console.log("score ====== "+convertedScore);
	if(checkBox.checked){
		playerMap.set(player, convertedScore+1);
	}else{
		playerMap.set(player, convertedScore-1);
	}
	console.log(player+":"+playerMap.get(player));
	stompClient.send("/ws/players", {}, JSON.stringify({'messageContent': player+":"+playerMap.get(player)}))	
}

$("#messageForm").submit(function() {
	console.log("sending message");
	sender = document.getElementById("sender").innerHTML;
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': ""+sender+": "+document.getElementById("message").value}));
    document.getElementById("message").value = "";
    return false;
});

$("#guessForm").submit(function() {
	console.log("sending guess");
	sender = document.getElementById("sender").innerHTML;
	guess = document.getElementById("guess").value;
    stompClient.send("/ws/guess", {}, JSON.stringify({'messageContent': ""+sender+": "+guess}));
    document.getElementById("guess").value = "Your guess was: "+guess;
    document.getElementById("guess").disabled = true;
    document.getElementById("guess").style.color = "lightblue";
    return false;
});

function showGroup(emojis){
	document.getElementById("emojiGroup").innerHTML = "";
	emojiList = emojis.split(",");
	emojiList.forEach(e => $("#emojiGroup").append('<button onclick="addEmoji(\''+e+'\')" type="button">'+e+'</button>'));
}

function addEmoji(emoji){
	$("#currentEmojis").append(emoji);
}

function sendEmojis(){
	console.log("sending emojis");
    stompClient.send("/ws/emoji", {}, JSON.stringify({'messageContent': document.getElementById("currentEmojis").innerHTML}));
    document.getElementById("currentEmojis").innerHTML = '';
    
    $('#hostFrame').contents().find('div').empty();
}

function clearEmojis(){
    document.getElementById("currentEmojiGroup").innerHTML = 'Waiting for host...';
}