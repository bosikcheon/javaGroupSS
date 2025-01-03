<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>surveyList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<p><br/></p>
<div class="container">
  <h2>설문조사 리스트</h2>
  <div>
    <table class="table table-bordered text-center">
			<tr class="table-borderless">
				<td colspan="7" class="text-left">
					<h2 class="pb-5">설문조사 리스트</h2>
				</td>
			</tr>
			<tr class="table table-bordered table-dark">
				<th>제목</th>
				<th>작성날짜</th>
				<th>시작일</th>
				<th>마감일</th>
				<th>비고</th>
			</tr>
			<c:forEach var="vo" items="${vos}">
				<tr class=" table table-bordered" >
					<td>${vo.title}
						<c:if test="${vo.showSw==0}"><div class="badge bg-danger">종료</div></c:if> 
						<c:if test="${vo.showSw==1}"><div class="badge bg-success">진행중</div></c:if> 
					</td>
					<td>${fn:substring(vo.SDate,0,11)}</td>
					<td>${fn:substring(vo.startDate,0,11)}</td>
					<td>${fn:substring(vo.endDate,0,11)}</td>
					<td>
						<input type="button" class="btn btn-warning" value="수정/추가" onclick="location.href='surveyUpdate?idx=${vo.idx}';"/>
						<input type="button" class="btn btn-success" value="통계보기"  onclick="location.href='surveyResult?idx=${vo.idx}';"/>
					</td>
				</tr>
			</c:forEach>
    </table>
  </div>
</div>
<p><br/></p>
</body>
</html>