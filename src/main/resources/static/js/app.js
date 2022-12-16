var welcome = "Welcome to Game Room".split('');
var welcomeIndex = 0;
var b = 0;

$(document).ready(function() {
	if(document.getElementById("welcome")!=null){
		for(var i=0; i<welcome.length; i++){
			b+=10;
			$("#welcome").append("<span id='"+i+"' style='color: rgba(0, 0, "+b+", 1);'>" + welcome[i] + "</span>");
		}
		handleColors();
	}
	if(document.getElementById("isHosting")!=null){
		setTimeout(showWarning, 500);
	}
});

function handleColors(){
	document.getElementById(welcomeIndex).style = 'color: rgba(255, 255, 255, 1);';
	welcomeIndex++;
    if(welcomeIndex<welcome.length){setTimeout(handleColors, 10);}
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

function openPage(page){
	window.location.replace(page);
}