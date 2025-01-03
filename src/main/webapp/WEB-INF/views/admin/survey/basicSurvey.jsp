<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>basicSurvey.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<p><br/></p>
<div class="container">
  <form name="myform" method="post">
  	<table class="table table-borderless">
  	  <tr>
  	    <td><h5>기본 정보에 체크해 주세요.</h5></td>
  	  </tr>
  	  <tr><th>1. 귀하의 성별을 알려주세요.</th></tr>
			<tr><td><input type="radio" name="gender" value="남자" checked/> 남자</td></tr>
			<tr><td><input type="radio" name="gender" value="여자"/> 여자</td></tr>
	  	<tr><th>2. 귀하의 연령대는 어디에 속합니까?</th></tr>
			<tr><td><input type="radio" name="age" value="19세 이하" checked/> 19세 이하</td></tr>
			<tr><td><input type="radio" name="age" value="20~29세"/> 20~29세</td></tr>
			<tr><td><input type="radio" name="age" value="30~39세"/> 30~39세</td></tr>
			<tr><td><input type="radio" name="age" value="40~49세"/> 40~49세</td></tr>
			<tr><td><input type="radio" name="age" value="50~59세"/> 50~59세</td></tr>
			<tr><td><input type="radio" name="age" value="60세 이상"/> 60세 이상</td></tr>
			<tr><th>3. 귀하의 거주지는 어디입니까?</th></tr>
			<tr><td><input type="radio" name="address" value="강원도" checked/> 강원도</td></tr>
			<tr><td><input type="radio" name="address" value="서울/경기"/> 서울/경기</td></tr>
			<tr><td><input type="radio" name="address" value="충청도(대전,세종)"/> 충청도(대전,세종)</td></tr>
			<tr><td><input type="radio" name="address" value="전라도(광주)"/> 전라도(광주)</td></tr>
			<tr><td><input type="radio" name="address" value="경상도(울산,부산)"/> 경상도(울산,부산)</td></tr>
			<tr><td><input type="radio" name="address" value="제주도"/> 제주도</td></tr>
  	</table>
  	<div><input type="submit" class="form-control btn btn-dark mb-1" value="다음으로..."/></div>
  	<div><input type="button" class="form-control btn btn-secondary mb-1" value="창닫기" onclick="window.close();"/></div>
  	<!-- <div><input type="checkbox" name="noShow" id="noShow" onclick="noShow()"><label for="noShow">오늘하루 그만보기</label></div> -->
  	<input type="hidden" name="sIdx" value="${idx}"/>
  </form>
</div>
<p><br/></p>
</body>
</html>