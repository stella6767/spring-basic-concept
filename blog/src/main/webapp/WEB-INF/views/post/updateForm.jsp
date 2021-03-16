<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<input type="hidden" id="id" value="${post.id}"/>
			<input type="text" class="form-control" placeholder="Enter Title" id="title" value="${post.title }" />
		</div>

		<div class="form-group">
			<textarea rows="" cols="5" class="form-control" id="content" value="">
				${post.content }
			</textarea>

		</div>
		<button id="btn-update" class="btn btn-primary">수정완료</button>
	</form>
</div>
<script>
	$('#content').summernote({
		placeholder : '',
		tabsize : 2,
		height : 300
	});
	
	
	$("#btn-update").on("click",(e)=>{
		e.preventDefault(); //submit 막기, type=button 줘도 submit 방지가능
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};	
		let id = $("#id").val();
		console.log(data);
		console.log(id);
		
 		$.ajax({
			type:"PUT",
			url:"/post/"+id,
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"JSON"	
		}).done((res)=>{
			console.log(res);
			if(res.statusCode === 1){
				alert("수정에 성공하였습니다.");
				history.go(-1);
			}else{ //else가 의미가 없는게 어차피 실패뜨면, fail로 탐. 이거 안 탐
				alert("수정에 실패하였습니다.");
			}
		
		}).fail(); 
		
	});
</script>

<%@ include file="../layout/footer.jsp"%>