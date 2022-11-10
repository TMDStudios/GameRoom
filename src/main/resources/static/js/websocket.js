var stompClient = null;

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
    });
}

function showMessage(message) {
    $("#messages").append("<p>" + message + "</p>");
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': $("#message").val()}));
    document.getElementById("message").value = "Enter your message here...";
}