package com.project2.gradebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.project2.gradebook.configuration.EmbeddedTomcatConfiguration;


@SpringBootApplication
@Import({ EmbeddedTomcatConfiguration.class})
public class Application{
	public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
 }

}