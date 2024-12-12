<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>memberList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/userTable.css" />
  <style>
    th, td {
      text-align: center;
    }
  </style>
  <script>
    'use strict';
    
    function contentView(content) {
    	$("#myModal #modalContent").html(content);
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-5">회 원 리 스 트</h2>
  <table class="table table-hover" id="userTable">
    <tr class="table-secondary">
      <th>번호</th>
      <th>닉네임</th>
      <th>아이디</th>
      <th>성명</th>
      <th>성별</th>
      <th>생일</th>
      <th>이메일</th>
      <th>최종방문일</th>
    </tr>
	  <c:forEach var="vo" items="${vos}" varStatus="st">
	    <tr>
	      <td>${st.count}</td>
	      <td>${vo.nickName}</td>
	      <c:if test="${vo.userInfor == '공개'}">
		      <c:if test="${empty vo.content}"><c:set var="content" value="내용없음" /></c:if>
		      <c:if test="${!empty vo.content}"><c:set var="content" value="${vo.content}" /></c:if>
		      <td><a href="#" onclick='contentView("${content}")' data-bs-toggle="modal" data-bs-target="#myModal">${vo.mid}</a></td>
		      <td>${vo.name}:${content}</td>
		      <td>${vo.gender}</td>
		      <td>${fn:substring(vo.birthday,0,10)}</td>
		      <td>${vo.email}</td>
		      <td>
		        <c:if test="${sMid == vo.mid}">${fn:substring(sLastDate,0,16)}</c:if>
		        <c:if test="${sMid != vo.mid}">${fn:substring(vo.lastDate,0,16)}</c:if>
		      </td>
	      </c:if>
	      <c:if test="${vo.userInfor != '공개'}">
	        <td colspan="6" class="text-center">비 공 개</td>
	      </c:if>
	    </tr>
  	</c:forEach>
  	<!-- <tr><td colspan="8" class="m-0 p-0"></td></tr> -->
  </table>
  
<!-- 블록페이지 시작 -->
<div class="text-center">
  <ul class="pagination justify-content-center">
	  <c:if test="${pag > 1}"><li class="page-item"><a class="page-link text-secondary" href="memberList?pag=1&pageSize=${pageSize}">첫페이지</a></li></c:if>
	  <c:if test="${curBlock > 0}"><li class="page-item"><a class="page-link text-secondary" href="memberList?pag=${(curBlock-1)*blockSize + 1}&pageSize=${pageSize}">이전블록</a></li></c:if>
	  <c:forEach var="i" begin="${(curBlock*blockSize)+1}" end="${(curBlock*blockSize) + blockSize}" varStatus="st">
	    <c:if test="${i <= totPage && i == pag}"><li class="page-item active"><a class="page-link bg-secondary border-secondary" href="memberList?pag=${i}&pageSize=${pageSize}">${i}</a></li></c:if>
	    <c:if test="${i <= totPage && i != pag}"><li class="page-item"><a class="page-link text-secondary" href="memberList?pag=${i}&pageSize=${pageSize}">${i}</a></li></c:if>
	  </c:forEach>
	  <c:if test="${curBlock < lastBlock}"><li class="page-item"><a class="page-link text-secondary" href="memberList?pag=${(curBlock+1)*blockSize+1}&pageSize=${pageSize}">다음블록</a></li></c:if>
	  <c:if test="${pag < totPage}"><li class="page-item"><a class="page-link text-secondary" href="memberList?pag=${totPage}&pageSize=${pageSize}">마지막페이지</a></li></c:if>
  </ul>
</div>
<!-- 블록페이지 끝 -->

<!-- The Modal -->
<div class="modal fade" id="myModal">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
    <div class="modal-content">
    
      <!-- Modal Header -->
      <div class="modal-header">
        <h3 class="modal-title">자기소개</h3>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      
      <!-- Modal body -->
      <div class="modal-body">
        <span id="modalContent">${vo.content}</span>
      </div>
      
      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
      </div>
      
    </div>
  </div>
</div>

</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>