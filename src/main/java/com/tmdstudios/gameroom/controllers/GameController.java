package com.tmdstudios.gameroom.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.models.User;
import com.tmdstudios.gameroom.services.RoomService;
import com.tmdstudios.gameroom.services.UserService;

@RestController
public class GameController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoomService roomService;
	
	@RequestMapping("/update-scores")
	public String updateScores(String scores, HttpSession session) {
		session.setAttribute("scores", scores);
		return "UPDATED";
	}
	
	@RequestMapping("/get-scores")
	public String getScores(HttpSession session) {
		String scores = (String) session.getAttribute("scores");
		session.setAttribute("scores", scores);
		return scores;
	}
	
	@RequestMapping("/delete-last-room")
	public String deleteLastRoom(HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");		
		User user = userService.findById(userId);
		for(Room room : user.getRooms()) {
			roomService.deleteRoom(room);
		}
		session.setAttribute("scores", null);
		return "ROOMS DELETED";
	}
}