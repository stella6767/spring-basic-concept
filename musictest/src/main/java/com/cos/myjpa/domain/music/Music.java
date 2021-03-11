package com.cos.myjpa.domain.music;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Music {
	@Id //Pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Table, auto_increment, Sequence 
	private Integer id;
	
	@Column(nullable = false, length = 100, unique = true)
	private String name;
	
	@Column(nullable = false, length = 100) //12345 -> 해쉬(비밀번호 암호화)
	private String artist;
	
	private String url;
}
