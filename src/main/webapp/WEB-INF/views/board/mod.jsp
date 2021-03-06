<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
	
		<script>
			var isLogOn = '${isLogOn}';
			console.log(isLogOn);
			
			var sessionId = '${member.id}';
			console.log(sessionId);
			
			var boardId = '${boardVO.id}';
			console.log(boardId);
			
			if(isLogOn == 'null' || sessionId != boardId){
				alert('로그인 후 이용하세요');
				location.href='${contextPath}/board/list.do';
			}
		</script>
		
		<script>
		
		$(document).ready(function(){
			
			
			//파일업로드버튼이 변경이 발생했을 때 파일 업로드

			$("#fileUpload").change(function(e){
				
				var formData = new FormData();
				
				var inputFile = $("#fileUpload");
				
				//업로드파일 배열
				var files = inputFile[0].files;
				
				console.log(inputFile);
				console.log(files);
				
				for(var i=0; i<files.length;i++){
					
					//파일 확장자, 크기 확인
					if(!checkExtension(files[i].name, files[i].size)){
						return false;
					}
					
					formData.append("uploadFile", files[i]);
					console.log(formData);
				}
				
				$.ajax({
					url:'${contextPath}/file/fileUpload.do',
					processData:false,
					contentType:false,
					data:formData,
					type:'POST',
					dataType:'json',
					success:function(result){
						console.log(result);
						showUploadFile(result);
					}
				});
			});
			//
			
			
			//게시글 업로드버튼 누를때 파일정보까지 쿼리로 넘어가게 하기.
			$("#articleUpload").click(function(e){
				e.preventDefault();
				
				var str="";
				
				$("#fileList li").each(function(i, obj){
					var li = $(obj);
					
					console.dir(li);
					//li.data("")에서 파라미터를 적을때는 소문자를 써야한다. uploadpath(o) uploadPath(x)
					str+="<input type='hidden' name='fileList["+i+"].uploadPath' value='"+li.data("uploadpath")+"'>";
					str+="<input type='hidden' name='fileList["+i+"].uuid' value='"+li.data("uuid")+"'>";
					str+="<input type='hidden' name='fileList["+i+"].fileName' value='"+li.data("filename")+"'>";
					str+="<input type='hidden' name='fileList["+i+"].fileType' value='"+li.data("filetype")+"'>";
					

				});
				
				var form = $("#form");
				
				form.append(str);
				
				form.submit();
				
				
			});
			//
			
			//파일 삭제
			$("#fileList").on("click", "button", function(e){
				var targetFile =$(this).data("file");
				console.log(targetFile);
				var targetLi = $(this).closest("li");
				
				$.ajax({
					url:'${contextPath}/file/deleteFile.do',
					data:{targetFile : targetFile},
					dataType:'text',
					type:'POST',
					success:function(result){
						alert(result);
						targetLi.remove();
					}
				});
			});
			//
			
		
			//첨부파일 가져오기(function함수쓸때 }뒤에 () 까먹지 않기
			(function(){
				
			 	var uploadDiv = $("#fileList");
				console.log('${boardVO.articleNO}');
				
				$.getJSON("${contextPath}/file/list.do", {articleNO : '${boardVO.articleNO}'}, function(arr){
					
					var str="";
					
					
					
					$(arr).each(function(i, attach){
						
						console.log(arr);
						
						if(attach.fileType.search('image') !=-1){
							var path = encodeURIComponent(attach.uploadPath+"\\"+attach.uuid+"_"+attach.fileName);

							console.log(path);
							str="";
							str+="<li data-uploadPath='"+attach.uploadPath;
							str+="' data-uuid='"+attach.uuid;
							str+="' data-fileName='"+attach.fileName;
							str+="' data-fileType='"+attach.fileType+"'>";
							str+="<a href='#'><img style='height:100px; width:100px;' src='${contextPath}/file/showImage.do?path="+path+"'>";
							str+="<button type='button' data-file='"+path+"'>x</button>"
							str+="</a></li>";
							uploadDiv.append(str);
							
						}else{
							str="";
							str="<li data-uploadPath='"+attach.uploadPath;
							str+="' data-uuid='"+attach.uuid;
							str+="' data-fileName='"+attach.fileName;
							str+="' data-fileType='"+attach.fileType+"'>";
							str+="<a href='#'><img src='${contextPath}/resources/doc.jpg'><button type='button' data-file='"+path+"'>x</button></a></li>"
							uploadDiv.append(str);
						}
					
					});

				});

			}());
			//
			
			//파일업로드 파일체크
			var regex = new RegExp("(.*?)\.(exe|zip|alz)$");
			var maxSize = 1024*1024*10; //10MB
			
			function checkExtension(name, size){
				if(regex.test(name)){
					alert("해당 확장자는 업로드할 수 없습니다.");
					return false;
				}
				
				if(size>maxSize){
					alert("해당 파일은 용량초과(10MB)로 업로드할 수 없습니다.");
					return false;
				}
				return true;
			};
			
			//업로드파일 화면에 표시
			function showUploadFile(result){
				
				var uploadDiv = $("#fileList");
				
				for(var i=0; i<result.length; i++){
					console.log("쇼업로드");
					var path = encodeURIComponent(result[i].uploadPath+"/"+result[i].uuid+"_"+result[i].fileName);
					var str="";
					
					
					if(result[i].fileType.match('image')){
						
						str+="<li data-uploadPath='"+result[i].uploadPath;
						str+="' data-uuid='"+result[i].uuid;
						str+="' data-fileName='"+result[i].fileName;
						str+="' data-fileType='"+result[i].fileType+"'>";
						str+="<a href='#'><img style='height:100px; width:100px;' src='${contextPath}/file/showImage.do?path="+path+"'>";
						str+="<button type='button' data-file='"+path+"'>x</button>"
						str+="</a></li>";
						uploadDiv.append(str);
	
					}else{
						
						str="<li data-uploadPath='"+result[i].uploadPath;
						str+="' data-uuid='"+result[i].uuid;
						str+="' data-fileName='"+result[i].fileName;
						str+="' data-fileType='"+result[i].fileType+"'>";
						str+="<a href='#'><img src='${contextPath}/resources/doc.jpg'><button type='button' data-file='"+path+"'>x</button></a></li>"
						uploadDiv.append(str);
					}
				}
			}
			//
			
		});//document

			
		</script>
		
	</head>
	
	<body>
		
		<form id="form" method="post" action="${contextPath}/board/modProcess.do" enctype="multipart/form-data">
			<input type="text" name="articleNO" value="${boardVO.articleNO}" hidden> 
		
			<table> 
				<tr>
					<td>글 제목</td>
					<td><input type="text" name="title" value="${boardVO.title}"></td>
				</tr>
				
				<tr>
					<td>내용</td>
					<td><textarea name="content" rows="20" cols="50" >${boardVO.content}</textarea></td>
				</tr>
				
				<tr>
					<td>파일추가</td>
					<td><input type="file" id="fileUpload" name="uploadFile" multiple></td>
				</tr>
				
				<tr>
					<td>파일 업로드 결과</td>
					<td>
						<ul id="fileList">
						
						</ul>
					</td>
				</tr>	
								
				
				<tr>
					<td>
						<input id="articleUpload" type="submit" value="확인">
					</td>
				</tr>
			</table>
		</form>
	
	</body>

</html>