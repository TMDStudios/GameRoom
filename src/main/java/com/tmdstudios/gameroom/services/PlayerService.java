package com.tmdstudios.gameroom.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmdstudios.gameroom.models.Player;
import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.repositories.PlayerRepo;

@Service
public class PlayerService {
	@Autowired
	PlayerRepo playerRepo;
	
	public Player newPlayer(Player player) {
		System.out.println("NEW PLAYER");
		return playerRepo.save(player);
	}
	
	public Player findById(Long id) {
		System.out.println("FIND PLAYER");
		return playerRepo.findByIdIs(id);
	}
	
	public List<Player> allPlayers(){
		System.out.println("ALL PLAYERS");
		return playerRepo.findAll();
	}
	
	public Player findByName(String name, Room room) {
		System.out.println("FIND (BY NAME) PLAYER");
		return playerRepo.findByNameIsAndRoomIs(name, room);
	}
	
	public void deletePlayer(Player player) {
		System.out.println("DELETE PLAYER");
		playerRepo.delete(player);
	}
}
