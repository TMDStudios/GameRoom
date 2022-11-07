package com.tmdstudios.gameroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GameRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameRoomApplication.class, args);
	}

}
