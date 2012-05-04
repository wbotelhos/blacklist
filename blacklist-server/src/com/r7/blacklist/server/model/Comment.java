package com.r7.blacklist.server.model;

import java.util.Date;

public class Comment {

	private Long id;

	private String censured;

	private String original;

	private Boolean valid;

	private Date createdAt;

	public Comment() {
		
	}

	public Comment(Long id, String censured, String original, Boolean valid, Date createdAt) {
		this.id = id;
		this.censured = censured;
		this.original = original;
		this.valid = valid;
		this.createdAt = createdAt;
	}

	public Boolean isValid() {
		return (valid == null) ? false : valid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getCensured() {
		return censured;
	}

	public void setCensured(String censured) {
		this.censured = censured;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
