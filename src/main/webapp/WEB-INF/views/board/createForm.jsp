<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    

<html>

<head>

</head>

<script src="http://code.jquery.com/jquery-latest.js"></script>

<script>
	var isLogOn = '<%=session.getAttribute("isLogOn") %>';
	
	console.log(isLogOn);
	
	if(isLogOn == 'null'){
		alert('로그인 후 이용하세요');
		location.href='list.do';
	}

</script>

<body>

글쓰기


<form method="post" action="createProcess.do" enctype="POST">
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