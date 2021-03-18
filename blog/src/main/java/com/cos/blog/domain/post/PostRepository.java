package com.cos.blog.domain.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer>{
	

	
	@Query(value ="select * FROM post WHERE title LIKE %:keyword% OR content LIKE %:keyword%",
			countQuery = "SELECT count(*) FROM post WHERE title like %:keyword% OR content LIKE %:keyword%", nativeQuery = true)
    Page<Post> findByKeyword(String keyword,Pageable pageable);
	

	
}
