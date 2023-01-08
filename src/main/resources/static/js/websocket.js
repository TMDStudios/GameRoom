var stompClient = null;
const playerMap = new Map();
var round = 0;
var emojiCount = 0;
var multiplier = 1;
var link = "";
const countriesMap = new Map();
var countries = [];
var states = [];
var correctFlag = "";

$(document).ready(function() {
	var fullLink = document.getElementById("roomLink").innerHTML.split("/");
	link = fullLink[fullLink.length-1];
	console.log(link);
    console.log("WS is live");
    connect();
    
    $("#send").click(function() {
        sendMessage();
    });
    
    if(document.getElementById("gameType").innerHTML=="Guess the Flag"){
		getFlagsList();
	}
});

function connect() {
    var socket = new SockJS('/room-messages');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connection: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
			switch(JSON.parse(message.body).type) {
				case "message"+link:
			    	showMessage(JSON.parse(message.body).content);
			        break;
			    case "guess"+link:
			    	showGuess(JSON.parse(message.body).content);
			        break;
			    case "emoji"+link:
			    	showEmojis(JSON.parse(message.body).content);
			        break;
			    case "reviewQuestion"+link:
			    	showEmojis(JSON.parse(message.body).content);
			        break;
			    case "flag"+link:
			    	showFlag(JSON.parse(message.body).content);
			        break;
			    case "score"+link:
			    	updatePlayers(JSON.parse(message.body).content);
			        break;
			    case "allScores"+link:
			    	updateScores(JSON.parse(message.body).content);
			        break;
			    case "blockPlayer"+link:
			    	blockPlayer(JSON.parse(message.body).content);
			        break;
			    default:
			    	console.log("Unknown message type: "+JSON.parse(message.body).type)
			    	break;
			}
        });
        sender = document.getElementById("sender").innerHTML;
		stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': ""+sender+" has joined", 'messageType': 'message'+link}));
		if(document.getElementById("guesses")==null){
			addPlayer(sender);
		}
		if(document.getElementById("guesses")!=null){
			if(playerMap.size<1){
				let req = new XMLHttpRequest();
				req.open('GET', "/get-scores");
			  	req.onload = function() {
			    	if(this.responseText){populateMap(this.responseText);}
			  	}
			  	req.send();
			}
		}else{
			if(window.screen.width > 480){
				document.getElementById("messages").style = "height: 300px;overflow-x: hidden;overflow-y: auto;display: block;box-sizing:border-box;width: 100%;"+
				"border: solid 1px rgba(212, 212, 212, 0.1);padding-left: 12px;overflow: -moz-scrollbars-none;-ms-overflow-style: none;";
				document.getElementById("currentEmojiGroup").style = "text-align: center;font-size: 48px;height: 150px;";
			}
			document.getElementById("guess").value = "Waiting for game to start...";
			document.getElementById("guess").disabled = true;
			document.getElementById("guess").style.color = "lightblue";
			if(document.getElementById("countryBtns")!=null){
				document.getElementById("guess").style.display = "none";
			}
		}
    });
}

function showMessage(message) {
    if(message.includes(" has joined")){
		$("#messages").append("<p style='color: teal;'>" + message + "</p>");
		if(document.getElementById("guesses")!=null){
			stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': showPlayers(), 'messageType': 'allScores'+link}))
		}
	}else{
		let req = new XMLHttpRequest();
		req.open('GET', "https://www.purgomalum.com/service/xml?text="+message);
	  	req.onload = function() {
			end = message.indexOf(":");
			playerName = message.substring(0,end);
			$("#messages").append("<p onclick=\"blockPlayerMessage('"+playerName+"')\">" + this.responseText + "</p>");
	  	}
	  	req.send();
	}

	document.getElementById("messages").scroll({
		top: document.getElementById("messages").scrollHeight,
		behavior: 'smooth'
	});
}

function blockPlayerMessage(player){
	if(confirm("Kick "+player+" from the room?")){
		stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': player, 'messageType': 'blockPlayer'+link}))
	}
}

function blockPlayer(blockedPlayer){
	if(blockedPlayer==document.getElementById("sender").innerHTML){
		window.location.replace("/logout");
		alert("You have been kicked from the room!");
	}
}

function updateScores(allScores){
	if(allScores.length>3){populateMap(allScores);}	
}

function showEmojis(emojis) {
	try {
  		enableGuesses()
	}catch(error) {
	  	console.log("TypeError Warning");
	}
	$("#currentEmojiGroup").empty();
    $("#currentEmojiGroup").append(emojis);
}

function showFlag(flagCode) {
	try {
  		enableGuesses()
	}catch(error) {
	  	console.log("TypeError Warning");
	}
	$("#currentEmojiGroup").empty();
	$("#currentEmojiGroup").append("<img class='flag' src='https://flagcdn.com/"+flagCode+".svg'/>");
	correctFlag = countriesMap.get(flagCode);
	if(document.getElementById("countryBtns")!=null){
		document.getElementById("guess").style.display = "none";
		document.getElementById("countryAnswer").innerHTML = "Choose a country";
		$("#countryBtns").empty();
		var correctAnswer = Math.floor(Math.random() * 4);
		var answers = [];
		var flagCodes = [];
		while(answers.length<4){
			if(answers.length==correctAnswer){
				if(!answers.includes(countriesMap.get(flagCode))){
					answers.push(countriesMap.get(flagCode));
					flagCodes.push(flagCode);
				}
			}else{
				var randomIndex = getRandomIndex(countries.length-1);
				if(!answers.includes(countriesMap.get(countries[randomIndex][0]))){
					answers.push(countriesMap.get(countries[randomIndex][0]));
					flagCodes.push(countries[randomIndex][0]);
				}
			}
		}
		console.log("FLAG CODES = "+flagCodes);
		
		for(var i = 0; i<answers.length; i++){
			$("#countryBtns").append('<button class="flagGuessBtn" onclick="sendCountryGuess(\''+flagCodes[i]+'\',\''+flagCode+'\')" type="button">'+answers[i]+'</button>');
		}
	}
}

function getRandomIndex(maxIndex){
	return Math.floor(Math.random() * maxIndex);
}

function enableGuesses() {
	if(document.getElementById("countryBtns")==null){
		document.getElementById("guess").disabled = false;
		document.getElementById("guess").style.color = "black";
		document.getElementById("guess").value = "";
	}
}

function showGuess(guess) {
	end = guess.indexOf(":");
	player = guess.substring(0,end);
	
	if(document.getElementById("flagForm")!=null){
		var flagCode = guess.substring(end+2,guess.length);
		var playerGuess = countriesMap.get(flagCode);
	}
	
	if(document.getElementById("flagForm")!=null){
		autoScore(player, playerGuess);
		if(playerGuess==correctFlag){
			$("#guesses").append("<p style='color: green;'>" + guess + "</p>");
		}else{
			$("#guesses").append("<p style='color: red;'>" + guess + "</p>");
		}
	}else{
		let req = new XMLHttpRequest();
		req.open('GET', "https://www.purgomalum.com/service/xml?text="+guess);
	  	req.onload = function() {
			$("#guesses").append("<p><input class=\"checkbox\" type=\"checkbox\" id='"+player+"' onclick=\"handleCheck('"+player+"')\"/>" + this.responseText + "</p>");
	  	}
	  	req.send();
	}

	if(document.getElementById("guesses")!=null){
		document.getElementById("guesses").scroll({
			top: document.getElementById("guesses").scrollHeight,
			behavior: 'smooth'
		});
	}
}

function autoScore(player, guess) {
	playerScore = playerMap.get(player);
	if(playerScore===undefined){playerScore=0;}
	convertedScore = parseInt(playerScore);
	if(guess==correctFlag){
		playerMap.set(player, convertedScore+1*multiplier);
	}
	stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': player+":"+playerMap.get(player), 'messageType': 'score'+link}))
}

function handleMultiplier() {
	if(multiplier==1){
		multiplier=2;
		document.getElementById("emojiTitle").innerHTML = 'Score x 2';
	}else{
		multiplier=1;
		document.getElementById("emojiTitle").innerHTML = '';
	}
}

function showPlayers() {
	var scoresString="";
	$("#playerDiv").empty();
	sortedPlayers = [];
	playerMap.forEach(function(value, key) {
		sortedPlayers.push([key,value]);
	});
	sortedPlayers.sort((a, b) => b[1].localeCompare(a[1]));
	sortedPlayers.forEach((player) => {
		if(document.getElementById("guesses")!=null){
			$("#playerDiv").append("<p class='playerCard' onclick='overrideScore(\""+player[0]+"\")'>" + player[0] + ":" + player[1] + "</p>")	
		}else{
			$("#playerDiv").append("<p class='playerCard'>" + player[0] + ":" + player[1] + "</p>")	
		}
		scoresString+=player[0]+":"+player[1]+",";
	});
	
	if(scoresString){
		scoresString=scoresString.slice(0,scoresString.length-1);
		let xhttp = new XMLHttpRequest();
	  	xhttp.open("POST", "/update-scores");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("scores="+scoresString);
		return scoresString;
	}
	return scoresString;
}

function overrideScore(player) {
	if(confirm("Override player score?")){
		let score = prompt("Enter new score:");
		if(score===null){
			oldScore = playerMap.get(player);
			score = parseInt(oldScore);
		}
		playerMap.set(player, score);
		stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': player+":"+playerMap.get(player), 'messageType': 'score'+link}))
	}
}

function updatePlayers(message) {
	end = message.indexOf(":");
	playerName = message.substring(0,end);
	playerScore = message.substring(end+1,message.length);
	playerMap.set(playerName, playerScore);
	showPlayers();
}

function handleCheck(player) {
	var checkBox = document.getElementById(player);
	playerScore = playerMap.get(player);
	if(playerScore===undefined){playerScore=0;}
	convertedScore = parseInt(playerScore);
	if(checkBox.checked){
		playerMap.set(player, convertedScore+1*multiplier);
	}else{
		playerMap.set(player, convertedScore-1*multiplier);
	}
	stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': player+":"+playerMap.get(player), 'messageType': 'score'+link}))
}

function addPlayer(player) {
	playerScore = playerMap.get(player);
	if(playerScore===undefined){playerScore=0; playerMap.set(player, 0);}
	stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': player+":"+playerMap.get(player), 'messageType': 'score'+link}))
}

$("#messageForm").submit(function() {
	console.log("sending message");
	sender = document.getElementById("sender").innerHTML;
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': ""+sender+": "+document.getElementById("message").value, 'messageType': 'message'+link}));
    document.getElementById("message").value = "";
    return false;
});

$("#reviewQuestionForm").submit(function() {
	round++;
	console.log("sending question");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': document.getElementById("reviewQuestion").value, 'messageType': 'reviewQuestion'+link}));
    document.getElementById("reviewQuestion").value = "";
    
    $('#guesses').empty();
    return false;
});

$("#flagForm").submit(function() {
    showCountries(document.getElementById("flagSearch").value);
    
    $('#guesses').empty();
    return false;
});

$("#guessForm").submit(function() {
	console.log("sending guess");
	sender = document.getElementById("sender").innerHTML;
	guess = document.getElementById("guess").value;
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': ""+sender+": "+guess, 'messageType': 'guess'+link}));
    document.getElementById("guess").value = "Your guess was: "+guess;
    document.getElementById("guess").disabled = true;
    document.getElementById("guess").style.color = "lightblue";
    return false;
});

function sendCountryGuess(country, correctAnswer) {
	$("#countryBtns").empty();
	document.getElementById("guess").style.display = "block";
	document.getElementById("countryAnswer").innerHTML = "The correct answer was "+countriesMap.get(correctAnswer);
	console.log("sending guess");
	sender = document.getElementById("sender").innerHTML;
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': ""+sender+": "+country, 'messageType': 'guess'+link}));
    
    document.getElementById("guess").disabled = true;
    if(country==correctAnswer){
		document.getElementById("guess").value = "Correct! It is "+countriesMap.get(country)+"!";
		document.getElementById("guess").style.color = "lime";
	}else{
		document.getElementById("guess").value = "Your guess was: "+countriesMap.get(country);
		document.getElementById("guess").style.color = "lightblue";
	}
    return false;
}

function showGroup(emojis) {
	document.getElementById("emojiGroup").innerHTML = "";
	if(emojis.length>0){
		emojiList = emojis.split(",");
		emojiList.forEach(e => $("#emojiGroup").append('<button class="emoji" onclick="addEmoji(\''+e+'\')" type="button">'+e+'</button>'));
	}
}

function showCountries(search) {
	if(document.getElementById("countries")!=null){
		document.getElementById("countries").innerHTML = "";
		if(search.length>0){
			var filteredCountries = [];
			countries.forEach(c => {
				if(c[1].toLowerCase().includes(search)){
					filteredCountries.push(c);
				}
			});
			filteredCountries.forEach(c => $("#countries").append('<button class="flagBtn" onclick="sendFlag(\''+c[0]+'\', \''+c[1]+'\')" type="button">'+c[1].slice(0,32)+'</button>'));
		}else{
			countries.forEach(c => $("#countries").append('<button class="flagBtn" onclick="sendFlag(\''+c[0]+'\', \''+c[1]+'\')" type="button">'+c[1].slice(0,32)+'</button>'));
		}
		document.getElementById("flagSearch").value = "";
	}
}

function sendFlag(flagId, country) {
	$('#guesses').empty();
	round++;
	console.log("sending flags");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': flagId, 'messageType': 'flag'+link}));
    if(document.getElementById("currentFlag")!=null){
		document.getElementById("currentFlag").innerHTML = country;
	}
}

function addPreset(preset){
	$("#currentEmojis").empty();
	addEmoji(preset);
}

function addEmoji(emoji){
	console.log(emojiCount);
	if(emojiCount<12){
		$("#currentEmojis").append(emoji);
		emojiCount++;
	}else{
		alert("Maximum number of emojis reached.");
	}
}

function sendEmojis(){
	round++;
	emojiCount = 0;
	console.log("sending emojis");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': document.getElementById("currentEmojis").innerHTML, 'messageType': 'emoji'+link}));
    document.getElementById("currentEmojis").innerHTML = '';
    
    $('#guesses').empty();
}

function populateMap(sessionData) {
	scoresArray = sessionData.split(",");
	if(scoresArray.length>1){
		scoresArray.forEach(player => updatePlayers(player));
	}else if(scoresArray.length===1){
		updatePlayers(scoresArray[0])
	}
}

function clearEmojis(){
	emojiCount = 0;
    document.getElementById("currentEmojis").innerHTML = '';
}

function getFlagsList() {
	let req = new XMLHttpRequest();
	req.open('GET', "https://flagcdn.com/en/codes.json");
  	req.onload = function() {
		if (req.status == 200){
			const jsonData = JSON.parse(this.responseText);
			for(var countryCode in jsonData){
				if(countryCode.length==2){
					countries.push([countryCode, jsonData[countryCode]]);
					countriesMap.set(countryCode, jsonData[countryCode]);
				}
				if(countryCode.length>3 && countryCode[0]=='u'){
					states.push([countryCode, jsonData[countryCode]])
				}
			}
			showCountries("");
		}
  	}
  	req.send();
}
