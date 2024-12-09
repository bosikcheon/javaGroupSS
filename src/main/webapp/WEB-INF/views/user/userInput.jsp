<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>userInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
    th, td {
      padding: 10px !important;
    }
    th {
      text-align: center;
      background-color: #eee !important;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <form method="post">
    <h2 class="text-center mb-4">회 원 가 입</h2>
    <table class="table table-bordered">
      <tr>
        <th>아이디</th><td><input type="text" name="mid" id="mid" class="form-control" autofocus placeholder="아이디를 입력하세요"/></td>
      </tr>
      <tr>
        <th>비밀번호</th><td><input type="password" name="pwd" id="pwd" class="form-control" placeholder="비밀번호를 입력하세요"/></td>
      </tr>
      <tr>
        <th>성명</th><td><input type="text" name="name" id="name" class="form-control" placeholder="성명을 입력하세요"/></td>
      </tr>
      <tr>
        <th>나이</th><td><input type="number" name="age" id="age" class="form-control" placeholder="나이를 입력하세요"/></td>
      </tr>
      <tr>
        <th>성별</th>
        <td>
          <input type="radio" name="gender" id="gender1" value="남자"/>남자 &nbsp;
          <input type="radio" name="gender" id="gender2" value="여자" checked />여자
        </td>
      </tr>
      <tr>
        <th>주소</th><td><input type="text" name="address" id="address" class="form-control" placeholder="주소를 입력하세요"/></td>
      </tr>
      <tr>
        <td colspan="2" class="text-center">
          <input type="submit" value="회원가입" class="btn btn-success mr-3" />
          <input type="reset" value="다시입력" class="btn btn-warning mr-3" />
          <input type="button" value="돌아가기" onclick="location.href='${ctp}/user/userMain';" class="btn btn-info" />
        </td>
      </tr>
    </table>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>