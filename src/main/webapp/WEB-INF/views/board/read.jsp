<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
	
		<script>
			$(document).ready(function(){
				
				
				//파일 다운로드
				$("#fileList").on("click","li", function(e){
					
					var li = $(this);
					
					var path = encodeURIComponent(li.data("uploadpath")+"/"+li.data("uuid")+"_"+li.data("filename"));
					
					self.location="${contextPath}/file/download.do?path="+path;
					
				});
				
				
				
				//글수정
				$("#boardMod").click(function(e){
					e.preventDefault();
					location.href='${contextPath}/board/mod/${boardVO.articleNO}';
				});
				
				//글삭제
				$("#boardDel").click(function(e){
					e.preventDefault();
					var flag = confirm('삭제하시겠습니까?');
					
					console.log(flag);
					
					if(flag == true){
						location.href='${contextPath}/board/del/${boardVO.articleNO}';
					}else{
						return;
					}
				});
				
				//답글달기
				$("#rep").click(function(e){
					e.preventDefault();
					location.href='${contextPath}/board/create.do?parentNO=${boardVO.articleNO}';		
				});
				
				console.log('${boardVO.articleNO}');
				
				
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
								str+="</a></li>";
								uploadDiv.append(str);
								
							}else{
								str="";
								str="<li data-uploadPath='"+attach.uploadPath;
								str+="' data-uuid='"+attach.uuid;
								str+="' data-fileName='"+attach.fileName;
								str+="' data-fileType='"+attach.fileType+"'>";
								str+="<a href='#'><img src='${contextPath}/resources/doc.jpg'></a></li>"
								uploadDiv.append(str);
							}
						
						});

					});

				}());

			});
		</script>
			
			
			
		<script>
			
			
		
		</script>
	
	</head>

	<body>
	
		<form method="get" action="modForm.do" enctype="get">
			<table> 
				<tr>
					<td>글 제목</td>
					<td>${boardVO.title}</td>
				</tr>
				
				<tr>
					<td>작성일</td>
					<td>${boardVO.writeDate}</td>
				</tr>		
				
				<tr>
					<td>내용</td>
					<td>${boardVO.content}</td>
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
						<input id="rep" type="button" value="답글쓰기">
						
						<c:choose>
							<c:when test="${member.id eq boardVO.id}">
								<input id="boardMod" type="submit" value="수정">
								<input id="boardDel" type="submit" value="삭제">
							</c:when>
						</c:choose>
					</td>
				</tr>
			</table>
		</form>
	
	</body>
</html>