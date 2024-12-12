<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ajaxTest4_3.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    function midCheck() {
    	let mid = $("#mid").val();
    	if(mid.trim() == "") {
    		alert("아이디를 선택하세요");
    		return false;
    	}
    	
    	$.ajax({
    		type : 'post',
    		url  : '${ctp}/study/ajax/ajaxTest4_4',
    		data : {mid : mid},
    		success:function(res) {
    			$("#demo").html(res);
    		},
    		error : function() {
    			alert("전송 오류!");
    		}
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>AJax활용한 DB자료처리</h2>
  <hr/>
  <form>
    <h3>아이디를 선택하세요(String로 가져오기)</h3>
    <select name="mid" id="mid" onchange="midCheck()">
      <option value="">아이디선택</option>
      <c:forEach var="mid" items="${midVos}" varStatus="st">
      	<option>${mid}</option>
      </c:forEach>
    </select>
    <hr/>
    <div id="demo"></div>
    <hr/>
    
    <h3>아이디를 선택하세요(vo로 가져오기)</h3>
    <select name="mid2" id="mid2" onchange="midCheck2()">
      <option value="">아이디선택</option>
      <c:forEach var="mid" items="${midVos}" varStatus="st">
      	<option>${mid}</option>
      </c:forEach>
    </select>
    <hr/>
    <div id="demo2"></div>
    <hr/>
    
    <h3>주소를 선택하세요(vos로 가져오기)</h3>
    <div>동일한 주소는 콤보상자에 1개씩만 출력하시오</div>
    <select name="address" id="address" onchange="addressCheck()">
      <option value="">주소선택</option>
      <!--  -->
    </select>
    <hr/>
    <div id="demo3"></div>
    <hr/>
  </form>
  <hr/>
  <div><a href="${ctp}/study/ajax/ajaxForm" class="btn btn-success">돌아가기</a></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>