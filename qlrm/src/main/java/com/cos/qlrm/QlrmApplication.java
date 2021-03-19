package com.cos.qlrm;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cos.qlrm.domain.Person;
import com.cos.qlrm.domain.PersonRepository;

@SpringBootApplication
public class QlrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(QlrmApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataInit(PersonRepository personRepository) {
		return (args) -> {
			// 데이터 초기화 하기
			personRepository.saveAll(Arrays.asList(
					new Person("홍길동", "프로그래머", "0102222"),
					new Person("홍길동", "프로그래머","0102222"),
					new Person("홍길동", "프로그래머", "0102222"),
					new Person("홍길동", "프로그래머", "0102222"),
					new Person("홍길동", "프로그래머", "0102222")
					)
			);
		};
	}
}
