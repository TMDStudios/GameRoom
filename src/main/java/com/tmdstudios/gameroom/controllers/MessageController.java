package com.tmdstudios.gameroom.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.tmdstudios.gameroom.models.Message;
import com.tmdstudios.gameroom.models.ResponseMessage;

@Controller
public class MessageController {
	
	@MessageMapping("/message")
    @SendTo("/topic/messages")
    public ResponseMessage getMessage(final Message message) throws InterruptedException {
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }

}
