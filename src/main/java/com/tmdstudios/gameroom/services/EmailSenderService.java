package com.tmdstudios.gameroom.services;

public interface EmailSenderService {
	void sendEmail(String to, String subject, String message);
}
