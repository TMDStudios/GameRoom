package com.tmdstudios.gameroom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmdstudios.gameroom.models.Message;
import com.tmdstudios.gameroom.services.WebSocketService;

@RestController
public class WebSocketController {
	@Autowired
    private WebSocketService service;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody final Message message) {
        service.notifyFrontend(message.getMessageContent());
    }
}
