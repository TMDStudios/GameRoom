package com.tmdstudios.gameroom.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
	    return "index.jsp";
	}
}
