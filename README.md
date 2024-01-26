![Game Room Logo](/src/main/resources/static/images/GameRoom.png)

The purpose of Game Room is to provide educators with a platform to play games and/or review materials with their students.

This Spring Boot web app uses MySQL to store host and room data. Players are not saved to the database. User and player login is handled with HttpSession. Scores are also stored using HttpSession.

The rooms are meant to be used once. Hosts can only have one active room at any time. Once per day, rooms older than 24 hours are automatically deleted.

> Note: Project Cloud is currently not deployed

~~>You can see the website here: https://gameroom.up.railway.app/~~

## Key Features

- Hosts can create one of three game types (Emoji Game, Review, and Guess the Flag)
- All room logic is handled with JavaScript and Sockets
- A local chat is included in every game room
- Hosts can override scores at any time
- The scores table is automatically updated and displayed to the host and all players
- A profanity filter ensures that no inappropriate language is used in player names, player guesses, as well as the chat
- Hosts have the ability to kick users from the room


## You may also like...

[Study Room](https://github.com/TMDStudios/StudyRoom 'Study Room') - Open-source platform for students to improve their English vocabulary and grammar

[Mock Trader](https://github.com/TMDStudios/MockTrader 'Mock Trader') - Open-source Bitcoin trading game

[Crypto Ledger](https://play.google.com/store/apps/details?id=com.tmdstudios.cryptoledgerkotlin 'Crypto Ledger') - Open-source app for tracking cryptocurrency trades

[Py Learning Companion](https://play.google.com/store/apps/details?id=com.tmdstudios.python 'Py Learning Companion') - Python Study App

[TMD Studios](https://tmdstudios.github.io 'TMD Studios') - A simple one-page website to showcase TMD Studios projects.
