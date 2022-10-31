package com.tmdstudios.gameroom.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.tmdstudios.gameroom.models.LoginUser;
import com.tmdstudios.gameroom.models.Player;
import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.models.User;
import com.tmdstudios.gameroom.services.PlayerService;
import com.tmdstudios.gameroom.services.RoomService;
import com.tmdstudios.gameroom.services.UserService;

@Controller
public class MainController {
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		// Rooms are automatically deleted after 24 hours
		for(Room room:roomService.allRooms()) {
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
		
		model.addAttribute("rooms", roomService.allRooms());
		model.addAttribute("players", playerService.allPlayers());
	    return "index.jsp";
	}
	
	@GetMapping("/login")
	public String authLogin(HttpSession session, Model model) {
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
		
	    return "login.jsp";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
			BindingResult result, Model model, HttpSession session) {
	     
		User user = userService.login(newLogin, result);
	 
	    if(result.hasErrors() || user==null) {
	        model.addAttribute("newUser", new User());
	        return "login.jsp";
	    }
	     
	    session.setAttribute("userId", user.getId());
	 
	    return "redirect:/";
	}
	
	@GetMapping("/register")
	public String authRegister(HttpSession session, Model model) {
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
	    
	    return "register.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, 
			BindingResult result, Model model, HttpSession session) {

	    User user = userService.register(newUser, result);
	     
	    if(result.hasErrors()) {
	        model.addAttribute("newLogin", new LoginUser());
	        return "register.jsp";
	    }

	    session.setAttribute("userId", user.getId());
	 
	    return "redirect:/";
	}
	
	@GetMapping("/rooms/new")
	public String newRoom(@ModelAttribute("room") Room room, Model model, HttpSession session) {
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/login";
		}
		
		String[] gameTypes = {"Emoji Game", "Game Type 2", "Game Type 3", "Game Type 4"};
		model.addAttribute("gameTypes", gameTypes);
		return "new_room.jsp";
	}
	
	@PostMapping("/rooms/new")
	public String createRoom(
			@Valid @ModelAttribute("room") Room room, 
			BindingResult result, 
			HttpSession session, 
			Model model,
			RedirectAttributes redirectAttributes) {
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/login";
		}
		
		Long userId = (Long) session.getAttribute("userId");		
		User user = userService.findById(userId);
		
		if(result.hasErrors()) {
			return "new_room.jsp";
		}else {
			if(room.getPrivateRoom()) {
				if(room.getPassword().length()<6||room.getPassword().length()>12) {
					redirectAttributes.addFlashAttribute("error", "Private rooms must have a password of 6 to 12 characters.");
					return "redirect:/rooms/new";
				}else {
					roomService.newRoom(room);
				}
			}else {
				roomService.newRoom(room);
			}
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
			if(room.getPrivateRoom()) {
//				model.addAttribute("roomId", room.getId());
				session.setAttribute("roomId", room.getId());
				session.setAttribute("playerName", playerName);
				return "join_private_room.jsp";
			}
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
	
	@GetMapping("/rooms/join-private")
	public String joinPrivateRoom() {
		return "join_private_room";
	}
	
	@PostMapping("/rooms/join-private")
	public String joinPrivateRoom(
			@RequestParam(value="roomPassword") String roomPassword, 
			RedirectAttributes redirectAttributes, 
			Model model,
			HttpSession session) {
		if(roomPassword.length()>0) {
			Long roomId = (Long) session.getAttribute("roomId");
			if(roomPassword.equals(roomService.findById(roomId).getPassword())) {
				Room room = roomService.findById(roomId);
				String playerName = (String) session.getAttribute("playerName");
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
				return "redirect:/rooms/"+room.getLink();
			}
			redirectAttributes.addFlashAttribute("error", "Wrong password");
			return "redirect:/rooms/join";
		}
		redirectAttributes.addFlashAttribute("error", "Password must be 6 to 12 characters");
		return "redirect:/rooms/join";
	}
	
	public void deleteRoom(Room room) {
		System.out.println("DELETING ROOM: "+room.getName());
		roomService.deleteRoom(room);
	}
}
