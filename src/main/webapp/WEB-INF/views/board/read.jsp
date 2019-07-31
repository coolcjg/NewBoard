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
				
				console.log("${member.id}");
				console.log("${boardVO.id}");
			
				$("#boardMod").click(function(e){
					e.preventDefault();
					location.href='${contextPath}/board/mod/${boardVO.articleNO}';
				});
				
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
				
				$("#rep").click(function(e){
					e.preventDefault();
					location.href='${contextPath}/board/create.do?parentNO=${boardVO.articleNO}';		
				});
			})
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