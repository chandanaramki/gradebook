package com.project2.gradebook.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project2.gradebook.domain.Gradebook;
import com.project2.gradebook.domain.GradebookList;
import com.project2.gradebook.domain.Student;
import com.project2.gradebook.domain.StudentList;

@RestController
@RequestMapping(value ="/gradebook")
public class GradeBookController {
	
	static Map<String, Gradebook> primaryGradebookMap = new ConcurrentHashMap<String, Gradebook>();
	public static final String ORDINARY_USER ="ordinary";
	public static final String ADMIN_USER ="admin";
	
	@RequestMapping(value="/hello",  method = RequestMethod.GET)
	public String displayhello() {
		return "Hello!!!";
	}
	
	@RequestMapping(value ="/gradebook ", method = RequestMethod.GET,  produces = "text/xml")
	public GradebookList getAllGradebooks() {
		GradebookList gradebookListObj = new GradebookList();
		//Check for usertype:must be ordinary
		//Return list of gradebooks from primary
		return gradebookListObj;
	}
	
	@RequestMapping(value ="/gradebook/{name}", method={ RequestMethod.PUT, RequestMethod.POST})
	public int createGradebook(@PathVariable("name") String name) {
		Gradebook newGradebook = new Gradebook();
		//Check if title already exists.
		//Check if its primary server.
		//check if title name given starts with whitespace character.
		//Check for usertype:must be ordinary
		// if PUT then update secondary Copy Too ..If there is any
		return 1;
	}
	
	@RequestMapping(value ="/gradebook/{id}", method = RequestMethod.DELETE)
	public void deleteGradebook(@PathVariable("id") String id) {
		//Check if its primary server.
		//check if grdebook exists
		//removes all copies of the GradeBook
		//Check for usertype:must be admin
	}
	
	@RequestMapping(value ="/secondary/{id}", method={ RequestMethod.PUT, RequestMethod.POST})
	public int createSecondaryCopy(@PathVariable("id") int id) {
		Gradebook newGradebook = new Gradebook();
		//Check if title already exists on secondary.
		//Check if its primary server.
		//check if title exists in primary or not.
		//Check for usertype:must be Admin
		//
		return 1;
	}
	
	@RequestMapping(value ="/secondary/{id}", method=RequestMethod.DELETE)
	public void deleteSecondaryCopy(@PathVariable("id") int id) {
		//Check if its primary server.
		//Check for usertype:must be Admin
		////Check if title already exists on secondary.
	}
	
	@RequestMapping(value ="/gradebook/{id}/student/{name}/grade/{grade}", method={ RequestMethod.PUT, RequestMethod.POST})
	public int createStudent(@PathVariable("id") int id,@PathVariable("name") String name, @PathVariable("grade") String grade) {
		Student newStudent = new Student();
		//Check if student name already exists.
		//Check if its primary server.
		//validate given gradebookID
		//Check for usertype:must be ordinary
		// if PUT then update secondary Copy Too..{Check if gradebook exists and student exists}
		return 1;
	}
	
	@RequestMapping(value ="/gradebook/{id}/student ", method = RequestMethod.GET,  produces = "text/xml")
	public StudentList getAllStudent(@PathVariable("id") int id) {
		StudentList studentList = new StudentList();
		//Check for usertype:must be ordinary
		//allowed on primary and secondary both
		//Return list of students from a gradebook
		return studentList;
	}
	
	
	@RequestMapping(value ="/gradebook/{id}/student/{name}", method=RequestMethod.DELETE)
	public void deleteStudent(@PathVariable("id") int id,@PathVariable("name") String name) {
		//Check if its primary server.
		//Check for usertype:must be Admin
		////Check if title already exists on secondary.
	}
	
	@RequestMapping(value ="/gradebook/{id}/student/{name}", method=RequestMethod.GET)
	public Student getStudent(@PathVariable("id") int id, @PathVariable("name") String name) {
		//Check if its primary server.
		//Check for usertype:must be Admin
		//Check if there is a record with that gradebookid and student name
		return new Student(); //Remove this later
	}
	
	public Boolean isAdminUser(String userType) {
		Boolean result = false;
		if(userType.equalsIgnoreCase(ADMIN_USER)){
			result = true;
		}
		return result;
	}
	
	public boolean isValidGrade(String letter) {
		if(letter.equalsIgnoreCase("A")||letter.equalsIgnoreCase("B")||letter.equalsIgnoreCase("C")
				||letter.equalsIgnoreCase("D")||letter.equalsIgnoreCase("E") ||letter.equalsIgnoreCase("F") ||letter.equalsIgnoreCase("I") 
				||letter.equalsIgnoreCase("W") ||letter.equalsIgnoreCase("Z")||letter.equalsIgnoreCase("A+") ||letter.equalsIgnoreCase("A-")
				||letter.equalsIgnoreCase("B+")||letter.equalsIgnoreCase("B-")||letter.equalsIgnoreCase("C+")||letter.equalsIgnoreCase("C-")
				||letter.equalsIgnoreCase("D+")||letter.equalsIgnoreCase("D-"))
			return true;
		else 
			return false;
	}
	
	
}
	