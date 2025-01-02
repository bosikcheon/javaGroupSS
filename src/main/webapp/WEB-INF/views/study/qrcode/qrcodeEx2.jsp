<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>qrcodeEx2.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    function qrcodeCreate2() {
    	let subject = $("#subject").val();
    	let moveUrl = $("#moveUrl").val();
    	
    	if(subject.trim() == "" || moveUrl.trim() == "") {
    		alert("이동할 사이트 정보를 입력해 주세요");
    		$("#subject").focus();
    		return false;
    	}
    	
    	let query = {
    			subject   : subject,
    			moveUrl  : moveUrl
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/qrcode/qrcodeCreate2",
    		data : query,
    		success:function(res) {
    			if(res != "") {
    				let str = 'QR 코드명 : ' + res + '<br/>';
    				str += '<img src="${ctp}/qrcode/'+res+'.png" />';
    				$("#demo").html(str);
    			}
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
  <h2>소개사이트를 QR코드로 생성하기</h2>
  <form name="myfrom">
    <table class="table table-bordered text-center">
      <tr>
        <th>소개사이트명</th>
        <td><input type="text" name="subject" id="subject" value="cjsk1126블로그" autofocus required class="form-control"/></td>
      </tr>
      <tr>
        <th>주소(URL)</th>
        <td><input type="text" name="moveUrl" id="moveUrl" value="https://cjsk1126.tistory.com/" required class="form-control"/></td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="button" value="QR코드생성" onclick="qrcodeCreate2()" class="btn btn-success me-2"/>
          <input type="reset" value="다시입력" class="btn btn-warning me-2"/>
          <input type="button" value="돌아가기" onclick="location.href='qrcodeForm';" class="btn btn-info"/>
        </td>
      </tr>
    </table>
  </form>
  <hr/>
  <div>생성된 QR코드 :<br/>
    <div id="demo"></div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>