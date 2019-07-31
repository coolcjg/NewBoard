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
		</script>
	</head>
	
	<body>
		<form method="post" action="createProcess.do" enctype="POST">
			<input type="text" name="parentNO" value="${parentNO}" hidden/>
			
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
					<td>
						<input type="submit" value="확인">
					</td>
				</tr>
			</table>
		</form>
	</body>

</html>