<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>chartForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<script>
  'use strict';
  
  function chartChange() {
	  let part = document.getElementById("part").value;
	  let str = "";
	  
	  if(part == "barV" ) {
		  str += '<form name="chartForm" method="post">';
		  str += '<table class="table table-bordered text-center">';
		  str += '<tr><td>제목</td><td colspan="3"><input type="text" name="title" class="form-control"/></td></tr>';
		  str += '<tr><td>부제목</td><td colspan="3"><input type="text" name="subTitle" class="form-control"/></td></tr>';
		  str += '<tr class="table-secondary">';
		  str += '<th>범례</th><th><input type="text" name="legend1"/></th><th><input type="text" name="legend2"/></th><th><input type="text" name="legend3"/></th>';
		  str += '</tr>';
		  str += '<tr>';
		  str += '<td><input type="text" name="x1"></td><td><input type="text" name="x1Value1"></td><td><input type="text" name="x1Value2"></td><td><input type="text" name="x1Value3"></td>';
		  str += '</tr>';
		  str += '<tr>';
		  str += '<td><input type="text" name="x2"></td><td><input type="text" name="x2Value1"></td><td><input type="text" name="x2Value2"></td><td><input type="text" name="x2Value3"></td>';
		  str += '</tr>';
		  str += '<tr>';
		  str += '<td><input type="text" name="x3"></td><td><input type="text" name="x3Value1"></td><td><input type="text" name="x3Value2"></td><td><input type="text" name="x3Value3"></td>';
		  str += '</tr>';
		  str += '<tr>';
		  str += '<td><input type="text" name="x4"></td><td><input type="text" name="x4Value1"></td><td><input type="text" name="x4Value2"></td><td><input type="text" name="x4Value3"></td>';
		  str += '</tr>';
		  str += '<tr>';
		  str += '<td colspan="4" class="text-center"><input type="button" value="차트그리기" onclick="chartShow(\'barV\')" class="btn btn-primary" /></td>';
		  str += '</tr>';
		  str += '</table>';
		  str += '<input type="hidden" name="part" value="barV" />';
		  str += '</form>';
		  
		  $("#demo").html(str);
	  }
	  else if(part == 'visitCount') {
		  chartShow(part);
	  }
  }
  
  function chartShow(part) {
	  alert("1.part : " + part);
	  if(part == 'barV') {
		  //chartForm.submit();
	  }
	  else if(part == 'visitCount') {
		  alert("2.part : " + part);
		  location.href = "memberViewChart?part=visitCount";
	  }
  }
</script>
<p><br/></p>
<div class="container">
  <h2>구글 차트 연습2</h2>
  <div>
    <h4>학습할 차트를 선택하세요</h4>
    <select name="part" id="part" onchange="chartChange()">
      <option value="">차트선택</option>
      <option value="barV" 		${part == 'barV' ? 'selected' : ''}>수직막대차트</option>
      <%-- <option value="barV" 		${part == 'pie' ? 'selected' : ''}>원형차트</option> --%>
      <option value="visitCount" 		${part == 'visitCount' ? 'selected' : ''}>최근방문자수</option>
      <%-- <option value="mostVisitCount" 		${part == 'mostVisitCount' ? 'selected' : ''}>많이방문한회원</option> --%>
    </select>
  </div>
  <hr/>
  <div id="demo"></div>
  <hr/>
  <div>
    <c:if test="${part == 'barV'}"><jsp:include page="barVChart.jsp" /></c:if>
    <%-- <c:if test="${part == 'pie'}"><jsp:include page="picChart.jsp" /></c:if> --%>
    <c:if test="${part == 'visitCount'}"><jsp:include page="visitCountChart.jsp" /></c:if>
    <%-- <c:if test="${part == 'mostVisitCount'}"><jsp:include page="mostVisitCountChart.jsp" /></c:if> --%>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>