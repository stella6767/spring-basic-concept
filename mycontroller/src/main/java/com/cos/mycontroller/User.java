package com.cos.mycontroller;

public class User {
	
	//1. username=ssar&password=1234 => 필드있네?
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	//2. setter 함수 호출(set함수명 다르게 적으면 null 뜰거임)
	public void setUsername(String username) {
		System.out.println("setUsername() 실행됨");
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		System.out.println("setPassword() 실행됨");
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	

}
