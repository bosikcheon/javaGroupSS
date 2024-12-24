<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>webMessage.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
    #leftWindow {
    	float: left;
    	width: 25%;
    	height: 520px;
    	text-align: center;
    	background-color: #ddd;
    	overflow:auto;
    }
    #rightWindow {
    	float: left;
    	width: 75%;
    	height: 520px;
    	text-align: center;
    	background-color: #eee;
    	overflow:auto;
    }
    #footerMargin {
    	clear: both;
    	margin: 10px;
    }
    h3 {text-align: center}
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h3>☆ 메세지 관리 ☆</h3>
  <div>(현재접속자:<font color="red">${sMid}</font>)</div>
  <div id="leftWindow">
    <p><br/></p>
    <p><a href="webMessage?mSw=0" class="text-dark link-primary link-underline-opacity-0 link-underline-opacity-75-hover">메세지작성</a></p>
    <p><a href="webMessage?mSw=1&mFlag=1" class="text-dark link-primary link-underline-opacity-0 link-underline-opacity-75-hover">받은메세지</a></p>
    <p><a href="webMessage?mSw=2&mFlag=2" class="text-dark link-primary link-underline-opacity-0 link-underline-opacity-75-hover">새메세지</a></p>
    <p><a href="webMessage?mSw=3&mFlag=3" class="text-dark link-primary link-underline-opacity-0 link-underline-opacity-75-hover">보낸메세지</a></p>
    <p><a href="webMessage?mSw=4&mFlag=4" class="text-dark link-primary link-underline-opacity-0 link-underline-opacity-75-hover">수신확인</a></p>
    <p><a href="webMessage?mSw=5&mFlag=5" class="text-dark link-primary link-underline-opacity-0 link-underline-opacity-75-hover">휴지통</a></p>
    <p><a href="webMessage?mSw=9" class="text-dark link-primary link-underline link-underline-opacity-0 link-underline-opacity-75-hover">휴지통비우기</a></p>
  </div>
  <div id="rightWindow">
    <p>
      <c:if test="${mSw == 0}">
      	<h3>메세지 작성</</h3>
      	<!-- 주소록 버튼 만들기.... -->
      	<jsp:include page="wmInput.jsp" />
      </c:if>
      <c:if test="${mSw == 1}">
      	<h3>받은메세지</h3>
      	<jsp:include page="wmList.jsp" />
      </c:if>
      <c:if test="${mSw == 2}">
      	<h3>새메세지</h3>
      	<jsp:include page="wmList.jsp" />
      </c:if>
      <c:if test="${mSw == 3}">
      	<h3>보낸메세지</h3>
      	<jsp:include page="wmList.jsp" />
      </c:if>
      <c:if test="${mSw == 4}">
      	<h3>수신확인</h3>
      	<jsp:include page="wmList.jsp" />
      </c:if>
      <c:if test="${mSw == 5}">
      	<h3>휴지통</h3>
      	<jsp:include page="wmList.jsp" />
      </c:if>
      <c:if test="${mSw == 6}">
      	<h3>메세지 내용보기</h3>
      	<jsp:include page="wmContent.jsp" />
      </c:if>
    </p>
  </div>
</div>
<div id="footerMargin"></div>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>