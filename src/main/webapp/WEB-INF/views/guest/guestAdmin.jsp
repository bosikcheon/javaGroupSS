<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>guestAdmin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-4">관 리 자 로 그 인</h2>
	<form name="myform" method="post">
    <div>아이디를 입력하시요
      <input type="text" name="mid" value="admin" class="form-control mb-3" autofocus required /> 
    </div>
    <div>비밀번호를 입력하시요
      <input type="password" name="pwd" id="pwd" value="1234" class="form-control mb-3" required>
    </div>
    <div class="row text-center">
      <div class="col"><button type="submit" class="btn btn-success mb-2">로그인</button></div>
      <div class="col"><button type="reset" class="btn btn-warning mb-2">다시입력</button></div>
      <div class="col"><button type="button" onclick="location.href='guestList'" class="btn btn-info">돌아가기</button></div>
    </div>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>