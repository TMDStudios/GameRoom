FROM openjdk:17
COPY /target/GameRoom-0.0.1-SNAPSHOT.war GameRoom.war
ENTRYPOINT ["java","-jar","GameRoom.war"]