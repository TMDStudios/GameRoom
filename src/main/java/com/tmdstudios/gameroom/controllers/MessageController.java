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
	
	@MessageMapping("/emoji")
    @SendTo("/topic/emojis")
    public ResponseMessage getEmojis(final Message message) throws InterruptedException {
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }
	
	@MessageMapping("/guess")
    @SendTo("/topic/guesses")
    public ResponseMessage getGuesses(final Message message) throws InterruptedException {
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }
	
	@MessageMapping("/players")
    @SendTo("/topic/players")
    public ResponseMessage getPlayers(final Message message) throws InterruptedException {
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }

}
