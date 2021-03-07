package com.cos.myjpa.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.myjpa.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User { //User 1 <-> Post N	
	@Id //Pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Table, auto_increment, Sequence 
	private Long id;
	private String username;
	private String password;
	private String email;
	@CreationTimestamp //자동으로 현재시간이 들어감
	private LocalDateTime createDate;

	//역방향 맵핑
	@ToString.Exclude
	@JsonIgnoreProperties({"user"}) //user getter 때리지 말자
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)//mappedBy = 변수명-나는 FK의 주인이 아니다. select할때 user변수가 FK의 주인이란걸 명시
	private List<Post> post;  //1:N 관계면 기본전략이 LAZY, EAGER은 바로 있으니 join 해서 들고오고. LAZY는 getter 때릴 때 select 2번

//	@Transient //테이블에는 안 만들어지는데, 자바 클래스에는 들고있음
//	private int value;
}
