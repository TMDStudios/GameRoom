package com.tmdstudios.gameroom.controllers;

import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tmdstudios.gameroom.models.LoginUser;
import com.tmdstudios.gameroom.models.Player;
import com.tmdstudios.gameroom.models.Room;
import com.tmdstudios.gameroom.models.User;
import com.tmdstudios.gameroom.services.EmojiService;
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
	
	@Autowired
	private EmojiService emojiService;
	
	String[] gameTypes = {"Emoji Game", "Review", "Guess the Flag"};
	
	private String[] banners = {
//			"https://tmdstudios.files.wordpress.com/2021/02/plclogolight.png?h=120",
			"https://tmdstudios.files.wordpress.com/2019/02/bitcoinbanner.png?h=120",
			"https://tmdstudios.files.wordpress.com/2022/12/shirtsetc-2.png?h=120"
			};
	
	private String[] links = {
//			"https://play.google.com/store/apps/details?id=com.tmdstudios.python",
			"https://freebitco.in/?r=15749838",
			"https://www.redbubble.com/people/shirtsetcetera/shop"
			};
	
	private void setBanner(HttpSession session) {
		int indexVal = new Random().nextInt(banners.length);
		session.setAttribute("banner", banners[indexVal]);
		session.setAttribute("link", links[indexVal]);
	}
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {	
		if(session.getAttribute("userId") != null) {
			Long userId = (Long) session.getAttribute("userId");		
			User user = userService.findById(userId);
			model.addAttribute("host", user);
			model.addAttribute("rooms", user.getRooms());
		}
	    return "index.jsp";
	}
	
	@GetMapping("/help")
	public String help() {	
	    return "help.jsp";
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
	
	@GetMapping("/reset-password/{token}")
	public String resetPassword(@PathVariable("token") String token, Model model) {
	    model.addAttribute("token", token);
	    return "password_reset.jsp";
	}
	
	@PostMapping("/reset-password/{token}")
	public String updatePassword(
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "pw") String pw,
			@RequestParam(value = "pwConfirm") String pwConfirm) {  
		if(pw.equals(pwConfirm)&&pw.length()>=8) {
			redirectAttributes.addFlashAttribute("message", "Your password has been reset!");
		}
	    return "redirect:/reset-password/{token}";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("userId", null);
		session.setAttribute("playerName", null);
		session.setAttribute("scores", null);
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
	public String newRoom(
			@ModelAttribute("room") Room room, 
			Model model, 
			HttpSession session,
			@RequestParam(value="type", required=false) String type) {
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/login";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.findById(userId);
		Boolean isHosting = user.getRooms().isEmpty() ? false : true;
		model.addAttribute("isHosting", isHosting);
		model.addAttribute("gameTypes", gameTypes);
		
		if(type==null) {
			type = "emoji";
		}
		
		switch(type) {
			case "review":
				model.addAttribute("type", "Review");
				break;
			case "flag":
				model.addAttribute("type", "Guess the Flag");
				break;
			default:
				model.addAttribute("type", "Emoji Game");
		}
		
		setBanner(session);
		
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
			model.addAttribute("gameTypes", gameTypes);
			return "new_room.jsp";
		}else {
			for(int i = 0; i<room.getName().length(); i++) {
				if(!Character.isLetter(room.getName().charAt(i))&&room.getName().charAt(i)!=' ') {
					redirectAttributes.addFlashAttribute("error", "Room Name can only contain letters.");
					return "redirect:/rooms/new";
				}
			}
			room.setHost(user);
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
		return "redirect:/rooms/"+room.getLink();
	}
	
	@GetMapping("/rooms/{roomLink}")
	public String viewRoom(
			HttpSession session, 
			Model model, 
			@PathVariable("roomLink") String roomLink, 
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		
//		String scores = (String) session.getAttribute("scores");
//		System.out.println(scores);
		
		setBanner(session);
		Room room = roomService.findByLink(roomLink);
		if(session.getAttribute("userId")==null&&session.getAttribute("playerName")==null) {
			model.addAttribute("roomLink", roomLink);
			return "join_room.jsp";
		}
		if(room!=null) {
			if(session.getAttribute("playerName")!=null) {
				String playerName = (String) session.getAttribute("playerName");
				Player player = playerService.findByName(playerName, room);
				if(player==null) {
					model.addAttribute("roomLink", roomLink);
					return "join_room.jsp";
				}
			}
			model.addAttribute("room", room);
			model.addAttribute("link", request.getRequestURL().toString());
			if(session.getAttribute("userId") != null) {
				Long userId = (Long) session.getAttribute("userId");		
				model.addAttribute("host", userService.findById(userId).getUsername());
			}
			model.addAttribute("preset", emojiService.preset());

			String[] smileys = emojiService.smileys();
			String[] gestures = emojiService.gestures();
			String[] people = emojiService.people();
			String[] clothing = emojiService.clothing();
			String[] general = emojiService.general();
			String[] animals = emojiService.animals();
			String[] food = emojiService.food();
			String[] activities = emojiService.activities();
			String[] travel = emojiService.travel();
			String[] objects = emojiService.objects();
			String[] symbols = emojiService.symbols();
			String[][] custom = {{"", "Select Custom Emojis"}, {smileys[1], smileys[0]}, {gestures[1], gestures[0]}, {people[1], people[0]},
					{clothing[1], clothing[0]}, {general[1], general[0]}, {animals[1], animals[0]}, {food[1], food[0]}, {activities[1], activities[0]},
					{travel[1], travel[0]}, {objects[1], objects[0]}, {symbols[1], symbols[0]}};
			model.addAttribute("custom", custom);
			
			return "view_room.jsp";
		}else {
			redirectAttributes.addFlashAttribute("error", "Room not found!");
			return "redirect:/rooms/join";
		}
	}
	
	@GetMapping("/guesses/")
	public String guessMessages() {	
		return "host_frame.jsp";
	}
	
	@GetMapping("/room-messages/")
	public String roomMessages() {	
		return "room_messages.jsp";
	}
	
	@GetMapping("/rooms/join")
	public String joinRoom(HttpSession session) {
		setBanner(session);
		String playerName = (String) session.getAttribute("playerName");
		Long roomId = (Long) session.getAttribute("roomId");
		Room room = roomService.findById(roomId);
		if(room!=null) {
			Player player = playerService.findByName(playerName, room);
			if(player!=null) {
				return "redirect:/rooms/"+room.getLink();
			}
		}
		
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
		
		roomLink = roomLink.toLowerCase();
		Room room = roomService.findByLink(roomLink);
		if(room!=null) {
			if(room.getPrivateRoom()) {
				session.setAttribute("roomId", room.getId());
				session.setAttribute("playerName", playerName);
				return "join_private_room.jsp";
			}
			Player player = playerService.findByName(playerName, room);
			if(player!=null) {
				if(room.getPlayers().contains(player)) {
					redirectAttributes.addFlashAttribute("error", "Please choose a different Player Name.");
					// Find better solution?
					redirectAttributes.addFlashAttribute("roomLink", roomLink);
					return "redirect:/rooms/join";
				}
			}
			for(int i = 0; i<playerName.length(); i++) {
				if(!Character.isLetter(playerName.charAt(i))) {
					redirectAttributes.addFlashAttribute("error", "Player Name can only contain letters.");
					return "redirect:/rooms/join";
				}
			}
			player = new Player(playerName, room);
			playerService.newPlayer(player);
			session.setAttribute("playerName", player.getName());
			session.setAttribute("myRoom", room.getLink());
			session.setAttribute("myRoomName", room.getName());
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
				session.setAttribute("myRoom", room.getLink());
				session.setAttribute("myRoomName", room.getName());
				model.addAttribute("room", room);
				return "redirect:/rooms/"+room.getLink();
			}
			redirectAttributes.addFlashAttribute("error", "Wrong password");
			return "redirect:/rooms/join";
		}
		redirectAttributes.addFlashAttribute("error", "Password must be 6 to 12 characters");
		return "redirect:/rooms/join";
	}
	
	@GetMapping(value="/.well-known/brave-rewards-verification.txt", produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String batVerification() {
		return "This is a Brave Rewards publisher verification file.\r\n"
				+ "\r\n"
				+ "Domain: railway.app\r\n"
				+ "Token: e07c0bcc94e8ace54daad1dbb51ec6dde14797ea93d27810f8e57d6fc4321b56";
	}
}
