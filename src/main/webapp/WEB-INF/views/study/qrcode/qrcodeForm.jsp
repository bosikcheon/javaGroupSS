<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>qrcode.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    function qrcodeCreate() {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/qrcode/qrcodeCreate",
    		success:function(res) {
    			if(res != "") {
    				let str = 'QR 코드명 : ' + res + '<br/>';
    				str += '<img src="${ctp}/qrcode/'+res+'.png" />';
    				$("#demo").html(str);
    			}
    			else alert("Qr코드 생성 실패~~");
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
  <h2>QR Code 연습하기</h2>
  <hr/>
  <div class="row">
    <div class="col"><a href="javascript:qrcodeCreate()" class="btn btn-success">Qr코드생성</a></div>
    <div class="col"><a href="qrcodeEx1" class="btn btn-primary">개인정보Qr생성</a></div>
    <div class="col"><a href="qrcodeEx2" class="btn btn-info">소개사이트Qr생성</a></div>
    <div class="col"><a href="qrcodeEx3" class="btn btn-warning">영화예매Qr생성</a></div>
  </div>
  <hr/>
  <div id="demo"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>