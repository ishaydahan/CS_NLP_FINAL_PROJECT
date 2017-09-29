package com.nlp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nlp.common.ApiHolder;

@SpringBootApplication
public class NlpApplication {

	public static void main(String[] args) {
		SpringApplication.run(NlpApplication.class, args);
		ApiHolder.getInstance();
	}
}
