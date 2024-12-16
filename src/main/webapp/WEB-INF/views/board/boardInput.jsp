<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>boardInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <!-- <script src="https://cdn.ckeditor.com/4.22.1/standard/ckeditor.js"></script> -->
  <script src="${ctp}/ckeditor/ckeditor.js"></script>
  <script>
    'use strict';
    
    function fCheck() {
    	
    	
    	//myform.submit();
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <form name="myform" method="post" onsubmit="return fCheck()">
	  <h2 class="text-center">게 시 판 글 쓰 기</h2>
    <table class="table table-bordered">
      <tr>
        <th class="text-center align-middle">글쓴이</th>
        <td>
          <input type="text" name="nickName" id="nickName" value="${sNickName}" class="form-control" readonly />
        </td>
      </tr>
      <tr class="mb-2">
        <th class="text-center align-middle">글제목</th>
        <td>
          <input type="text" name="title" id="title" placeholder="글제목을 입력하세요" class="form-control mr-1" autofocus required />
        </td>
    	</tr>
      <tr class="mb-2">
        <th class="text-center align-middle"><label for="content" class="form-label">글내용</label></th>
        <td><textarea rows="6" name="content" id="CKEDITOR" class="form-control" placeholder="게시글 내용을 입력하세요" required></textarea></td>
        <script>
        	CKEDITOR.replace("content",{
        		height: 480,
        		filebrowserUploadUrl: "${ctp}/imageUpload",
        		uploadUrl : "${ctp}/imageUpload"
        	});
        </script>
      </tr>
      <tr class="mb-2">
        <th class="text-center"><label for="openSw" class="form-label">공개여부</label></th>
        <td>
          <input type="radio" name="openSw" id="openSw1" value="공개" class="me-1" checked /><label for="openSw1">공개</label> &nbsp;&nbsp;
          <input type="radio" name="openSw" id="openSw2" value="비공개" class="me-1" /><label for="openSw2">비공개</label>
        </td>
      </tr>
    </table>
    <table class="table table-borderless">
      <tr>
			  <td class="text-left">
		    	<button type="button" onclick="location.href='boardList'" class="btn btn-info mb-2">돌아가기</button>
			    <button type="reset" class="btn btn-warning mb-2">다시입력</button>
        </td>
        <td class="text-end">
			    <button type="submit" class="btn btn-success mb-2">게시글등록</button>
			  </td>
      </tr>
    </table>
    <input type="hidden" name="mid" value="${sMid}" />
    <input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>