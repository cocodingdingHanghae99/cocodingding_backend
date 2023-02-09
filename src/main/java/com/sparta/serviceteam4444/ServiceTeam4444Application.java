package com.sparta.serviceteam4444;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ServiceTeam4444Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceTeam4444Application.class, args);
	}

}
