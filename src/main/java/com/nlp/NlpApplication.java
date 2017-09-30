package com.nlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nlp.common.ApiHolder;

@SpringBootApplication
public class NlpApplication {

	public static void main(String[] args) {
		ApiHolder.getInstance();
		SpringApplication.run(NlpApplication.class, args);
	}
}
