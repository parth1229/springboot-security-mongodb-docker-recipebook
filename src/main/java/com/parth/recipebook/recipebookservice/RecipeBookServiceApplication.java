package com.parth.recipebook.recipebookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RecipeBookServiceApplication {

	@Bean
	public RestTemplate getRestTemplateBean(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(RecipeBookServiceApplication.class, args);
	}

}
