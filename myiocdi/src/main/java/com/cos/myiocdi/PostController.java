package com.cos.myiocdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Component(용도없음), Configuration(설정파일), Service(서비스), Repository(레파지토리), Bean

//RestController, Controller -> IOC(싱글톤) 등록 new PostController(주입);
@RestController
public class PostController {
	
//	@Autowired
//	private Robot robot; //DI
	
	private final Robot robot; //DI
	
	public PostController(Robot robot) {
		this.robot = robot;
	}

	@GetMapping("/")
	public String home() {
		Robot r = new Robot(); //DI 아님
		return "home" + r.getName() + robot.getName();
	}
	
	
	
}
