package com.tmdstudios.gameroom.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.repositories.RoomRepo;

@Service
public class RoomService {
	@Autowired
	private RoomRepo roomRepo;
	
	public Room findById(Long id) {
		return roomRepo.findByIdIs(id);
	}
	
	public List<Room> allRooms(){
		return roomRepo.findAll();
	}
}
