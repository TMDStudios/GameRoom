var stompClient = null;
var ready = false;

$(document).ready(function() {
    console.log("Messages are live");
    connect();
});

function connect() {
    var socket = new SockJS('/room-messages');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connection: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
    });
}

function showMessage(message) {
    $("#messages").append("<p>" + message + "</p>");
    window.scrollTo(0,document.body.scrollHeight);
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
	onclick="sell(document.getElementById('${coin.id}').value, ${coin.totalAmount})"
}

function addEmoji(emoji){
	$("#currentEmojis").append(emoji);
}

function sendEmojis(){
	console.log("sending emojis");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': document.getElementById("currentEmojis").innerHTML}));
    document.getElementById("currentEmojis").innerHTML = '';
}

function clearEmojis(){
    document.getElementById("currentEmojis").innerHTML = '';
}