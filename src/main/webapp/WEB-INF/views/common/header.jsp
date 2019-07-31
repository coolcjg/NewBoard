<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<c:choose>
	<c:when test="${isLogOn==true && member!=null}">
		${member.name}님이 로그인중입니다.
		<a href="${contextPath}/member/modForm.do">정보수정</a>
		<a href="${contextPath}/member/logout.do">로그아웃</a>
	</c:when>
	
	
	<c:otherwise>
	<a href="${contextPath}/member/joinForm.do">회원가입</a>
	<a href="${contextPath}/member/loginForm.do">로그인</a>
	</c:otherwise>
</c:choose>

<a href="${contextPath}/board/list.do">게시글 보러가기</a>
<a href="${contextPath}/member/list.do">회원리스트</a>

<hr>
