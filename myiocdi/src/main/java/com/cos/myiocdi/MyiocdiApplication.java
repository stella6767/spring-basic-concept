package com.cos.myiocdi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyiocdiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyiocdiApplication.class, args);
	}
	
	@Bean //IOC - 리턴값을 ioc 컨테이너에 저장함 - 클래스가 아닌 메서드에 할당
	public Robot getRobot() {
		return new Robot();
	}

}
