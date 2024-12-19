<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>randomForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    let str = '';
    let cnt = 0;
    
    function numericCheck() {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/random/randomNumeric",
    		success:function(res) {
    			cnt++;
    			str += cnt + ".(randomNumeric) : " + res + "<br/>";
    			$("#demo").html(str);
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    function uuidCheck() {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/random/randomUUID",
    		success:function(res) {
    			cnt++;
    			str += cnt + ".(randomUUID) : " + res + "<br/>";
    			$("#demo").html(str);
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    function alphaNumericCheck() {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/random/randomAlphaNumeric",
    		success:function(res) {
    			cnt++;
    			str += cnt + ".(randomAlphaNumeric) : " + res + "<br/>";
    			$("#demo").html(str);
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
  <h2>무작위수 / randomAlphaNumeric</h2>
  <div>알파벳과 숫자를 랜덤하게 출력처리</div>
  <hr/>
  <div>
    <input type="button" value="Numeric" onclick="numericCheck()" class="btn btn-success me-2"/>
    <input type="button" value="UUID" onclick="uuidCheck()" class="btn btn-primary me-2"/>
    <input type="button" value="AlphaNumeric" onclick="alphaNumericCheck()" class="btn btn-info me-2"/>
    <input type="button" value="새로고침" onclick="location.reload()" class="btn btn-warning me-2"/>
  </div>
  <hr/>
  <div>
    <div>출력결과 :</div>
    <span id="demo"></span>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>