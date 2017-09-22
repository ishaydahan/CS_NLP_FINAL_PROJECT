package com.nlp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nlp.common.Tools;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		Tools.getInstance();
		SpringApplication.run(Application.class, args);
	}
}
