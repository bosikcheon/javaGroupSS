<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>multiFile.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    // 멀티파일 업로드처리
    function fCheck() {
    	let fName = document.getElementById("@RequestParam(name="part", defaultValue = "전체", required = false) String part,").value;
    	let fileSize = 0;
    	let maxSize = 1024 * 1024 * 30; 	// 저장파일 최대용량을 20MByte까지로 제한
    	let ext = "";
    	
    	if(fName.trim() == "") {
    		alert("업로드할 파일을 선택하세요");
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
    	
  		myform.submit();
    }
    
    // 개별 파일 삭제
    function fileDelete(file) {
    	let ans = confirm("선택한 파일을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/fileUpload/fileDelete",
    		data : {file : file},
    		success:function(res) {
    			if(res != "0") {
    				alert('파일이 삭제되었습니다.');
    				location.reload();
    			}
    			else alert("파일 삭제 실패~~");
    		},
    		error : function() {
    			alert("전송오류");
    		}
    	});
    }
    
    // 전체 파일 삭제
    function fileDeleteAll() {
    	let ans = confirm("폴더내 모든 파일을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/fileUpload/fileDeleteAll",
    		success:function(res) {
    			if(res != "0") {
    				alert('파일이 삭제되었습니다.');
    				location.reload();
    			}
    			else alert("파일 삭제 실패~~");
    		},
    		error : function() {
    			alert("전송오류");
    		}
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>멀티 파일 업로드 연습</h2>
  <form name="myform" method="post" enctype="multipart/form-data">
  	<p>올린이 : <input type="text" name="mid" value="${sMid}"/></p>
  	<p>
  	  <input type="file" name="fName" id="fName" multiple class="form-control" accept=".jpg,.gif,.png,.zip,.ppt,.pptx,.hwp" />
  	</p>
  	<p>
  		<input type="button" value="파일업로드" onclick="fCheck()" class="btn btn-success"/>
  		<input type="reset" value="다시선택" class="btn btn-warning"/>
  		<input type="button" value="싱글파일업로드(파일리스트)" onclick="location.href='fileUpload';" class="btn btn-primary"/>
  	</p>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>