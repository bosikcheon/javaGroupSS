<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    function fileDelete(file) {
    	let ans = confirm("선택한 파일을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/thumbnail/thumbnailDelete",
    		data : {file : file},
    		success:function(res) {
    			if(res != "0") {
    				alert("파일을 삭제처리하였습니다.");
    				location.reload();
    			}
    			else alert("파일 삭제 실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    function fileDeleteAll() {
    	let ans = confirm("모든 파일을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/thumbnail/thumbnailDeleteAll",
    		success:function(res) {
    			if(res != "0") {
    				alert("파일을 삭제처리하였습니다.");
    				location.reload();
    			}
    			else alert("파일 삭제 실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
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
  <h2>저장된 썸네일 이미지 리스트</h2>
  <div>서버에 저장된 파일정보 : 총 ${fCount}건)</div>
  <hr/>
  <div class="row">
    <div class="col"><a href="thumbnailForm" class="btn btn-success mb-2">돌아가기</a></div>
    <div class="col text-end"><a href="javascript:fileDeleteAll()" class="btn btn-danger mb-2">전체삭제</a></div>
  </div>
  <table class="table table-hover text-center">
    <c:set var="cnt" value="${fCount / 2}"/>
    <tr class="table-secondary">
      <th>번호</th>
      <th>파일명</th>
      <th>썸네일이미지</th>
      <th>비고</th>
    </tr>
    <c:forEach var="file" items="${files}" varStatus="st">
      <c:if test="${fn:substring(file,0,2) == 's_'}">
	      <tr>
	        <td>
	          <c:if test="${cnt >= 0}"><fmt:parseNumber value="${cnt}" integerOnly="true" /></c:if>
	          <c:if test="${cnt < 0}"><fmt:parseNumber value="${cnt * -1}" integerOnly="true" /></c:if>
	        </td>
	        <td>${fn:substring(file,2,fn:length(file))}</td>
	        <td><a href="${ctp}/thumbnail/${fn:substring(file,2,fn:length(file))}" target="_blank"><img src="${ctp}/thumbnail/${file}"/></a></td>
	        <td><input type="button" value="삭제" onclick="fileDelete('${file}')" class="btn btn-danger btn-sm"/></td>
	      </tr>
      </c:if>
      <c:set var="cnt" value="${cnt - 1}"/>
    </c:forEach>
  </table>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>