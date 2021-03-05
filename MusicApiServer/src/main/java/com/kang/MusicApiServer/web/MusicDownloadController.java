package com.kang.MusicApiServer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.kang.MusicApiServer.domain.MusicRepository;
import com.kang.MusicApiServer.domain.Yts;

@RestController
public class MusicDownloadController {

	@Autowired
	private MusicRepository musicRepository;
	
	@GetMapping({"", "/"})
	public String home() {
		return "home";
	}
	
	@GetMapping("/music/download/{page}")
	public String download(@PathVariable int page) {
		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
				" AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		HttpEntity request = 
				new HttpEntity(headers);

		ResponseEntity response = rt.exchange(
				"http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=85b342456906c65b1ff7659f8032f848&format=json&page="+page, 
				HttpMethod.GET, 
				request, 
				String.class
		);

		System.out.println("response getBody() : "+response.getBody());

		Gson gson = new Gson();
		Yts yts = gson.fromJson(response.getBody().toString(), Yts.class);

		musicRepository.saveAll(yts.getTracks().getTrack());
		System.out.println("yts : "+yts);
	
		return "다운로드 완료";
	}
	
	
}
