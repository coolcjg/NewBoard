<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
	</head>
	
	<body>
	
		<a id="write" href="create.do">글쓰기</a>
		
		<table>
			<tr>
				<td>레벨</td>
				<td>글 번호</td>
				<td>제목</td>
				<td>작성일</td>
				<td>아이디</td>
			</tr>
			
			<c:forEach var="article" items="${list}">
				<tr>
					<td>${article.level}</td>
					<td>${article.articleNO}</td>
					
					<td>
						<c:choose>
							<c:when test="${article.level>1}">
								<c:forEach begin="1" end="${article.level}" step="1">
									<span style="padding-left : 20px"></span>
								</c:forEach>
								<a href="read/${article.articleNO}">${article.title}</a>
							</c:when>
							
							<c:otherwise>
								<a href="read/${article.articleNO}">${article.title}</a>
							</c:otherwise>
						</c:choose>
					</td>
		
					<td>${article.writeDate}</td>
					<td>${article.id}</td>
				</tr>
			</c:forEach>
		</table>
		
	</body>
</html>