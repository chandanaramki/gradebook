package com.project2.gradebook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/gradebook")
public class GradeBookController {

	@RequestMapping(value="/hello",  method = RequestMethod.GET)
	public String displayhello() {
		return "Hello!!!";
	}
}
	