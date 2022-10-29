package com.tmdstudios.gameroom.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmdstudios.gameroom.models.Player;
import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.repositories.RoomRepo;

@Service
public class RoomService {
	@Autowired
	private RoomRepo roomRepo;
	
	public Room newRoom(Room room) {
		return roomRepo.save(room);
	}
	
	public Room updateRoom(Room room) {
		return roomRepo.save(room);
	}
	
	public Room findById(Long id) {
		return roomRepo.findByIdIs(id);
	}
	
	public Room findByLink(String link) {
		return roomRepo.findByLinkIs(link);
	}
	
	public List<Room> allRooms(){
		return roomRepo.findAll();
	}
	
	public Player findByName(String name) {
		return roomRepo.findByName(name);
	}
}
