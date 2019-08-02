<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		
		<style>
			ul li{
				float:left;
				list-style:none;
			}
		</style>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		
		<script>
		$(document).ready(function(){
			
			//검색기능
			var searchForm = $("#searchForm");
			$("#searchForm button").on("click", function(e){
				if(!searchForm.find("option:selected").val()){
					alert("검색 종류를 선택하세요");
					return false;
				}
				if(!searchForm.find("input[name='keyword']").val()){
					alert("키워드를 입력하세요");
					return false;
				}
				searchForm.find("input[name='pageNum']").val("1");
				e.preventDefault();
				searchForm.submit();
			});
		
			//페이지 이동
			$(".page_button a").on("click",function(e){
				e.preventDefault();
				var num = $(this).attr("href");
				console.log(num);
				$("#searchForm").find("input[name='pageNum']").val(num);
				$("#searchForm").submit();
			});
			
		});
		</script>
	</head>
	
	
	<body>
		
		<!-- 글 리스트 -->
		<div>
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
						<td>${article.lv}</td>
						<td>${article.articleNO}</td>
						
						<td>
							<c:choose>
								<c:when test="${article.lv>1}">
									<c:forEach begin="1" end="${article.lv}" step="1">
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
		</div>
		
		<!-- 페이지네이션 -->		
		<div id="pagination">
			<ul>
				<c:if test="${pageVO.prev}">
					<li><a href="${pageVO.startPage-1}">Prev</a></li>
				</c:if>
				
				<c:forEach var="num" begin="${pageVO.startPage}" end="${pageVO.endPage}">
					<li class="page_button"><a id="pageNum" href="${num}">${num}</a></li>				
				</c:forEach>
				
				<c:if test="${pageVO.next}">
					<li><a href="${pageVO.endPage+1}">Next</a></li>
				</c:if>
			
			</ul>
		</div>
		
		<br>
		
		<!-- 검색 -->
		<div>
			<form id='searchForm' action="${contextPath}/board/listPaging.do" method="get">
				<select name='type'>
					<option value="" <c:out value="${pageVO.type==null?'selected':''}"/>>--</option>
					<option value="T" <c:out value="${pageVO.type eq 'T'?'selected':''}"/> >제목</option>
					<option value="C" <c:out value="${pageVO.type eq 'C'?'selected':''}"/> >내용</option>
					<option value="W" <c:out value="${pageVO.type eq 'W'?'selected':''}"/> >작성자</option>
					<option value="TC" <c:out value="${pageVO.type eq 'TC'?'selected':''}"/> >제목 or 내용</option>
					<option value="TW" <c:out value="${pageVO.type eq 'TW'?'selected':''}"/> >제목 or 작성자</option>
					<option value="TWC" <c:out value="${pageVO.type eq 'TWC'?'selected':''}"/> >제목 or 내용 or 작성자</option>
				</select>
				<input type="text" name="keyword" value='${pageVO.keyword}'/>

				<button>Search</button>
				
				<input type="hidden" name="pageNum" value="${pageVO.pageNum}">
				<input type="hidden" name="amount" value="20">
					
			</form>
		</div>
		
		<!-- 글쓰기 -->
		<div>
			<a id="write" href="create.do">글쓰기</a>
		</div>
	</body>
</html>