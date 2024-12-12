<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>mail.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/userTable.css" />
  <script>
    'use strict'
    
    function emailCheck(email) {
    	myform.toMail.value = email;
    	document.getElementById("btnModalClose").click();
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">메 일 보 내 기</h2>
  <p class="text-center">(받는 사람의 메일주소를 정확히 입력하셔야 합니다.)</p>
  <form name="myform" method="post">
    <table class="table table-bordered" id="userTable">
      <tr>
        <th>받는사람</th>
        <td>
          <div class="input-group">
          	<input type="text" name="toMail" placeholder="받는사람 메일주소를 입력하세요" autofocus required class="form-control" />
          	<!-- <input type="button" value="주소록" class="btn btn-success" /> -->
          	<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">주소록</button>
          </div>
        </td>
      </tr>
      <tr>
      	<th>메일제목</th>
      	<td><input type="text" name="title" placeholder="메일 제목을 입력하세요" required class="form-control" /></td>
      </tr>
      <tr>
      	<th>메일내용</th>
      	<td><textarea rows="7" name="content" placeholder="메일 내용을 입력하세요" required class="form-control"></textarea></td>
      </tr>
      <tr>
        <td colspan="2" class="text-center">
          <input type="submit" value="메일보내기" class="btn btn-success me-2"/>
          <input type="reset" value="다시쓰기" class="btn btn-warning"/>
        </td>
      </tr>
    </table>
  </form>
</div>
<p><br/></p>

<!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog modal-dialog-scrollable">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">주 소 록</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
        <table class="table table-hover text-center">
          <tr class="table-secondary">
            <th>번호</th>
            <th>아이디</th>
            <th>이메일주소</th>
          </tr>
          <c:forEach var="vo" items="${vos}" varStatus="st">
            <tr>
              <td>${st.count}</td>
              <td>${vo.mid}</td>
              <td><a href="javascript:emailCheck('${vo.email}')">${vo.email}</a></td>
            </tr>
          </c:forEach>
        </table> 
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal" id="btnModalClose">Close</button>
      </div>

    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>