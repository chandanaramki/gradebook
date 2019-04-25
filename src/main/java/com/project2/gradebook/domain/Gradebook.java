package com.project2.gradebook.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="gradebook")
public class Gradebook {
	private int id; 
	private String title;
	private Boolean hasSecondary;
	private StudentList studenList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public StudentList getStudenList() {
		return studenList;
	}
	public void setStudenList(StudentList studenList) {
		this.studenList = studenList;
	}
}
