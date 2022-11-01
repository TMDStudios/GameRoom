package com.tmdstudios.gameroom.controllers;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmdstudios.gameroom.services.EmailSenderService;
import com.tmdstudios.gameroom.services.UserService;

@RestController
public class UserController {
	
	private final EmailSenderService emailSenderservice;
	
	public UserController(EmailSenderService emailSenderService) {
		this.emailSenderservice = emailSenderService;
	}
	
	private static final String EMAIL_MESSAGE = "Click on the following link to reset your password: ";

	@Autowired
	private UserService userService;
	
	@PostMapping("/api/reset-request")
	public String resetRequest(@RequestParam String email) {
		String newToken = userService.forgotPassword(email);
		
		if(!newToken.startsWith("Invalid")) {
			newToken = "http://localhost:8080/reset-password/" + newToken;
			this.emailSenderservice.sendEmail(
					email, 
					"Game Room Password Reset Request", 
					EMAIL_MESSAGE+newToken);
		}

		return newToken;
	}
	
	@PutMapping("/api/reset-password")
	public String resetPassword(
			@RequestParam String token, 
			@RequestParam String email, 
			@RequestParam String password) throws AddressException, MessagingException, IOException {

		return userService.resetPassword(token, password);
	}

}