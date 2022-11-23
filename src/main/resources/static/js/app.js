var welcome = "Welcome to Game Room".split('');
var welcomeIndex = 0;
var scrollRight = false;

$(document).ready(function() {
	for(var i=0; i<welcome.length; i++){
		$("#welcome").append("<span id='"+i+"' style='color: rgba(255, 255, 255, 1);'>" + welcome[i] + "</span>");
	}
	handleColors();
});

function handleColors(){
	scrollRight ? welcomeIndex-- : welcomeIndex++;
    if(welcomeIndex<=0||welcomeIndex>=welcome.length-1){scrollRight=!scrollRight;}
	var r = Math.floor(Math.random() * 155 + 100);
	var g = Math.floor(Math.random() * 155 + 100);
	var b = Math.floor(Math.random() * 155 + 100);
	document.getElementById(welcomeIndex).style.color = 'rgba(' + r + ',' + g + ',' + b + ',' + 1 + ')';
    setTimeout(handleColors, 75);
}