package com.tmdstudios.gameroom.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmdstudios.gameroom.models.RoomLog;
import com.tmdstudios.gameroom.repositories.RoomLogRepo;

@Service
public class RoomLogService {
	@Autowired
	RoomLogRepo roomLogRepo;
	
	public RoomLog addRoomLog(RoomLog roomLog) {
		System.out.println("ADD ROOM LOG");
		return roomLogRepo.save(roomLog);
	}
	
	public List<RoomLog> getRoomLog() {
		System.out.println("GET ROOM LOG");
		return roomLogRepo.findAll();
	}
}
