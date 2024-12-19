<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pdsList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    // 다운로드수 증가
    function downNumCheck(idx) {
    	$.ajax({
    		url  : "pdsDownNumCheck",
    		type : "post",
    		data : {idx : idx},
    		success:function() {
    			location.reload();
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 자료실 삭제(DB + 자료)
    function pdsDeleteCheck(idx,fSName) {
      let ans = confirm("선택하신 자료를 삭제하시겠습니까?");
      if(!ans) return false;
      
      $.ajax({
    	  type : "post",
    	  url  : "pdsDeleteCheck",
    	  data : {
    		  idx : idx,
    		  fSName : fSName
    	  },
    	  success:function(res) {
    		  if(res != "0") {
    			  alert("자료가 삭제되었습니다.");
    			  location.reload();
    		  }
    		  else alert('삭제 실패~');
    	  },
    	  error : function() {
    		  alert("전송오류!");
    	  }
      });
    }
    
    // 분류별 검색하기
    function partCheck() {
    	let part = $("#part").val();
    	//if(part != '') location.href = "PdsSearchList.pds?part="+part;
    	//if(part != '') location.href = "pdsList?pag=${pag}&pageSize=${pageSize}&part="+part;
    	//if(part == '전체') location.href = "pdsList";
    	
    	location.href = "pdsList?pag=${pageVO.pag}&pageSize=${pageVO.pageSize}&part="+part;
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">자 료 실 리 스 트</h2>
  <br/>
  <table class="table table-borderless m-0 p-0">
    <tr>
      <td>
        <%-- <c:if test="${sLevel > 2 || sLevel == 0}"><a href="pdsInput?part=${part}" class="btn btn-success btn-sm">자료올리기</a></c:if> --%>
        <c:if test="${sLevel > 2 || sLevel == 0}"><a href="pdsInput" class="btn btn-success btn-sm">자료올리기</a></c:if>
      </td>
      <td class="text-end">
        <form name="partForm">
          <select name="part" id="part" onchange="partCheck()">
            <option ${pageVO.part == '전체' ? 'selected' : ''}>전체</option>
            <option ${pageVO.part == '학습' ? 'selected' : ''}>학습</option>
            <option ${pageVO.part == '여행' ? 'selected' : ''}>여행</option>
            <option ${pageVO.part == '음식' ? 'selected' : ''}>음식</option>
            <option ${pageVO.part == '기타' ? 'selected' : ''}>기타</option>
          </select>
        </form>
      </td>
    </tr>
  </table>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>번호</th>
      <th>올린이</th>
      <th>자료제목</th>
      <th>올린날짜</th>
      <th>분류</th>
      <th>파일명(크기)</th>
      <th>다운수</th>
      <th>비고</th>
    </tr>
    <c:set var="curScrStartNo" value="${pageVO.curScrStartNo}"/>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr class="align-middle">
        <td>${curScrStartNo}</td>
        <td>${vo.nickName}</td>
        <c:if test="${vo.openSw == '공개' || sMid == vo.mid || sLevel == 0}">
	        <td>
	          <a href="pdsContent?idx=${vo.idx}&part=${pageVO.part}" class="text-decoration-none text-dark link-primary">${vo.title}</a>
	        </td>
	        <td>${vo.FDate}</td>
	        <td>${vo.part}</td>
	        <td>
	          <c:set var="fNames" value="${fn:split(vo.FName,'/')}"/>
	          <c:set var="fSNames" value="${fn:split(vo.FSName,'/')}"/>
	          <c:forEach var="fName" items="${fNames}" varStatus="st">
		          <c:if test="${sLevel != 1}"><a href="${ctp}/pds/${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})" class="text-decoration-none text-dark link-primary">${fName}</a><br/></c:if>
		          <c:if test="${sLevel == 1}">${fName}<br/></c:if>
	          </c:forEach>          
	          (<fmt:formatNumber value="${vo.FSize/1024}" pattern="#,##0" />KByte)
	        </td>
	        <td>${vo.downNum}</td>
	        <td>
	          <c:if test="${vo.mid == sMid || sLevel == 0}">
	  	        <a href="javascript:pdsDeleteCheck('${vo.idx}','${vo.FSName}')" class="badge bg-danger text-decoration-none">삭제</a><br/>
	          </c:if>
	          <c:if test="${sLevel != 1}"><a href="PdsTotalDown.pds?idx=${vo.idx}" class="badge bg-primary text-decoration-none">전체파일다운</a></c:if>
	          <c:if test="${sLevel == 1}">정회원메뉴</c:if>
	        </td>
        </c:if>
        <c:if test="${vo.openSw != '공개' && sMid != vo.mid && sLevel != 0}">
          <td colspan="6" class="text-center">비공개 파일</td>
        </c:if>
      </tr>
      <c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
    </c:forEach>
    <tr><td colspan="8" class="m-0 p-0"></td></tr>
  </table>
</div>

<!-- 블록페이지 시작 -->
<div class="text-center">
  <ul class="pagination justify-content-center">
	  <c:if test="${pageVO.pag > 1}"><li class="page-item"><a class="page-link text-secondary" href="pdsList?part=${pageVO.part}&pag=1&pageSize=${pageVO.pageSize}">첫페이지</a></li></c:if>
	  <c:if test="${pageVO.curBlock > 0}"><li class="page-item"><a class="page-link text-secondary" href="pdsList?part=${pageVO.part}&pag=${(pageVO.curBlock-1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}">이전블록</a></li></c:if>
	  <c:forEach var="i" begin="${(pageVO.curBlock*pageVO.blockSize)+1}" end="${(pageVO.curBlock*pageVO.blockSize) + pageVO.blockSize}" varStatus="st">
	    <c:if test="${i <= pageVO.totPage && i == pageVO.pag}"><li class="page-item active"><a class="page-link bg-secondary border-secondary" href="pdsList?part=${pageVO.part}&pag=${i}&pageSize=${pageVO.pageSize}">${i}</a></li></c:if>
	    <c:if test="${i <= pageVO.totPage && i != pageVO.pag}"><li class="page-item"><a class="page-link text-secondary" href="pdsList?part=${pageVO.part}&pag=${i}&pageSize=${pageVO.pageSize}">${i}</a></li></c:if>
	  </c:forEach>
	  <c:if test="${pageVO.curBlock < pageVO.lastBlock}"><li class="page-item"><a class="page-link text-secondary" href="pdsList?part=${pageVO.part}&pag=${(pageVO.curBlock+1)*pageVO.blockSize+1}&pageSize=${pageVO.pageSize}">다음블록</a></li></c:if>
	  <c:if test="${pageVO.pag < pageVO.totPage}"><li class="page-item"><a class="page-link text-secondary" href="pdsList?part=${pageVO.part}&pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}">마지막페이지</a></li></c:if>
  </ul>
</div>
<!-- 블록페이지 끝 -->

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>