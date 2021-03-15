package com.cos.jwtex1.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex1.domain.User;
import com.cos.jwtex1.domain.UserRepository;

import lombok.RequiredArgsConstructor;

//@RequestMapping("api/v1")
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class RestApiController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping({"","/"})
	public String home() {
		return "<h1>home</h1>";
	}
	
	@GetMapping("/user")
	public String userPost() {
		return "<h1>UserPost</h1>";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "<h1>Admin</h1>";
	}
	
	@PostMapping("/join")
	public User join(@RequestBody User user) {
		String rawPassword = user.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		
		user.setRoles("USER");
		user.setPassword(encPassword);
		return userRepository.save(user);
	}
	
}
