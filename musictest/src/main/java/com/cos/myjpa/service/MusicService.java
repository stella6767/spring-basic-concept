package com.cos.myjpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cos.myjpa.domain.music.Music;
import com.cos.myjpa.domain.music.MusicRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MusicService {

	private final MusicRepository musicRepository;

	
	public Music 음악저장하기(Music music) {	
		//Music music = new Music(null, "Celebrity", "아이유", "http://192.168.10.225:8080/001 아이유(IU) - Celebrity.mp3");
		musicRepository.save(music);
		
		return music;
	}
	
	
	public List<Music> 음악전체찾기(){
		List<Music> musics = musicRepository.findAll();
		return musics;
	}
	
	
}
