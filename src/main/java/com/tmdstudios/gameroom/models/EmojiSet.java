package com.tmdstudios.gameroom.models;

public class EmojiSet {
	String name;
	String emojis;
	public EmojiSet() {}
	public EmojiSet(String name, String emojis) {
		this.name = name;
		this.emojis = emojis;
	}
	public String getName() {
		return name;
	}
	public String getEmojis() {
		return emojis;
	}
}
