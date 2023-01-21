package com.tmdstudios.gameroom.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmdstudios.gameroom.models.Player;
import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.models.RoomLog;
import com.tmdstudios.gameroom.repositories.RoomRepo;

@Service
public class RoomService {
	@Autowired
	private RoomRepo roomRepo;
	
	@Autowired
	private RoomLogService roomLogService;
	
	@PostConstruct
//	@Scheduled(cron = "0 0 0 * * *")
	// Each day at midnight, all rooms older than 24 hours are deleted
	private void deleteOldRooms() {
		for(Room room:allRooms()) {
			try {
				String startDate = room.getCreatedAt().toString();
				long today = new Date().getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date roomDate = sdf.parse(startDate);
				long roomMillis = roomDate.getTime();
				System.out.println(room.getName()+" - ALIVE FOR - "+(today-roomMillis));
				if(today-roomMillis>86400000) {
					deleteRoom(room);
				}
			}catch(ParseException e) {
				System.out.println("ISSUE: "+e);
			}
		}
	}
	
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
	
	public void deleteRoom(Room room) {
		roomLogService.addRoomLog(new RoomLog(
				room.getName(), 
				room.getHost().getUsername(), 
				room.getGameType(), 
				room.getPlayers().size(), 
				room.getCreatedAt(), 
				new Date()));
		System.out.println("DELETING ROOM: "+room.getName());
		roomRepo.delete(room);
	}
}
