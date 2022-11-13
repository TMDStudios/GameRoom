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
    });
}

function showMessage(message) {
    $("#messages").append("<p>" + message + "</p>");
    window.scrollTo(0,document.body.scrollHeight);
}

function showEmojis(emojis) {
	$("#currentEmojiGroup").empty();
    $("#currentEmojiGroup").append(emojis);
}

$("#messageForm").submit(function() {
	console.log("sending message");
	sender = document.getElementById("sender").innerHTML;
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': ""+sender+": "+document.getElementById("message").value}));
    document.getElementById("message").value = "";
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