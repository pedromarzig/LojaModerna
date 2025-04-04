package com.example.LojaModerna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.example.LojaModerna.Repositories")
@SpringBootApplication
public class LojaModernaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaModernaApplication.class, args);
	}

}
