<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>memberLogin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
  <style>
    th {
      text-align: center;
      background-color: #eee;
    }
  </style>
  <script>
    'use strict';
    
    // 카카오 로그인앱키
  	window.Kakao.init("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
  	
    function kakaoLogin() {
    	window.Kakao.Auth.login({
    		scope: 'profile_nickname, account_email',
    		success:function(autoObj) {
    			//console.log(Kakao.Auth.getAccessToken(), "정상적으로 토큰이 발급됨");
    			window.Kakao.API.request({
    				url : '/v2/user/me',
    				success:function(res) {
    					const kakao_account = res.kakao_account;
    					//console.log("전송된정보 :",kakao_account);
    					location.href="${ctp}/member/kakaoLogin?nickName="+kakao_account.profile.nickname+"&email="+kakao_account.email+"&accessToken="+Kakao.Auth.getAccessToken();
    				}
    			});
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
  <h2 class="text-center mb-4">회 원 로 그 인</h2>
	<form name="myform" method="post">
    <table class="table table-bordered">
      <tr>
        <th>아이디</th>
        <td>
		      <input type="text" name="mid" value="${mid}" class="form-control mb-3" autofocus required /> 
        </td>
      </tr>
      <tr>
        <th>비밀번호</th>
        <td><input type="password" name="pwd" id="pwd" value="1234" class="form-control mb-3" required></td>
      </tr>
      <tr>
        <td colspan="2" class="text-center">
		      <button type="submit" class="btn btn-success mr-2">로그인</button>
		      <button type="reset" class="btn btn-warning mr-2">다시입력</button>
		      <a href="javascript:kakaoLogin()"><img src="${ctp}/images/kakaoLogin.png" width="73px"/></a>
		      <button type="button" onclick="location.href='memberJoin'" class="btn btn-info mr-3">회원가입</button>
		      <input type="checkbox" name="idSave" checked /> 아이디 저장
		    </td>
		  </tr>
		</table>
		<div class="text-center">
		  <a href="memberIdSearch"><span class="badge bg-secondary">아이디찾기</span></a> |
		  <a href="memberPwdSearch"><span class="badge bg-secondary">비밀번호찾기</span></a>
		</div>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>