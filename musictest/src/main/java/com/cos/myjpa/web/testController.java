package com.cos.myjpa.web;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.Music;

@RestController
public class testController {
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	

	@GetMapping("/{fileName}")
	public ResponseEntity<Resource> resouceFileDownload(@PathVariable String fileName) {
		
		//for문으로 다 돌려서 list에 받고 전달..
		try {
		Resource resource  = resourceLoader.getResource("classpath:static/"+ fileName);	
		File file = resource.getFile();	//파일이 없는 경우 fileNotFoundException error가 난다.
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,file.getName())	//다운 받아지는 파일 명 설정
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))	//파일 사이즈 설정
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())	//바이너리 데이터로 받아오기 설정
				.body(resource);	//파일 넘기기
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest()
					.body(null);
		} catch (Exception e ) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}
	
}
