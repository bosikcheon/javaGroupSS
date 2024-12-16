<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>adminContent.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<p><br/></p>
<div class="container">
  <h3>관리자 메인화면</h3>
  <hr/>
  <!-- 
  	방명록/게시판/자료실에 최근 올라온글 표시...(개수)
  	신규회원~~처리....
  	탈퇴신청회원~~ 처리...
  -->
  <p>방명록 새글(최근1주일) : <font color="blue"><b>${guestRecentCnt}</b></font>건</p>
  <p>게시판 새글(최근1주일) : <font color="blue"><b>${boardRecentCnt}</b></font>건</p>
  <p>신고글(최근1주일) : __건</p>
  <p>신규등록회원(준회원) : <a href="${ctp}/admin/member/memberList?level=3"><font color="blue"><b>${level3Cnt}</b></font></a>건</p>
  <p>탈퇴신청회원 : <a href="${ctp}/admin/member/memberList?level=99"><font color="blue"><b>${level99Cnt}</b></font></a>건</p>
</div>
</body>
</html>