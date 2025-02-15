<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>게시판 글쓰기</title>
<script type="text/javascript">
$(function(){
	$(".upFileArea").on("dragenter dragover", function(e){
		e.preventDefault(); // 진행중인 이벤트 버블링 캔슬
	});
	
	$(".upFileArea").on("drop", function(e){
		e.preventDefault(); // 진행중인 이벤트 버블링 캔슬
		console.log(e.originalEvent.dataTransfer.files);
		let files = e.originalEvent.dataTransfer.files;
		
		$.each(files, function(i, file){
			console.log(file.name)
			
			let form = new FormData();
			form.append("uploadFile", file); // 전송할 데이터 추가
			// 위의 "uploadFile" 파일의 이름은 컨트롤러단의 MultipartFile 매개변수명과 일치시킨다
			
			$.ajax({
		          url: "uploadFile", 
		          type: "post",
		          data : form, 
		          dataType: "json", 
		          processData : false, // text데이터에 대해서 쿼리 스트링 처리하지 않음
		          contentType : false, // application/x-www-form-urlencoded 처리하지 않음
		          success: function (data) {
		        	  console.log(data);
		        	  if(data!=null){
		        		  displayUploadedFile(data);
		        	  }
		          },
		          error: function () {},
		          complete: function () {},
		        });
		});
	});
});
	
	function displayUploadedFile(json){
		let output = "";
		$.each(json, function(i, item){
			let name = item.newFileName;
			
			if(item.thumbFileName != null){
				let thumb = item.thumbFileName;
				output += `<img src='../resources/uploads/\${thumb}' id='\${item.newFileName}'/>`;
				output += `<img src='../resources/images/remove.png' class='remIcon' onclick='remFile(this)'/>`;
			} else{
				output += `<a href='../resources/uploads/\${name}'/>\${item.originalFileName}</a>`;
				output += `<img src='../resources/images/remove.png' class='remIcon' onclick='remFile(this)'/>`;
			}
		});
		$(".upLoadFiles").html(output)
	}
	
	function remFile(fileId){
		console.log(fileId);
		let removeFile = $(fileId).prev().attr('id'); //삭제할 파일의 originalName
		console.log(removeFile);
		$.ajax({
	          url: "remFile", 
	          type: "GET",
	          data : {
	        	"removeFile" : removeFile
	          }, 
	          dataType: "text", 
	          success: function (data) {
	        	  console.log(data);
	        	  if(data == "success"){	        		  
	        		  $(fileId).prev().remove();
	        		  $(fileId).remove();
	        	  } 
	          },
	          error: function () {},
	          complete: function () {},
	        });
	}
	
	function cancelBtn(){
		console.log("취소")
		$.ajax({
	          url: "remAllFile", 
	          type: "GET",
	          dataType: "text", 
	          success: function (data) {
	        	  console.log(data);
	        	  if(data == "success"){	        		  
	        		  location.href = "listAll"
	        	  }
	          },
	          error: function () {},
	          complete: function () {},
	        });
	}
	
	
</script>
<style>
	.upFileArea {
		width : 100%;
		height : 120px;
		border : 1px dotted #333;
		border-radius : 5px;
		
		background-color : #ededed;
		
		font-weight : bold;
		line-height : 120px;
		text-align : center;
	}
	#content{
		border-radius : 10px;
	}
</style>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
<!-- 	로그인하지 않은 유저는 login.jsp페이지로 돌려보내기 -->
<%-- <c:if test="${sessionScope.loginUser == null }"> --%>
<%-- 	<c:redirect url="../member/login.jsp"></c:redirect> --%>
<%-- </c:if> --%>
	
	<div class="container">
		<h1>게시판 글쓰기</h1>
		<form action="writeBoard" method="post">
			<div class="mb-3 mt-3">
			    <input type="hidden" class="form-control" id="writer" name="writer" value="${loginUser.userId}" >
			</div>
			
			<div class="mb-3 mt-3">
			    <label for="title" class="form-label">제목:</label>
			    <input type="text" class="form-control" id="title" name="title">
			</div>
			
			<div class="mb-3 mt-3">
			    <label for="content" class="form-label">내용:</label>
			    <textarea rows="10" style="width: 100%" id="content" name="content"></textarea>
			</div>
			
			<div class="mb-3 mt-3">
			    <label for="upFile" class="form-label">첨부이미지 :</label>
			    <div class="upFileArea">업로드할 파일을 드래그앤드랍 하세요.</div>
			    <div class="upLoadFiles"></div>
			</div>
			<input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
			
			<button type="submit" class="btn btn-success">저장</button>
			<button type="reset" class="btn btn-danger" onclick="cancelBtn();">취소</button>
		
		</form>
	</div>
	
	
<jsp:include page="../footer.jsp"></jsp:include>
	
</body>
</html>