package com.socegen.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.socegen.employee", "com.socegen.employee.*" })
@SpringBootApplication
public class SocgenEmployeeRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocgenEmployeeRegistrationApplication.class, args);
	}

}
