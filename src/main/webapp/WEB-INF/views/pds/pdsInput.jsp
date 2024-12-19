<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pdsInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
    th {
      text-align: center;
      background-color: #eee !important;
    }
  </style>
  <script>
	  'use strict';
	  
	  // 멀티파일 업로드처리
	  function fCheck() {
		  let title = document.getElementById("title").value;
	  	let fName = document.getElementById("fName").value;
	  	let fileSize = 0;
	  	let maxSize = 1024 * 1024 * 30; 	// 저장파일 최대용량을 30MByte까지로 제한
	  	let ext = "";
	  	
	  	if(fName.trim() == "") {
	  		alert("업로드할 파일을 선택하세요");
	  		return false;
	  	}
	  	else if(title.trim() == "") {
	  		alert("제목을 입력하세요");
	  		document.getElementById("title").focus();
	  		return false;
	  	}
	  	
	  	let fileLength = document.getElementById("fName").files.length;	// 선택한 파일의 개수
	  	
	  	for(let i=0; i<fileLength; i++) {
	  		fName = document.getElementById("fName").files[i].name;
	  		ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
	  		fileSize += document.getElementById("fName").files[i].size;
	  		if(ext != 'jpg' && ext != 'gif' && ext != 'png' && ext != 'hwp' && ext != 'zip' && ext != 'ppt' && ext != 'pptx') {
	    		alert("업로드 가능한 파일은 'jpg/gif/png/hwp/zip/ppt/pptx/'만 가능합니다.")
	    		return false;
	    	}
	  	}
	  	
	  	if(fileSize > maxSize) {
	  		alert("업로드할 파일의 최대용량은 30MByte 입니다.");
	  		return false;
	  	}
	  	myform.fSize.value = fileSize;
			myform.submit();
	  }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">자 료 올 리 기</h2>
  <br/>
  <form name="myform" method="post" enctype="multipart/form-data">
    <table class="table table-bordered">
      <tr class="align-middle">
        <th>분류</th>
        <td>
          <select name="part" id="part" class="form-control">
            <option>학습</option>
            <option>여행</option>
            <option>음식</option>
            <option>기타</option>
          </select>
        </td>
      </tr>
      <tr class="align-middle">
        <th>자료파일</th>
        <td>
          <div>
          	<!-- input태그의 file속성에 사용하는 name의 변수는 VO에 필드명과 같아서는 안된다.(400에러) -->
	          <input type="file" name="file" id="fName" multiple class="form-control">
          </div>
        </td>
      </tr>
      <tr>
        <th>올린이</th>
        <td>${sNickName}</td>
      </tr>
      <tr class="align-middle">
        <th>제목</th>
        <td><input type="text" name="title" id="title" placeholder="제목을 입력하세요" class="form-control"/></td>
      </tr>
      <tr class="align-middle">
        <th>상세내역</th>
        <td><textarea rows="4" name="content" id="content" placeholder="상세내역을 입력하세요" class="form-control"></textarea></td>
      </tr>
      <tr>
        <th>공개여부</th>
        <td>
          <input type="radio" name="openSw" id="openSw1" value="공개" class="me-1" checked /><label for="openSw1">공개</label> &nbsp;&nbsp;
          <input type="radio" name="openSw" id="openSw2" value="비공개" class="me-1" /><label for="openSw2">비공개</label>
        </td>
      </tr>
      <tr class="align-middle">
        <th>비밀번호</th>
        <td>
          <input type="password" name="pwd" id="pwd" value="1234" class="form-control" required />
        </td>
      </tr>
      <tr>
        <td colspan="2" class="text-center">
          <input type="button" value="자료올리기" onclick="fCheck()" class="btn btn-success me-2"/>
          <!-- <input type="submit" value="자료올리기" class="btn btn-success"/> -->
          <input type="reset" value="다시쓰기" class="btn btn-warning me-2"/>
          <input type="button" value="돌아가기" onclick="location.href='pdsList?part=${part}'" class="btn btn-info"/>
        </td>
      </tr>
    </table>
    <input type="hidden" name="mid" value="${sMid}"/>
    <input type="hidden" name="nickName" value="${sNickName}"/>
    <input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}"/>
    <input type="hidden" name="fSize" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>