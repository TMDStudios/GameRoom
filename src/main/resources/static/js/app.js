var welcome = "Welcome to Game Room".split('');
var welcomeIndex = 0;
var scrollRight = false;
var lastIndex = 0;

$(document).ready(function() {
	if(document.getElementById("welcome")!=null){
		for(var i=0; i<welcome.length; i++){
			$("#welcome").append("<span id='"+i+"' style='color: rgba(255, 255, 255, 1);'>" + welcome[i] + "</span>");
		}
		handleColors();
	}
	if(document.getElementById("isHosting")!=null){
		setTimeout(showWarning, 500);
	}
});

function handleColors(){
	scrollRight ? welcomeIndex-- : welcomeIndex++;
    if(welcomeIndex<=0||welcomeIndex>=welcome.length-1){scrollRight=!scrollRight;}
    if(welcomeIndex>0&&scrollRight){lastIndex=welcomeIndex-1;}
    else if(welcomeIndex<welcome.length-1&&!scrollRight){lastIndex=welcomeIndex+1;}
	var r = Math.floor(Math.random() * 155 + 100);
	var g = Math.floor(Math.random() * 155 + 100);
	var b = Math.floor(Math.random() * 155 + 100);
	document.getElementById(welcomeIndex).style = 'color: rgba(' + r + ',' + g + ',' + b + ',' + 1 + '); font-size: 1em';
	document.getElementById(lastIndex).style = 'color: rgba(255, 255, 255, 1); font-size: 1.1em';
    setTimeout(handleColors, 100);
}

function showWarning(){
	if(confirm("WARNING\nCreating a new room will delete all scores from other rooms!\nProceed?")){
		let xhttp = new XMLHttpRequest();
	  	xhttp.open("POST", "/reset-scores");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send();
	}else{
		window.location.replace("/");
	}
}