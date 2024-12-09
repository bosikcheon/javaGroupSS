<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>userList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    function delCheck(idx) {
    	let ans = confirm("현재 회원을 삭제처리 하시겠습니까?");
    	if(!ans) return false;
    	else location.href = '${ctp}/user/userDeleteOk?idx='+idx;
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-4">회 원 전 체 리 스 트</h2>
  <table class="table table-borderless m-0">
    <tr>
      <td class="text-right"><a href="${ctp}/user/userMain" class="btn btn-success btn-sm">돌아가기</a></td>
    </tr>
  </table>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>번호</th>
      <th>아이디</th>
      <th>비밀번호</th>
      <th>성명</th>
      <th>나이</th>
      <th>성별</th>
      <th>주소</th>
      <th>비고</th>
    </tr>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
        <td>${st.count}</td>
        <td>${vo.mid}</td>
        <td>${vo.pwd}</td>
        <td>${vo.name}</td>
        <td>${vo.age}</td>
        <td>${vo.gender}</td>
        <td>${vo.address}</td>
        <td>
          <%-- 
          <a href="${ctp}/user/userUpdate?idx=${vo.idx}" class="badge text-bg-warning">수정</a> /
          <a href="javascript:delCheck(${vo.idx})" class="badge text-bg-danger">삭제</a>
           --%>
          <span onclick="location.href='${ctp}/user/userUpdate?idx=${vo.idx}';" class="badge text-bg-warning" style="cursor:pointer">수정</span> /
          <a href="javascript:delCheck(${vo.idx})"><span class="badge text-bg-danger">삭제</span></a>
        </td>
      </tr>
    </c:forEach>
    <tr><td colspan="8" class="m-0 p-0"></td></tr>
  </table>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>