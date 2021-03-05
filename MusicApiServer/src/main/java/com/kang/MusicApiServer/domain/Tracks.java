package com.kang.MusicApiServer.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class Tracks {

	private List<Track> track;

	public List<Track> getTrack() {
		return track;
	}

	public void setTrack(List<Track> track) {
		this.track = track;
	}
	
	
	
	
}
