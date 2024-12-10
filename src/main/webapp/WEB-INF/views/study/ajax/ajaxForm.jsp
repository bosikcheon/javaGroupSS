<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ajaxForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    let intStr = 0;
    
    // 동기식
    function ajaxTest1(idx) {
    	location.href = "${ctp}/study/ajax/ajaxTest1?idx="+idx;
    }
    
    // 비동기식(숫자값의 전달처리)
    function ajaxTest2(idx) {
    	$.ajax({
    		type : "get",
    		url  : "${ctp}/study/ajax/ajaxTest2",
    		data : {idx : idx},
    		success:function(res) {
    			intStr += Number(res);
    			//$("#demo2").html(res);
    			$("#demo2").html(intStr);
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 비동기식(문자값의 전달처리)
    function ajaxTest3(str) {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/ajax/ajaxTest3",
    		data : {str : str},
    		success:function(res) {
    			$("#demo3").html(res);
    		},
    		error : function() {
    			alert("전송오류!");
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
  <h2>AJAX 연습</h2>
  <hr/>
  <div>숫자값 전달<br/>
    <a href="javascript:ajaxTest1(10)">값전달1(동기식)</a> :
    <span id="demo1">${idx}</span>
  </div>
  <hr/>
  <div>숫자값 전달<br/>
    <a href="javascript:ajaxTest2(10)">값전달2(비동기식)</a> :
    <span id="demo2"></span>
  </div>
  <hr/>
  <div>문자값 전달<br/>
    <a href="javascript:ajaxTest3('안녕하세요')">값전달3(비동기식)</a> :
    <span id="demo3"></span>
  </div>
  <hr/>
  <div>응용(배열형식) 전달 - 시(도)/구(시,동) 출력<br/>
    <a href="${ctp}/study/ajax/ajaxTest4_1" class="btn btn-primary">응용(String배열)</a>
    <a href="${ctp}/study/ajax/ajaxTest4_2" class="btn btn-secondary">응용(ArrayList)</a>
    <a href="${ctp}/study/ajax/ajaxTest4_3" class="btn btn-primary">응용(Map)</a>
    <a href="${ctp}/study/ajax/ajaxTest4_4" class="btn btn-primary">응용(DB자료)</a>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>