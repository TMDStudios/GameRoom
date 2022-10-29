package com.tmdstudios.gameroom.controllers;

import javax.servlet.http.HttpServletRequest;
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

import com.tmdstudios.gameroom.models.Player;
import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.services.PlayerService;
import com.tmdstudios.gameroom.services.RoomService;

@Controller
public class MainController {
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private PlayerService playerService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		model.addAttribute("rooms", roomService.allRooms());
		model.addAttribute("players", playerService.allPlayers());
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
	
	@GetMapping("/rooms/{roomLink}")
	public String viewRoom(
			HttpSession session, 
			Model model, 
			@PathVariable("roomLink") String roomLink, 
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		Room room = roomService.findByLink(roomLink);
		if(room!=null) {
			model.addAttribute("room", room);
			model.addAttribute("link", request.getRequestURL().toString());
			return "view_room.jsp";
		}else {
			redirectAttributes.addFlashAttribute("error", "Room not found!");
			return "redirect:/rooms/join";
		}
	}
	
	@GetMapping("/rooms/join")
	public String joinRoom() {
		return "join_room.jsp";
	}
	
	@PostMapping("/rooms/join")
	public String joinRoom(
			@RequestParam(value="roomLink", required=false) String roomLink, 
			RedirectAttributes redirectAttributes, 
			@RequestParam(value="playerName") String playerName, 
			Model model,
			HttpSession session) {
		if(playerName.length()<3||playerName.length()>24) {
			redirectAttributes.addFlashAttribute("error", "Player Name must be between 3 and 24 characters long.");
			return "redirect:/rooms/join";
		}
		
		Room room = roomService.findByLink(roomLink);
		if(room!=null) {
			Player player = playerService.findByName(playerName, room);
			if(player!=null) {
				if(room.getPlayers().contains(player)) {
					redirectAttributes.addFlashAttribute("error", "Please choose a different Player Name.");
					return "redirect:/rooms/join";
				}
			}
			player = new Player(playerName, room);
			playerService.newPlayer(player);
			session.setAttribute("playerName", player.getName());
			model.addAttribute("room", room);
			return "redirect:/rooms/"+roomLink;
		}else {
			redirectAttributes.addFlashAttribute("error", "Room not found!");
			return "redirect:/rooms/join";
		}
	}
}
