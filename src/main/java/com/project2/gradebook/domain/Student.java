package com.project2.gradebook.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

	@JacksonXmlRootElement(localName="student")
	public class Student {
		private String name; 
		private String grade;
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
}