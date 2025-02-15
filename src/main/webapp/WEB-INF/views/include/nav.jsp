<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>
  function memberDeleteCheck() {
	  let ans = confirm("회원 탈퇴 하시겠습니까?");
	  if(ans) {
		  ans = confirm("탈퇴하시게되면 1개월간 같은 아이디로 다시 가입하실수 없습니다.\n탈퇴를 계속 진행 할까요?");
		  if(ans) {
			  $.ajax({
				  type : "post",
				  url  : "${ctp}/member/memberDeleteCheck",
				  success:function(res) {
					  if(res != "0") {
						  alert("회원에서 탈퇴되셨습니다.\n로그아웃 처리 됩니다.");
						  location.href = '${ctp}/member/memberLogin';
					  }
					  else alert("회원 탈퇴신청 실패~~~");
				  },
				  error : function() {
					  alert("전송오류!");
				  }
			  });
		  }
	  }
  }
  
  window.Kakao.init("xxxxxxxxxxx");
  function kakaoLogout() {
	  const accessToken = Kakao.Auth.getAccessToken();
	  if(accessToken) {
		  Kakao.Auth.logout(function() {
			  window.location.href = "https://kauth.kakao.com/oauth/logout?client_id=xxxxxxxxxxxxxxxxxxxxxxxxx&logout_redirect_uri=http://localhost:9090/javaGroupS/member/memberLogout";
		  });
	  }
  }
</script>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-black w3-card">
    <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <!-- <a href="http://192.168.50.20:9090/javaGroupS" class="w3-bar-item w3-button w3-padding-large">HOME</a> -->
    <a href="http://localhost:9090/javaGroupSS" class="w3-bar-item w3-button w3-padding-large">HOME</a>
    <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Guest</a>
    <c:if test="${!empty sLevel}">
	    <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Board</a>
	    <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">PDS</a>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Study1 <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/user/userMain" class="w3-bar-item w3-button">UserList</a>
	        <a href="${ctp}/dbtest/dbtestList" class="w3-bar-item w3-button">DB Test</a>
	        <a href="${ctp}/study/ajax/ajaxForm" class="w3-bar-item w3-button">Ajax Test</a>
	        <a href="${ctp}/study/mail/mailForm" class="w3-bar-item w3-button">메일보내기</a>
	        <a href="${ctp}/study/random/randomAlphaNumeric" class="w3-bar-item w3-button">랜덤알파뉴메릭</a>
	        <a href="${ctp}/study/fileUpload/fileUpload" class="w3-bar-item w3-button">파일업로드연습</a>
	        <a href="${ctp}/study/transaction/transactionForm" class="w3-bar-item w3-button">트랜잭션연습</a>
	        <a href="${ctp}/study/validator/validatorForm" class="w3-bar-item w3-button">Backend체크</a>
	        <a href="${ctp}/errorPage/errorMain" class="w3-bar-item w3-button">에러페이지연습</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Study2 <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/study/thumbnail/thumbnailForm" class="w3-bar-item w3-button">썸네일 이미지</a>
	        <a href="${ctp}/study/kakao/kakaomap" class="w3-bar-item w3-button">카카오맵</a>
	        <a href="${ctp}/study/chart/chartForm" class="w3-bar-item w3-button">구글차트1</a>
	        <a href="${ctp}/study/chart2/chart2Form" class="w3-bar-item w3-button">구글차트2</a>
	        <a href="${ctp}/study/restapi/restapiForm" class="w3-bar-item w3-button">Rest API</a>
	        <a href="${ctp}/study/crawling/jsoupForm" class="w3-bar-item w3-button">크롤링(JSOUP)</a>
	        <a href="${ctp}/study/crawling/seleniumForm" class="w3-bar-item w3-button">크롤링(Selenium)</a>
	        <a href="${ctp}/study/qrcode/qrcodeForm" class="w3-bar-item w3-button">QR코드연습</a>
	        <a href="${ctp}/study/captcha/captchaForm" class="w3-bar-item w3-button">캡차연습</a>
	        <a href="${ctp}/study/infiniteScroll/infiniteScroll" class="w3-bar-item w3-button">무한스크롤연습</a>
	        <a href="${ctp}/dbShop/dbProductList" class="w3-bar-item w3-button">미니쇼핑몰</a>
	        <a href="${ctp}/study/payment/payment" class="w3-bar-item w3-button">결제연습</a>
	        <a href="${ctp}/" class="w3-bar-item w3-button">스케줄러연습</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More" onclick="location.href='${ctp}/member/memberMain';">MyPage <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/webMessage/webMessage" class="w3-bar-item w3-button">웹메세지</a>
	        <a href="${ctp}/webSocket/webSocket" class="w3-bar-item w3-button">웹소켓채팅</a>
	        <a href="${ctp}/dbShop/dbCartList" class="w3-bar-item w3-button">장바구니</a>
	        <a href="${ctp}/dbShop/dbMyOrder" class="w3-bar-item w3-button">주문리스트</a>
	        <a href="${ctp}/member/memberList" class="w3-bar-item w3-button">회원리스트</a>
	        <a href="${ctp}/member/memberPwdCheck/p" class="w3-bar-item w3-button">비밀번호변경</a>
	        <a href="${ctp}/member/memberPwdCheck/i" class="w3-bar-item w3-button">회원정보수정</a>
	        <a href="javascript:memberDeleteCheck()" class="w3-bar-item w3-button">회원탈퇴</a>
	        <c:if test="${sLevel == 0}"><a href="${ctp}/admin/adminMain" class="w3-bar-item w3-button">관리자메뉴</a></c:if>
	      </div>
	    </div>
	  </c:if>
    <c:if test="${empty sLevel}">
	    <a href="${ctp}/member/memberLogin" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Login</a>
	    <a href="${ctp}/member/memberJoin" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Join</a>
    </c:if>
    <c:if test="${!empty sLevel}">
    	<div class="w3-dropdown-hover w3-hide-small">
				<button class="w3-padding-large w3-button" title="More" onclick="location.href='${ctp}/member/memberMain';">Logout <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
			    <a href="${ctp}/member/memberLogout" class="w3-bar-item w3-button w3-padding-large w3-hide-small">일반Logout</a>
			    <a href="javascript:kakaoLogout()" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Kakao Logout</a>
	      </div>
		  </div>
    </c:if>
    <!-- <a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a> -->
  </div>
</div>

<!-- Navbar on small screens (remove the onclick attribute if you want the navbar to always show on top of the content when clicking on the links) -->
<div id="navDemo" class="w3-bar-block w3-black w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top:46px">
  <a href="#band" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">BAND</a>
  <a href="#tour" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">TOUR</a>
  <a href="#contact" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">CONTACT</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">MERCH</a>
</div>