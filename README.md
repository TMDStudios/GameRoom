# GameRoom

![Railway Badge](https://img.shields.io/badge/deployment-railway-purple)

![Game Room Logo](/src/main/resources/static/images/GameRoom.png)

The purpose of Game Room is to provide educators with a platform to play games and/or review materials with their students.

This Spring Boot website uses MySQL to store host and room data. Players are not saved to the database. User and player login is handled with HttpSession. Scores are also stored using HttpSession.

The rooms are meant to be used once. Hosts can only have one active room at any time. Once per day, rooms older than 24 hours are automatically deleted.

>You can see the website here: https://gameroom.up.railway.app/

## Key Features

- Hosts can create one of three game types (Emoji Game, Review, and Guess the Flag)
- All room logic is handled with JavaScript and Sockets
- A local chat is included in every game room
- Hosts can override scores at any time
- The scores table is automatically updated and displayed to the host and all players


## You may also like...

[Mock Trader](https://github.com/TMDStudios/MockTrader 'Mock Trader') - Open-source Bitcoin trading game

[Crypto Ledger](https://play.google.com/store/apps/details?id=com.tmdstudios.cryptoledgerkotlin 'Crypto Ledger') - Open-source app for tracking cryptocurrency trades

[Py Learning Companion](https://play.google.com/store/apps/details?id=com.tmdstudios.python 'Py Learning Companion') - Python Study App
