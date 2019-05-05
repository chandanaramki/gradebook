package com.project2.gradebook.domain;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.project2.gradebook.domain.Student;


@JacksonXmlRootElement(localName="student-list")
public class StudentList {
	
	@JacksonXmlProperty(localName="student")
	@JacksonXmlElementWrapper(useWrapping=false)
	private List<Student> studentList = new ArrayList<Student>();
	
	public List<Student> getStudents(){
		return studentList;
	}
	
	public void setStudents(List<Student> studentList) {
		this.studentList = studentList;
	}
	
}	
