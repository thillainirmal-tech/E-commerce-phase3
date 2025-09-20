package com.example.minor_project_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MinorProject01Application {

	public static void main(String[] args) {

		SpringApplication.run(MinorProject01Application.class, args);
	}

}
