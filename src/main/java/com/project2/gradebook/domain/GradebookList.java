package com.project2.gradebook.domain;

import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="grade-list")
public class GradebookList {
	@JacksonXmlProperty(localName="gradebook")
	@JacksonXmlElementWrapper(useWrapping=false)
	public List<Gradebook> gradebookList;
}

