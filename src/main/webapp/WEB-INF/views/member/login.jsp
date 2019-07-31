<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    
    
<html>

	<head>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		
		<script>
		
		$(document).ready(function(){
			var loginFail = '${loginFail}';
			
			console.log(loginFail);
			
			if(loginFail == 'true'){
				alert('로그인 실패');
			};
			
		})
		</script>
	
	
	</head>
	
	
	<body>    
		<form action="${contextPath}/member/loginProcess.do" method="get">
			<table>
				<tr>
					<td>아이디</td><td><input type="text" name="id"></td>
				</tr>
				
				<tr>
					<td>비밀번호</td><td><input type="password" name="pwd"></td>
				</tr>
				
				<tr>
					<td>
						<input type="submit" value="로그인"><input type="button" value="취소">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>