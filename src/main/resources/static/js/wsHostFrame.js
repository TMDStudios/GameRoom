var stompClient = null;

$(document).ready(function() {
    console.log("Host Frame is live");
    var socket = new SockJS('/room-messages');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connection: ' + frame);
        stompClient.subscribe('/topic/guesses', function (guess) {
            showGuess(JSON.parse(guess.body).content);
        });
    });
});

function showGuess(guess) {
	end = guess.indexOf(":");
	player = guess.substring(0,end);
    $("#guesses").append("<p><input type=\"checkbox\" id='"+player+"' onclick=\"handleCheck('"+player+"')\"/>" + guess + "</p>");
    window.scrollTo(0,document.body.scrollHeight);
}