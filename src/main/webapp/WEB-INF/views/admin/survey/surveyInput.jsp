<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>surveyInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<p><br/></p>
<div class="container">
  <h2>설문 조사 등록하기</h2>
  <form name="myform" method="post">
	  <div>
			<table class="table table-borderless">
				<tr>
					<td><font size="6"><b>설문조사 등록창</b></font> (설문조사에 관한 주제 내용을 기록합니다.)</td>
					<td class="text-end"><input type="checkbox" name="showSw" checked /> 홈화면에 설문지 띄우기</td>
				</tr>
			</table>
			<table class="table table-bordered">
				<tr>
					<th>설문조사명</th>
					<td><input type="text" name="title" placeholder="설문할 주제를 입력하세요." class="form-control" autofocus /></td>
				</tr>
				<tr>
					<th>기간설정</th>
					<td>시작일시 <input type="date" name="startDate" value="<%=LocalDate.now() %>" class="form-control"/> 
					- 마감일시 <input type="date" name="endDate" value="<%=LocalDate.now() %>" class="form-control"/>
					<input type="submit" class="btn btn-success"  value="설문조사지생성"/></td> 
				</tr>
			</table>
	  </div>
  </form>
</div>
<p><br/></p>
</body>
</html>