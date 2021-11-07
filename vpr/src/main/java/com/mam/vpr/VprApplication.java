package com.mam.vpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class VprApplication {

	public static void main(String[] args) {
		SpringApplication.run(VprApplication.class, args);
	}

}
