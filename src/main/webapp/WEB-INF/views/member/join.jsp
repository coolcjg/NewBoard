<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    


회원가입


<form method="post" action="${contextPath}/member/joinprocess.do" enctype="multipart/form-data">
	<table> 
		<tr>
			<td>아이디</td>
			<td><input type="text" name="id"></td>
		</tr>
		
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="pwd"></td>
		</tr>
		
		<tr>
			<td>이름</td>
			<td><input type="text" name="name"></td>
		</tr>
		
		<tr>
			<td>이메일</td>
			<td><input type="text" name="email"></td>
		</tr>
		
		<tr>
			<td>
				<input type="submit" value="회원가입">
				<input type="button" value="취소">
			</td>
		</tr>
		
	</table>
</form>