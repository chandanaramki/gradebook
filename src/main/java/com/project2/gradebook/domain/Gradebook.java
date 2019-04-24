package com.project2.gradebook.domain;

public class Gradebook {
	private String id; 
	private String title;
	private Boolean hasSecondary;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getHasSecondary() {
		return hasSecondary;
	}
	public void setHasSecondary(Boolean hasSecondary) {
		this.hasSecondary = hasSecondary;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
