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
		return playerRepo.save(player);
	}
	
	public Player findById(Long id) {
		return playerRepo.findByIdIs(id);
	}
	
	public List<Player> allPlayers(){
		return playerRepo.findAll();
	}
	
	public Player findByName(String name, Room room) {
		return playerRepo.findByNameIsAndRoomIs(name, room);
	}
	
	public void deletePlayer(Player player) {
		playerRepo.delete(player);
	}
}
