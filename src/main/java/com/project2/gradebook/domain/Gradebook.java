package com.project2.gradebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="gradebook")
public class Gradebook {
	private Integer id; 
	private String title;
	private String createdServer;
	private Boolean isSecondaryCopy;
	private StudentList studentList;
	                                                                                   
	public Gradebook() {
		super();
	}
	
	public Gradebook(Integer id, String title, Boolean isSecondaryCopy, String createdServer, StudentList studentList) {
		this.id = id;
		this.title = title;
		this.isSecondaryCopy = isSecondaryCopy;
		this.createdServer = createdServer;
		this.studentList = studentList;
	}
	
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public StudentList getStudentList() {
		return studentList;
	}
	public void setStudentList(StudentList studentList) {
		this.studentList = studentList;
	}
	public String getCreatedServer() {
		return createdServer;
	}

	public void setCreatedServer(String createdServer) {
		this.createdServer = createdServer;
	}

	public Boolean getIsSecondaryCopy() {
		return isSecondaryCopy;
	}

	public void setIsSecondaryCopy(Boolean isSecondaryCopy) {
		this.isSecondaryCopy = isSecondaryCopy;
	}
}