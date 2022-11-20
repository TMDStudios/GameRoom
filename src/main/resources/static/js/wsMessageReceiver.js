var stompClient = null;

$(document).ready(function() {
    console.log("Messages are live");
    var socket = new SockJS('/room-messages');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connection: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
    });
});

function showMessage(message) {
	if(message.includes(" has joined")){
		$("#messages").append("<p style='color: teal;'>" + message + "</p>");
	}else{
		$("#messages").append("<p>" + message + "</p>");
	}
    window.scrollTo(0,document.body.scrollHeight);
}