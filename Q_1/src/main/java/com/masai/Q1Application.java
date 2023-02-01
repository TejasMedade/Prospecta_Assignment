package com.masai;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Q1Application {

	public static void main(String[] args) {
		SpringApplication.run(Q1Application.class, args);
	}

	// Model Mapper Bean
	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();

	}
	
	//Rest Template Bean
	@Bean
	public RestTemplate restTemplate() {

		return new RestTemplate();
	}

}
