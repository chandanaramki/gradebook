package com.project2.gradebook.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

	@JacksonXmlRootElement(localName="student")
	public class Student {
		private Integer gradebook;
		private String name; 
		private String grade;
		private Boolean isSecondary;
		
		public Student() {
			
		}
		
		public Student(Integer gradebook, String name, String grade, Boolean isSecondary ) {
			this.gradebook = gradebook;
			this.name = name;
			this.grade = grade;
			this.isSecondary = isSecondary;
		}
		
		public Integer getGradeBook() {
			return gradebook;
		}
		
		public void setGradeBook(Integer gradebook) {
			this.gradebook = gradebook;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getGrade() {
			return grade;
		}
		
		public void setGrade(String grade) {
			this.grade = grade;
		} 
		
		public Boolean getIsSecondary() {
			return isSecondary;
		}
		
		public void setIsSecondary(Boolean isSecondary) {
			this.isSecondary = isSecondary;
		}
}