<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>guestInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>방 명 록 글 쓰 기</h2>
  <form name="myform" method="post" class="was-validated">
    <div class="form-group">
      <label for="name">성명</label>
      <input type="text" class="form-control" id="name" value="${sNickName}" placeholder="Enter username" name="name" autofocus required>
      <div class="valid-feedback">Ok!!!</div>
      <div class="invalid-feedback">성명을 입력하세요.</div>
    </div>
    <div class="form-group">
      <label for="email">E-mail</label>
      <input type="text" class="form-control" id="email" placeholder="Enter Email" name="email">
    </div>
    <div class="form-group">
      <label for="homePage">홈페이지</label>
      <input type="text" class="form-control" id="homePage" value="http://" placeholder="Enter HomePage" name="homePage">
    </div>
    <div class="form-group">
      <label for="content">방문소감</label>
      <textarea rows="5" class="form-control" id="content" placeholder="Enter content" name="content" required></textarea>
      <div class="valid-feedback">Ok!!!</div>
      <div class="invalid-feedback">방문소감을 입력하세요.</div>
    </div>
    <div class="form-group text-center">
    	<button type="submit" class="btn btn-primary mr-3">방명록등록</button>
    	<button type="reset" class="btn btn-warning mr-3">다시입력</button>
    	<button type="button" onclick="location.href='guestList'" class="btn btn-info">돌아가기</button>
    </div>
    <input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>