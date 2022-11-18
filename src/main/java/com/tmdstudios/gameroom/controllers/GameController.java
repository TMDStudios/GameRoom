package com.tmdstudios.gameroom.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
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
}