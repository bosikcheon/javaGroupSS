<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>dbtestList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/userTable.css" />
  <script>
    'use strict';
    
    $(document).ready(function() {
    	$("#userInputTable").hide();
    	$("#btnShow").show();
    	$("#btnHide").hide();
    	
    	$("#btnShow").click(function() {
    		$("#userInputTable").show();
      	$("#btnShow").hide();
      	$("#btnHide").show();
    	});
    	
    	$("#btnHide").click(function() {
    		$("#userInputTable").hide();
      	$("#btnShow").show();
      	$("#btnHide").hide();
    	});
    });
    
    function idCheck() {
    	let mid = myform.mid.value;
    	if(mid.trim() == "") {
    		alert("아이디를 입력하세요");
    		return false;
    	}
    	
    	let url = "${ctp}/dbtest/dbtestIdCheckForm?mid="+mid;
    	window.open(url, "idCheckForm", "width=500px,height=250px");
    }
    
    function delCheck(idx) {
    	let ans = confirm("현재 회원을 삭제처리 하시겠습니까?");
    	if(!ans) return false;
    	else location.href = '${ctp}/dbtest/dbtestDeleteOk?idx='+idx;
    }
    
    function updateCheck(idx) {
    	$(".userRow").hide();
    	$("#updateBtn"+idx).hide();
    	$("#row"+idx).show();
    }
    
    function idSearch() {
    	let mid = document.getElementById("idSearch").value;
    	if(mid.trim == "") {
    		alert("검색할 아이디를 입력하세요");
    		document.getElementById("idSearch").focus();
    		return false;
    	}
    	location.href = "${ctp}/dbtest/dbtestSearch/"+mid;
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <c:if test="${empty mid}"><h2 class="text-center">전체 회원 리스트</h2></c:if>
  <c:if test="${!empty mid}"><h3 class="text-center">아이디 : <font color="blue">${mid}</font>로 검색한결과 <font color="red">${fn:length(vos)}</font>건의 회원이 검색되었습니다.</h3></c:if>
  <hr/>
  <div class="text-center">
  	<!-- 
	  <div class="row">
	    <div class="col-md-2">
	  -->
	  <div class="row justify-content-between">
	  	<div class="col-3">
		    <!-- 메뉴(회원 가입폼 보이기/가리기) -->
		    <input type="button" value="회원가입창보이기" id="btnShow" class="btn btn-success mb-2"/>
		    <input type="button" value="회원가입창가리기" id="btnHide" class="btn btn-primary mb-2"/>
	    </div>
	    <!-- <div class="col-md-2 offset-md-8"> -->
	    <div class="col-3">
		    <input type="button" value="전체회원보기" onclick="location.href='${ctp}/dbtest/dbtestList';" class="btn btn-info mb-2"/>
	    </div>
	  </div>
  </div>
  <div id="userInputTable">
  	<!-- 회원 가입폼 -->
  	<form name="myform" method="post" action="${ctp}/dbtest/dbtestInputOk">
  	  <table class="table table-bordered">
	      <tr>
	        <th>아이디</th>
	        <td>
	          <div class="input-group">
	          	<input type="text" name="mid" id="mid" class="form-control" autofocus placeholder="아이디를 입력하세요"/>
	          	<input type="button" value="아이디중복체크" onclick="idCheck()" class="btn btn-success"/>
	          </div>
	        </td>
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
	          <input type="submit" value="회원가입" class="btn btn-success me-2" />
	          <input type="reset" value="다시입력" class="btn btn-warning" />
	        </td>
	      </tr>
	    </table>
  	</form>
  </div>
  <div id="userTable">
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
	          <a href="javascript:updateCheck(${vo.idx})" id="updateBtn${idx}"><span class="badge text-bg-warning">수정</span></a> /
	          <a href="javascript:delCheck(${vo.idx})"><span class="badge text-bg-danger">삭제</span></a>
	        </td>
	      </tr>
	      <tr id="row${vo.idx}" class="userRow" style="display:none">
	        <form name="myform${vo.idx}" method="post" action="${ctp}/dbtest/dbtestUpdateOk">
	          <td></td>
	          <td><input type="text" name="mid" value="${vo.mid}" class="form-control" readonly ></td>
	          <td><input type="password" name="pwd" value="${vo.pwd}" class="form-control"></td>
	          <td><input type="text" name="name" value="${vo.name}" class="form-control"></td>
	          <td><input type="text" name="age" value="${vo.age}" class="form-control"></td>
	          <td>
	            <input type="radio" name="gender" value="남자" ${vo.gender=='남자' ? 'checked' : ''}>남자<br/>
	            <input type="radio" name="gender" value="여자" ${vo.gender=='여자' ? 'checked' : ''}>여자
	          </td>
	          <td><input type="text" name="address" value="${vo.address}" class="form-control"></td>
	          <td><input type="submit" value="수정" class="btn btn-secondary btn-sm"/></td>
	          <input type="hidden" name="idx" value="${vo.idx}" />
	        </form>
	      </tr>
	    </c:forEach>
	  </table>
	  <!-- 
	  <table class="table table-bordered">
	    <tr class="text-center row align-items-center">
	      <td class="text-start pt-5">안녕하세요</td>
	      <td>안녕하세요</td>
	      <td class="text-end">반갑습니다.</td>
	    </tr>
	  </table>
	   -->
  </div>
  <div class="row">
    <div class="col-md-6 offset-md-3">
		  <div class="input-group">
		    <span class="input-group-text">아이디검색</span>
		    <input type="text" name="idSearch" id="idSearch" placeholder="검색할 아이디를 입력하세요" class="form-control"/>
		    <input type="button" value="검색" onclick="idSearch()" class="btn btn-success"/>
		  </div>
	  </div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>