<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		
		<script>
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
		})
		
		</script>
	</head>
	
	<body>
	
		<a id="write" href="create.do">글쓰기</a>
		
		<div>
			<form id='searchForm' action="${contextPath}/board/listPaging.do" method="get">
				<select name='type'>
					<option value="" <c:out value="${pageMaker.cri.type==null?'selected':''}"/>>--</option>
					<option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/> >제목</option>
					<option value="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/> >내용</option>
					<option value="W" <c:out value="${pageMaker.cri.type eq 'W'?'selected':''}"/> >작성자</option>
					<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}"/> >제목 or 내용</option>
					<option value="TW" <c:out value="${pageMaker.cri.type eq 'TW'?'selected':''}"/> >제목 or 작성자</option>
					<option value="TWC" <c:out value="${pageMaker.cri.type eq 'TWC'?'selected':''}"/> >제목 or 내용 or 작성자</option>
				</select>
				<input type="text" name="keyword" value='<c:out value="${pageMaker.cri.keyword}"/>'/>

				<button>Search</button>
				
				<input type="hidden" name="pageNum" value="1">
				<input type="hidden" name="amount" value="10">
				
			</form>
		</div>	
		
		<div id="pagination">
			<ul>
				<c:if test="${pageVO.prev}">
					<li><a href="${pageVO.startPage-1}">Prev</a></li>
				</c:if>
				
				<c:forEach var="num" begin="${pageVO.startPage}" end="${pageVO.endPage}">
					<li class="paginate_button ${pageVO.pageNum==num? 'active':'' }"><a href="${num}">${num}</a></li>				
				</c:forEach>
				
				<c:if test="${pageVO.next}">
					<li><a href="${pageVO.endPage+1}">Next</a></li>
				</c:if>
			
			</ul>
		</div>
		
	
		
			
		
		
		
		
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
		
	</body>
</html>