package com.cos.blog.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);  //이건 옵셔널 안 걸거임 
}
