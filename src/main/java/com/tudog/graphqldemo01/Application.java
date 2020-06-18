package com.tudog.graphqldemo01;

import com.tudog.graphqldemo01.config.ApiBeanRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		springApplication.addListeners(new ApiBeanRegistry());
		springApplication.run(args);
	}

}
