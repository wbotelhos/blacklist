package com.r7.blacklist.server.model;

public class Blacklist {

	private Long id;

	private String word;

	public Blacklist(Long id, String word) {
		this.id = id;
		this.word = word;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
