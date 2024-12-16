<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberPwdSearch.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/userTable.css" />
  <script>
    'use strict';
    
    function inforCheck() {
    	let mid = $("#mid").val();
    	let email = $("#email").val();
    	
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/member/inforCheck",
    		data : {
    			mid : mid,
    			email : email
    		},
    		success:function(res) {
    			if(res != "0") {
			    	let spin = "<div class='text-center'><div class='spinner-border text-muted'></div>임시 비밀번호를 메일로 발송중입니다. 잠시만 기다려주세요<div class='spinner-border text-muted'></div></div>";
			    	$("#certificationBox").html(spin);
			    	emailSendOk(mid, email);
    			}
    			else alert("개인정보를 확인하시고 다시 전송하세요");
    		}
    	});
    }
    
    // 메일로 전송받은 인증키 확인처리하기
    function emailSendOk(mid, email) {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/member/emailSendOk",
    		data : {
    			mid : mid,
    			email : email
    		},
    		success:function() {
		    	alert("임시 비밀번호를 메일로 발송했습니다.\n다시 로그인해 주세요");
		    	location.href = "memberLogin";
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
<div class="container text-center">
  <h3>비밀번호 찾기</h3>
  <br/>
  <p>가입시에 등록하신 아이디와 이메일 주소를 입력하세요</p>
  <table class="table table-bordered" id="userTable">
    <tr>
      <th class="align-middle">아이디</th>
      <td>
      	<input type="text" name="mid" id="mid" placeholder="아이디를 입력하세요" autofocus class="form-control"/>
      </td>
    </tr>
    <tr>
      <th class="align-middle">이메일주소</th>
      <td>
        <div class="input-group mt-2 mb-2">
        	<input type="text" name="email" id="email" placeholder="이메일 주소를 입력하세요" class="form-control"/>
        	<input type="button" value="회원정보확인" onclick="inforCheck()" class="btn btn-primary"/>
        </div>
      </td>
    </tr>
  </table>
  <br/>
  <div id="certificationBox"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>