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

function sendMessage(sender) {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': ""+sender+": "+document.getElementById("message").value}));
    document.getElementById("message").value = "";
}

function addEmoji(emoji){
	$("#currentEmojis").append(emoji);
}

function sendEmojis(){
	console.log("sending emojis");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': document.getElementById("currentEmojis").innerHTML}));
    document.getElementById("currentEmojis").innerHTML = '';
}