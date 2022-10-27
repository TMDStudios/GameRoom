package com.tmdstudios.gameroom.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.services.RoomService;

@Controller
public class MainController {
	@Autowired
	private RoomService roomService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		model.addAttribute("rooms", roomService.allRooms());
	    return "index.jsp";
	}
	
	@GetMapping("/rooms/new")
	public String newRoom(@ModelAttribute("room") Room room) {
		return "new_room.jsp";
	}
	
	@PostMapping("/rooms/new")
	public String createRoom(@Valid @ModelAttribute("room") Room room, BindingResult result, HttpSession session, Model model) {
		if(result.hasErrors()) {
			return "new_room.jsp";
		}else {
			roomService.newRoom(room);
		}
		return "redirect:/";
	}
	
	@GetMapping("/rooms/{roomId}")
	public String viewRoom(HttpSession session, Model model, @PathVariable("roomId") String roomId) {
		model.addAttribute("room", roomService.findByLink(roomId));
		return "view_room.jsp";
	}
	
	@GetMapping("/rooms/join")
	public String joinRoom() {
		return "join_room.jsp";
	}
	
	@PostMapping("/rooms/join")
	public String joinRoom(@RequestParam(value="roomLink", required=false) String roomLink, RedirectAttributes redirectAttributes) {
		if(roomService.findByLink(roomLink)!=null) {
			return "redirect:/";
		}else {
			redirectAttributes.addFlashAttribute("error", "Room not found!");
			return "redirect:/rooms/join";
		}
	}
}
