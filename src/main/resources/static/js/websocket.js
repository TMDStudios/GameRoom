var stompClient = null;
var ready = false;

$(document).ready(function() {
    console.log("Messages are live");
    connect();
    
    $("#send").click(function() {
        sendMessage();
    });

    $("#sendEmojis").click(function() {
        sendEmojis();
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
    $("#guesses").append("<p>" + guess + "</p>");
    window.scrollTo(0,document.body.scrollHeight);
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
}

function clearEmojis(){
    document.getElementById("currentEmojis").innerHTML = '';
}