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
}

function sendMessage(sender) {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': ""+sender+": "+document.getElementById("message").value}));
    document.getElementById("message").value = "";
}