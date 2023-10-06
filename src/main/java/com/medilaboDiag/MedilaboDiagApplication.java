package com.medilaboDiag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedilaboDiagApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedilaboDiagApplication.class, args);
	}

}
