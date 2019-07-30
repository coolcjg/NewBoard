<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
<script src="http://code.jquery.com/jquery-latest.js"></script>

<script>

$(document).ready(function(){
	
	
	//회원탈퇴버튼 누를때 확인창
	$("#withdrawal").click(function(e){
		console.log("aaa");
		var result = confirm("회원을 탈퇴하시겠습니까?");
		
		if(result){
			location.href='${contextPath}/member/delete.do';
		}
	})
	//
	
});


</script>

</head>

<body>
<form method="post" action="${contextPath}/member/modProcess.do" enctype="multipart/form-data">
	<table> 
		<tr>
			<td>아이디</td>
			<td><input type="text" id="id" name="id" value="${member.id}" disabled></td>
		</tr>
		
		<tr>
			<td>비밀번호</td>
			<td><input type="password" id="pwd" name="pwd" value=""></td>
		</tr>
		
		<tr>
			<td>이름</td>
			<td><input type="text" id="name" name="name" value="${member.name}"></td>
		</tr>
		
		<tr>
			<td>이메일</td>
			<td><input type="text" id="email" name="email" value="${member.email}"></td>
		</tr>
		
		<tr>
			<td>
				<input id="formtest" type="submit" value="수정완료">
				<input type="button" value="취소">
			</td>
		</tr>
		
		<tr>
			<td>
				<input id="withdrawal" type="button" value="회원탈퇴">
			</td>
		</tr>
		
	</table>
</form>
</body>

</html>
    
    