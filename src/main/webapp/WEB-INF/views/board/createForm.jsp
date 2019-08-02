<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    

<html>

	<head>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		
		<script>
			//세션에서 클래스를 가져올때는 ''를 사용한다.
			var member = '${member}';
			
			
			var parentNO = ${parentNO};
			
			console.log(member);
			console.log(parentNO);
						
			if(member == ''){
				alert('로그인 후 이용하세요');
				location.href='${contextPath}/member/loginForm.do';
			}
			
			$(document).ready(function(){
				
				
				//기본동작 막기
				var formObj = $("#form");
				
				$("input[type='submit']").on("click", function(e){
					e.preventDefault();
					console.log("submit clicked");
					
					var str="";
					
					$("#fileList li").each(function(i, obj){
						var jobj = $(obj);
						
						console.dir(jobj);
						str += "<input type='hidden' name='fileList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
						str += "<input type='hidden' name='fileList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
						str += "<input type='hidden' name='fileList["+i+"].uploadPath' value='"+jobj.data("uploadpath")+"'>";
						str += "<input type='hidden' name='fileList["+i+"].fileType' value='"+jobj.data("filetype")+"'>";
					});
					formObj.append(str);
					formObj.submit();
				});				
				
				
				
				
				
				
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
				
				//파일업로드버튼이 변경이 발생했을 때 파일 업로드

				$("#fileUpload").change(function(e){
					
					var formData = new FormData();
					
					var inputFile = $("#fileUpload");
					
					//업로드파일 배열
					var files = inputFile[0].files;
					
					console.log(inputFile);
					console.log(files);
					
					for(var i=0; i<files.length;i++){
						console.log("aa");
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
			});
			
			
			
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
			}
			
		</script>
	</head>
	
	<body>
		<form id="form" method="post" action="createProcess.do" enctype="multipart/form-data">
			<input type="hidden" name="parentNO" value="${parentNO}" />
			<script>

			</script>
			<table> 
				<tr>
					<td>글 제목</td>
					<td><input type="text" name="title"></td>
				</tr>
				
				<tr>
					<td>내용</td>
					<td><textarea name="content" rows="20" cols="50" ></textarea></td>
				</tr>
				
				<tr>
					<td>파일업로드</td>
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