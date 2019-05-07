package com.project2.gradebook.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.project2.gradebook.domain.Gradebook;
import com.project2.gradebook.domain.GradebookList;
import com.project2.gradebook.domain.Student;
import com.project2.gradebook.domain.StudentList;

@RestController
public class GradeBookController {
	
	static Map<Integer, Gradebook> primaryGradebookMap = new ConcurrentHashMap<Integer, Gradebook>();
	static Map<Integer, Gradebook> secondaryGradebookMap = new ConcurrentHashMap<Integer, Gradebook>();
	public static final String ORDINARY_USER ="ordinary";
	public static final String ADMIN_USER ="admin";
	public static final String HTTP = "http";
	public static final String LOCALHOST="localhost:";
	public static int ID_SEQ = 0;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//private static String secondaryServerPort1 = "8081"; // (PB) Not using this anymore, we can remove this if no one uses
	
	//(PB) Configuring these in run configurations.
	private static String secondaryServerPort = System.getProperty("server.secondaryPort");
	private static String currentServerPort = System.getProperty("server.port");
	private static String idSequencePort =  System.getProperty("server.gradebookIdPort");
	
	// (PB) Commented because I have configured these.
	/*
	 * //******SHOULD BE REMOVED LATER: WE ARE CONFIGIRING THIS IN RUN
	 * CONFIGURATIONS> static { if(currentServerPort.equals("8080")) {
	 * secondaryServerPort="8081"; }else if (currentServerPort.equals("8081")) {
	 * secondaryServerPort= "8080"; } } // changed to 8080
	 */
	
	@RequestMapping(value="/gradebookid",  method = RequestMethod.GET)
	public Integer gradeBookIdNextSeq() {
		return ID_SEQ = ID_SEQ + 1;
	}
	
	@RequestMapping(value="/hello",  method = RequestMethod.GET)
	public String displayhello() {
		return "Hello!!!";
	}
	
	@RequestMapping(value ="/gradebook", method = RequestMethod.GET, produces ="text/xml") 
	   public GradebookList getAllGradebooks() {
	
	//	System.out.println("IDSEQ" +GradebookIDSeqPort);
	//	System.out.println("check" +secondaryServerPort);
		GradebookList gradebookListObj = new GradebookList(); 
		gradebookListObj.gradebookList = new ArrayList<Gradebook>();
		for (Integer id : primaryGradebookMap.keySet()) { 
			Gradebook gradebook = primaryGradebookMap.get(id); 
			gradebookListObj.gradebookList.add(gradebook); 
			}
		return gradebookListObj;  
		}
	
	@RequestMapping(value ="/gradebook/{id}", method = RequestMethod.GET, produces ="text/xml") 
	   public ResponseEntity<?> getGradebook(@PathVariable("id") Integer id) {
	
	//	System.out.println("IDSEQ" +GradebookIDSeqPort);
	//	System.out.println("check" +secondaryServerPort);
		
		Gradebook gradebook = primaryGradebookMap.get(id);
		  if (gradebook != null) {
			  return new ResponseEntity<>(gradebook, HttpStatus.OK);  
		  }
		  return new ResponseEntity<String>("No Gradebook for the ID", HttpStatus.NO_CONTENT);
		  	
		}	
	
	@RequestMapping(value ="/gradebook/{name}", method={ RequestMethod.PUT, RequestMethod.POST})
	   public ResponseEntity<?> createGradeBook(@PathVariable("name") String name){
		
		StudentList studentList = new StudentList();
	//	System.out.println("check" +secondaryServerPort);
			if (!isValidTitle(name)) { 
				return new ResponseEntity<>("Invalid/Bad Request",HttpStatus.BAD_REQUEST); 
				  } else if(primaryGradebookMap != null && containsGradeBookTitle(primaryGradebookMap,name)) { 
					  return new ResponseEntity<>("The title already exists.",HttpStatus.BAD_REQUEST);
					  }
			String URI = HTTP+"://"+LOCALHOST+ secondaryServerPort +"/gradebook";
			GradebookList gradebooks = this.restTemplate.getForObject(URI, GradebookList.class);
			if(gradebooks != null && containsGradeBookTitle(gradebooks,name)) {
				return new ResponseEntity<>("The title already exists.",HttpStatus.BAD_REQUEST);
				}
		// getting next value of gradebook id 
			URI = HTTP+"://"+LOCALHOST+ idSequencePort +"/gradebookid";
			ID_SEQ = this.restTemplate.getForObject(URI, Integer.class);
				
			Gradebook gb = new  Gradebook(ID_SEQ, name, false, currentServerPort, studentList);
			primaryGradebookMap.put(ID_SEQ, gb);
			return new ResponseEntity<>("Gradebook added and the id of new gradebook is "+ID_SEQ,HttpStatus.OK);
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
	
	// Mapping to create Student record for gradebook

		@RequestMapping(value = "/gradebook/{id}/student/{name}/grade/{grade:[A-D][+-]?|[E-F]|I|W|Z}", produces = "application/xml", method = {RequestMethod.POST, RequestMethod.PUT })
		public ResponseEntity<?> createStudent(@PathVariable("id") @NotBlank Integer id, @PathVariable("name") @NotBlank String name, @PathVariable("grade") @NotBlank String grade, UriComponentsBuilder ucBuilder) {

			Student newStudentPrimary = new Student(id, name, grade, false);
			Student newStudentSecond = new Student(id, name, grade, true);
			RestTemplate restTemplate = new RestTemplate();

			// Validate if the Gradebook is a primary copy and if the ID is present.
			if (primaryGradebookMap != null && !containsGradeBookId(primaryGradebookMap, id, false)) {
				return new ResponseEntity<>("Gradebook does not exist or Gradebook is not a primary copy.",
						HttpStatus.BAD_REQUEST);
			}

			StudentList gradebookStudents = primaryGradebookMap.get(id).getStudentList();

			// Check if student present in student list
			for (Student student : gradebookStudents.studentList) {
				if (gradebookStudents.studentList != null && student.getName().equals(name)
						&& student.getGradeBook() == id) {
					gradebookStudents.studentList.set(gradebookStudents.studentList.indexOf(student),
							newStudentPrimary);

					// Update Secondary Copy
					
					String URI = HTTP + "://" + LOCALHOST + secondaryServerPort + "/gradebook/" + id;

					//if (isSecondaryServer()) { // For Your reference a Sample URI : String uri =
						// HTTP+"://"+LOCALHOST+secondaryServerPort +"/gradebook/"+id;
						Gradebook gradebook = this.restTemplate.getForObject(URI, Gradebook.class);
						if (gradebook != null && containsGradeBookId(gradebook, id, true)) {
							StudentList secondaryGradebookStudents = gradebook.getStudentList();
							for (Student secStudent : secondaryGradebookStudents.studentList) {
								if (gradebookStudents.studentList != null && secStudent.getName().equals(name)
										&& secStudent.getGradeBook() == id) {
									URI = HTTP + "://" + LOCALHOST + secondaryServerPort + "/gradebook/" + id + "/student/" + name + "/grade/"+ grade;
									restTemplate.put(URI, newStudentSecond);
								}
							}
						}
						HttpHeaders headers = new HttpHeaders();
						headers.setLocation(ucBuilder.path("/gradebook/{id}/student/{name}/grade/{grade}")
								.buildAndExpand(student.getGradeBook(), student.getName(), student.getGrade()).toUri());
						return new ResponseEntity<String>("Student " + name + " updated.", HttpStatus.OK);
					} 
				}

			// Creating new Student in Primary
			primaryGradebookMap.get(id).getStudentList().studentList.add(newStudentPrimary);

			// Create Secondary Copy
			String URI = HTTP + "://" + LOCALHOST + secondaryServerPort + "/gradebook/" + id;
			Gradebook gradebook = this.restTemplate.getForObject(URI, Gradebook.class);
			if (gradebook != null && containsGradeBookId(gradebook, id, true)) {
				URI = HTTP + "://" + LOCALHOST + secondaryServerPort + "/gradebook/" + id + "/student/" + name + "/grade/"+ grade;
				Student result = restTemplate.postForObject(URI, newStudentSecond, Student.class);
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/gradebook/{id}/student/{name}/grade/{grade}").buildAndExpand(
					newStudentPrimary.getGradeBook(), newStudentPrimary.getName(), newStudentPrimary.getGrade()).toUri());
			return new ResponseEntity<String>("Student " + name + " created.", HttpStatus.CREATED);
		}

		// Mapping to get all Students from within Gradebook

		@RequestMapping(value="/gradebook/{id}/student",method=RequestMethod.GET,produces="text/xml")
		public ResponseEntity<?> getAllStudent(@PathVariable("id") int id) {
		  
			// Check for usertype:must be ordinary 
			// allowed on primary and secondary both
			// Return list of students from a gradebook
		  
			Gradebook gradebook = primaryGradebookMap.get(id);
			if (gradebook != null) {
				StudentList studentList = gradebook.getStudentList();
				if (studentList != null) {
					return new ResponseEntity<>(studentList, HttpStatus.OK); 
				}
				return new ResponseEntity<String>("No Student Details for Gradebook", HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<String>("No Gradebook for the ID", HttpStatus.NO_CONTENT);
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
	
	public boolean containsGradeBookTitle(final Map<Integer, Gradebook> gradebookMap, final String title) {
		  for(Integer id : gradebookMap.keySet()) {
			  Gradebook gb = gradebookMap.get(id);
			  if(gb.getTitle().equalsIgnoreCase(title)) { 
				  return true; 
				  } 
			  }
		  return false; 
		  }
	
	public boolean containsGradeBookTitle(final GradebookList gradebooks, final String title){
		for(Gradebook gb : gradebooks.gradebookList) {
			if(gb.getTitle().equalsIgnoreCase(title)) {
				return true;
			}
		}
		
		return false;
	}
	
	// Validation method for Gradebook ID
		public boolean containsGradeBookId(final Gradebook gradebook, final Integer id, final boolean secondaryCopy) {
			if (gradebook.getId() == id && gradebook.getIsSecondaryCopy() == secondaryCopy) {
				return true;
			}
			return false;
		}

		// Validation method for Gradebook ID
		public boolean containsGradeBookId(final Map<Integer, Gradebook> gradebookMap, final Integer dgbId,
				final boolean secondaryCopy) {
			for (Integer id : gradebookMap.keySet()) {
				Gradebook gb = gradebookMap.get(id);
				if (gb.getId() == dgbId && gb.getIsSecondaryCopy() == secondaryCopy) {
					return true;
				}
			}
			return false;
		}
	
	public boolean isValidTitle(String title) {
		if(null != title && !Character.isWhitespace(title.charAt(0)))
			return true;
		else 
			return false;
	}	
}