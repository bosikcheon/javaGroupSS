<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>boardSearchList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    // 사용자 페이지 설정
    function pageSizeChange() {
    	let pageSize = document.getElementById("pageSize").value;
    	location.href = "boardSearchList?search=${pageVO.search}&searchString=${pageVO.searchString}&pageSize="+pageSize+"&pag=1";
    }
    
    function contentView(content) {
    	$("#myModal #modalContent").html(content);
    	$("#myModal").modal({
        fadeDuration: 1000,
        fadeDelay: 0.5,
      });
    }
    
    function cursorMove() {
    	document.getElementById("searchString").focus();
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">게시판 조건별 리스트</h2>
  <div class="text-center">
    (<font color='blue'>${pageVO.searchTitle}</font>(으)로 <font color='blue'><b>${pageVO.searchString}</b></font>(를)을 검색한 결과 <font color='red'><b>${pageVO.totRecCnt}</b></font>건이 검색되었습니다.)
  </div>
  <table class="table table-borderless mt-3 mb-0 p-0">
    <tr>
      <td><a href="boardList" class="btn btn-success btn-sm">돌아가기</a></td>
      <td class="text-end">한페이지 분량 :
        <select name="pageSize" id="pageSize" onchange="pageSizeChange()">
          <option value="5"  <c:if test="${pageVO.pageSize == 5}"  >selected</c:if>>5건</option>
          <option value="10" <c:if test="${pageVO.pageSize == 10}" >selected</c:if>>10건</option>
          <option value="15" <c:if test="${pageVO.pageSize == 15}" >selected</c:if>>15건</option>
          <option value="20" <c:if test="${pageVO.pageSize == 20}" >selected</c:if>>20건</option>
          <option value="30" <c:if test="${pageVO.pageSize == 30}" >selected</c:if>>30건</option>
          <option value="30" <c:if test="${pageVO.pageSize == 50}" >selected</c:if>>50건</option>
        </select>
      </td>
    </tr>
  </table>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>글번호</th>
      <th>글제목</th>
      <th>글쓴이</th>
      <th>글쓴날짜</th>
      <th>조회수(좋아요)</th>
    </tr>
    <c:set var="curScrStartNo" value="${pageVO.curScrStartNo}" />
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <c:if test="${vo.openSw =='공개' || sMid == vo.mid || sLevel == 0}">
		    <tr>
		      <td>${curScrStartNo}</td>
		      <td class="text-left">
		        <c:if test="${vo.claim == 'NO' || sMid == vo.mid || sLevel == 0}"><a href="boardContent?search=${pageVO.search}&searchString=${pageVO.searchString}&idx=${vo.idx}&pag=${pageVO.pag}&pageSize=${pageVO.pageSize}">${vo.title}</a></c:if> 
		        <c:if test="${vo.claim != 'NO' && sMid != vo.mid && sLevel != 0}"><a href="javascript:alert('현재글은 신고된 글입니다.')">${vo.title}</a></c:if> 
		        <c:if test="${vo.time_diff <= 24}"><img src="${ctp}/images/new.gif" /></c:if>
		      </td>
		      <td><a href="#" onclick='contentView("${vo.content}")' data-toggle="modal" data-target="#myModal">${vo.nickName}</a></td>
		      <td>
		        ${vo.time_diff > 24 ? fn:substring(vo.WDate,0,10) : vo.date_diff == 0 ? fn:substring(vo.WDate,11,19) : fn:substring(vo.WDate,0,19)}
		      </td>
		      <td>${vo.readNum}</td>
		    </tr>
	    </c:if>
	    <c:set var="curScrStartNo" value="${curScrStartNo - 1}" />
    </c:forEach>
    <tr><td colspan="5" class="m-0 p-0"></td></tr>
  </table>
  <br/>
</div>
<!-- 블록페이지 시작 -->
<div class="text-center">
  <ul class="pagination justify-content-center">
	  <c:if test="${pageVO.pag > 1}"><li class="page-item"><a class="page-link text-secondary" href="boardSearchList?level=${level}&search=${pageVO.search}&searchString=${pageVO.searchString}&pageSize=${pageVO.pageSize}&pag=1">첫페이지</a></li></c:if>
	  <c:if test="${pageVO.curBlock > 0}"><li class="page-item"><a class="page-link text-secondary" href="boardSearchList?level=${level}&search=${pageVO.search}&searchString=${pageVO.searchString}&pageSize=${pageVO.pageSize}&pag=${(pageVO.curBlock-1)*pageVO.blockSize + 1}">이전블록</a></li></c:if>
	  <c:forEach var="i" begin="${(pageVO.curBlock*pageVO.blockSize)+1}" end="${(pageVO.curBlock*pageVO.blockSize) + pageVO.blockSize}" varStatus="st">
	    <c:if test="${i <= pageVO.totPage && i == pageVO.pag}"><li class="page-item active"><a class="page-link bg-secondary border-secondary" href="boardSearchList?level=${level}&search=${pageVO.search}&searchString=${pageVO.searchString}&pageSize=${pageVO.pageSize}&pag=${i}">${i}</a></li></c:if>
	    <c:if test="${i <= pageVO.totPage && i != pageVO.pag}"><li class="page-item"><a class="page-link text-secondary" href="boardSearchList?level=${level}&search=${pageVO.search}&searchString=${pageVO.searchString}&pageSize=${pageVO.pageSize}&pag=${i}">${i}</a></li></c:if>
	  </c:forEach>
	  <c:if test="${pageVO.curBlock < pageVO.lastBlock}"><li class="page-item"><a class="page-link text-secondary" href="boardSearchList?level=${level}&search=${pageVO.search}&searchString=${pageVO.searchString}&pageSize=${pageVO.pageSize}&pag=${(pageVO.curBlock+1)*pageVO.blockSize+1}">다음블록</a></li></c:if>
	  <c:if test="${pageVO.pag < pageVO.totPage}"><li class="page-item"><a class="page-link text-secondary" href="boardSearchList?level=${level}&search=${pageVO.search}&searchString=${pageVO.searchString}&pageSize=${pageVO.pageSize}&pag=${pageVO.totPage}">마지막페이지</a></li></c:if>
  </ul>
</div>
<!-- 블록페이지 끝 -->

<!-- The Modal -->
<div class="modal fade" id="myModal">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header">
        <h3 class="modal-title">자기소개</h3>
        <button type="button" class="close" data-dismiss="modal">×</button>
      </div>
      <div class="modal-body">
        <span id="modalContent">${vo.content}</span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>