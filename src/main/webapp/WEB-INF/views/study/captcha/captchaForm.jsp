<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>captchaForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
    #captchaImage {
      width  : 250px;
      height : 50px;
      border : 3px dotted #A3C552;
      text-align : center;
      padding: 5px;
    }
  </style>
  <script>
    'use strict';
    
    function captchaCheck() {
    	let strCaptcha = $("#strCaptcha").val();
    	if(strCaptcha.trim == "") {
    		alert("코드값을 입력하세요");
    		$("#strCaptcha").focus();
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/captcha/captchaCheck",
    		data : {strCaptcha : strCaptcha},
    		success:function(res) {
    			if(res != "0") alert("확인되었습니다.");
    			else alert("확인실패~ 다시 입력후 전송처리해 주세요");
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
  <h2>Captcha 연습</h2>
  <hr/>
  <form name="myform">
    <p id="captchaCode">다음 코드를 입력해 주세요(${captchaImage}) : 
      <img src="${ctp}/data/captcha/${captchaImage}" id="captchaImage"/>
    </p>
    <p>
      <input type="text" name="strCaptcha" id="strCaptcha"/>
      <input type="button" value="확인" id="confirmBtn" onclick="captchaCheck()"/>
      <input type="button" value="새로고침" onclick="location.href='captchaImage';"/>
    </p>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>