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
	</head>
	
	<body>
		
		<form method="post" action="${contextPath}/board/modProcess.do" enctype="POST">
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
					<td>
						<input type="submit" value="확인">
					</td>
				</tr>
			</table>
		</form>
	
	</body>

</html>