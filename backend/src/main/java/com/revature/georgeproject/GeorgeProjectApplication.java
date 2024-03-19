package com.revature.georgeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(scanBasePackages = {
		"com.revature.georgeproject.controllers",
		"com.revature.georgeproject.models",
		"com.revature.georgeproject.services",
		"com.revature.georgeproject.repositories"},
		exclude = {DataSourceAutoConfiguration.class}
)
@EnableMongoAuditing
public class GeorgeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeorgeProjectApplication.class, args);
	}

}
