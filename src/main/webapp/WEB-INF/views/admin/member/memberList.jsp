<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>admin/member/memberList.jsp</title>
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
    
    function levelChange(e) {
    	let ans = confirm("선택한 회원의 등급을 변경하시겠습니까?");
    	if(!ans) {
    		location.reload();
    		return false;
    	}
    	// items[0] : 레벨번호, items[1] : 고유번호
    	let items = e.value.split("/");
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/admin/member/memberLevelChange",
    		data : {
    			level : items[0],
    			idx   : items[1]
    		},
    		success:function(res) {
    			if(res != 0) {
    				alert("등급 수정 완료!!");
    				location.reload();
    			}
    			else alert("등급 수정 실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    /* 등급을 변경하거나 페이지를 변경한다면 무조건 1Page로 간다는 전제조건으로 시작한다. */
    
    // 등급별 조회
    function levelViewCheck() {
    	let level = document.getElementById("levelView").value;
    	location.href = "${ctp}/admin/member/memberList?pageSize=${pageSize}&pag=1&level="+level;
    }
    
    // 사용자 페이지 설정
    function pageSizeChange() {
    	let pageSize = document.getElementById("pageSize").value;
    	//location.href = "MemberList.ad?pageSize="+pageSize+"&pag=${pag}&level=${level}";
    	location.href = "MemberList.ad?pageSize="+pageSize+"&pag=1&level=${level}";
    }
    
    // 탈퇴신청후 30일 지난 회원들 DB에서 자료 삭제시키기
    function delCheck(mid) {
    	let ans = confirm("회원정보를 삭제하시겠습니까?");
    	if(!ans) return false;
    		
    	$.ajax({
    		type : "post",
    		url  : "MemberDeleteOk.ad",
    		data : {mid : mid},
    		success:function(res) {
    			if(res != 0) {
    				alert(mid + " 회원정보를 DB에서 삭제 시켰습니다.");
    				location.reload();
    			}
    			else alert(mid + "회원정보 삭제 실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    		
    }
    
    // 전체 선택
    function allCheck() {
    	for(let i=0; i<myform.levelCheck.length; i++) {
    		myform.levelCheck[i].checked = true;
    	}
    }
    
    // 전체 해제
    function allReset() {
    	for(let i=0; i<myform.levelCheck.length; i++) {
    		myform.levelCheck[i].checked = false;
    	}
    }
    
    // 선택 반전
    function reverseCheck() {
    	for(let i=0; i<myform.levelCheck.length; i++) {
    		myform.levelCheck[i].checked = !myform.levelCheck[i].checked;
    	}
    }
    
    // 선택항목 등급변경처리
    function levelSelectCheck() {
    	let select = document.getElementById("levelSelect");
    	let levelSelectText = select.options[select.selectedIndex].text;
    	let levelSelect = document.getElementById("levelSelect").value;
    	
    	let idxSelectArray = '';
    	for(let i=0; i<myform.levelCheck.length; i++) {
    		if(myform.levelCheck[i].checked) idxSelectArray += myform.levelCheck[i].value + "/";
    	}
    	idxSelectArray = idxSelectArray.substring(0,idxSelectArray.lastIndexOf("/"));
    	
    	let query = {
    			levelSelect : levelSelect,
    			idxSelectArray : idxSelectArray
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/admin/member/memberLevelSelectCheck",
    		data : query,
    		success:function(res) {
    			if(res != "0") alert("선택한 항목들이 " + levelSelectText + "(으)로 변경되었습니다.");
    			else alert("등급변경 실패~~");
    			location.reload();
    		},
    		error : function() {
    			alert("전송오류");
    		}
    	});
    	
    }
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-5">회 원 리 스 트</h2>
  
  <table class="table table-borderless m-0">
    <tr>
      <td>
        <div class="row">
          <div class="col-7">
		        <div class="input-group">
		          <input type="button" value="전체선택" onclick="allCheck()" class="btn btn-success btn-sm me-1"/>
		          <input type="button" value="전체취소" onclick="allReset()" class="btn btn-warning btn-sm me-1"/>
		          <input type="button" value="선택반전" onclick="reverseCheck()" class="btn btn-info btn-sm me-2"/>
		          <select name="levelSelect" id="levelSelect" class="form-select">
		            <option value="">등급선택</option>
		            <option value="2">정회원</option>
		            <option value="1">우수회원</option>
		            <option value="3">준회원</option>
		          </select>
		          <input type="button" value="선택항목등급변경" onclick="levelSelectCheck()" class="btn btn-success btn-sm me-1"/>
		        </div>
	        </div>
	        <div class="col-2"></div>
	        <div class="col-3">
		        <div class="input-group">
			        <span class="input-group-text" style="background-color:#ddd">등급별조회</span>
			        <select name="levelView" id="levelView" onchange="levelViewCheck()" class="form-select">
			          <option value="999" <c:if test="${level == 999}">selected</c:if> >전체회원</option>
			          <option value="3"   <c:if test="${level == 3}" >selected</c:if> >준회원</option>
			          <option value="2"   <c:if test="${level == 2}" >selected</c:if> >정회원</option>
			          <option value="1"   <c:if test="${level == 1}" >selected</c:if> >우수회원</option>
			          <option value="99"  <c:if test="${level == 99}">selected</c:if> >탈퇴신청회원</option>
			          <option value="0"   <c:if test="${level == 0}" >selected</c:if>  >관리자</option>
			        </select>
		        </div>
	        </div>
        </div>
      </td>
    </tr>
  </table>  
  
  <form name="myform">
	  <table class="table table-hover" id="userTable">
	    <tr class="table-secondary">
	      <th>번호</th>
	      <th>닉네임</th>
	      <th>아이디</th>
	      <th>성명</th>
	      <th>성별</th>
	      <th>생일</th>
	      <!-- <th>이메일</th> -->
	      <th>최종방문일</th>
	      <th>활동여부</th>
	      <th>현재레벨</th>
	    </tr>
		  <c:forEach var="vo" items="${vos}" varStatus="st">
		    <tr>
		      <td>
		        <c:if test="${vo.level != 0}"><input type="checkbox" name="levelCheck" id="levelCheck${vo.idx}" value="${vo.idx}" class="form-check-input" /></c:if>
		        <c:if test="${vo.level == 0}"><input type="checkbox" name="levelCheck" id="levelCheck${vo.idx}" value="${vo.idx}" disabled class="form-check-input"/></c:if>
		        ${curScrStartNo}
		      </td>
		      <td>${vo.nickName}</td>
		      <c:if test="${vo.userInfor == '공개'}">
			      <c:if test="${empty vo.content}"><c:set var="content" value="내용없음" /></c:if>
			      <c:if test="${!empty vo.content}"><c:set var="content" value="${vo.content}" /></c:if>
			      <td><a href="#" onclick='contentView("${content}")' data-bs-toggle="modal" data-bs-target="#myModal">${vo.mid}</a></td>
			      <td>${vo.name}</td>
			      <td>${vo.gender}</td>
			      <td>${fn:substring(vo.birthday,0,10)}</td>
			      <%-- <td>${vo.email}</td> --%>
			      <td>
			        <c:if test="${sMid == vo.mid}">${fn:substring(sLastDate,0,16)}</c:if>
			        <c:if test="${sMid != vo.mid}">${fn:substring(vo.lastDate,0,16)}</c:if>
			      </td>
		      </c:if>
		      <c:if test="${vo.userInfor != '공개'}">
		        <td colspan="5" class="text-center">비 공 개</td>
		      </c:if>
		      <td>
		        <c:if test="${vo.userDel == 'OK'}"><c:set var="strUserDel" value="탈퇴신청중" /></c:if>
		        <c:if test="${vo.userDel != 'OK'}"><c:set var="strUserDel" value="활동중" /></c:if>
		        <c:if test="${vo.userDel == 'OK'}"><font color="red">${strUserDel}</font></c:if>
		        <c:if test="${vo.userDel != 'OK'}">${strUserDel}</c:if>
		      </td>
		      <td>
		        <select name="level" id="level" onchange="levelChange(this)" class="form-select-sm">
		          <option value="3/${vo.idx}"  ${vo.level == 3 ? 'selected' : ''}>준회원</option>
		          <option value="2/${vo.idx}"  ${vo.level == 2 ? 'selected' : ''}>정회원</option>
		          <option value="1/${vo.idx}"  ${vo.level == 1 ? 'selected' : ''}>우수회원</option>
		          <option value="0/${vo.idx}"  ${vo.level == 0 ? 'selected' : ''}>관리자</option>
		          <option value="99/${vo.idx}" ${vo.level == 99 ? 'selected' : ''}>탈퇴신청회원</option>
		        </select>
		      </td>
		    </tr>
		    <c:set var="curScrStartNo" value="${curScrStartNo - 1}" />
	  	</c:forEach>
	  </table>
  </form>
  
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
</body>
</html>