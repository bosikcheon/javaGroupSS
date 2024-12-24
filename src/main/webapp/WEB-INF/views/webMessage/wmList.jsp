<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>wmList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    setTimeout("location.reload()", 1000*10);
    
    function msgDelete(idx, mFlag) {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/webMessage/msgDelete",
    		data : {
    			idx : idx,
    			mFlag:mFlag
    		},
    		success:function(res) {
    			if(res != "0") {
    				alert("메세지를 휴지통으로 보냈습니다.");
    				location.reload();
    			}
    			else alert("메세지 처리 오류!");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  </script>
</head>
<body>
<br/>
<div class="container">
  <table class="table table-hover">
    <tr class="table-secondary">
      <th>번호</th>
      <th>
        <c:if test="${mSw==1 || mSw==2 || mSw==5}">보낸사람</c:if>
        <c:if test="${mSw==3 || mSw==4}">받은사람</c:if>
      </th>
      <th>제목</th>
      <th>
        <c:if test="${mSw==1 || mSw==2 || mSw==5}">보낸/확인(날짜)</c:if>
        <c:if test="${mSw==3 || mSw==4}">받은날짜</c:if>
      </th>
    </tr>
    <c:set var="curScrStartNo" value="${pageVO.curScrStartNo}"/>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
        <td>${curScrStartNo}</td>
        <td>
          <c:if test="${mSw==1 || mSw==2 || mSw==5}">${vo.sendId}</c:if>
          <c:if test="${mSw==3 || mSw==4}">${vo.receiveId}</c:if>
        </td>
        <td class="text-start">
          <c:if test="${mSw != 4 && mSw != 5}"><a href="webMessage?mSw=6&mFlag=${param.mSw}&idx=${vo.idx}" class="text-dark link-primary link-underline-opacity-0 link-underline-opacity-75-hover">${vo.title}</a></c:if>
          <%-- <c:if test="${mSw == 4}">${vo.title} <a href="javascript:msgDelete(${vo.idx}, ${param.mFlag})" class="badge bg-danger">삭제</a></c:if> --%>
          <c:if test="${mSw == 4 || mSw == 5}">${vo.title}</c:if>
          <c:if test="${vo.receiveSw == 'n' && mFlag != 3 && mFlag != 4}"><img src="${ctp}/images/new.gif" width="25px"/></c:if>
        </td>
        <td>
          <span style="font-size:11px">
            <c:if test="${mSw==1 || mSw==2 || mSw==5}">${fn:substring(vo.sendDate,0,19)}</c:if>
            <c:if test="${mSw==3 || mSw==4}">${fn:substring(vo.receiveDate,0,19)}</c:if>
          </span>
        </td>
      </tr>
      <c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
    </c:forEach>
  </table>
</div>

<!-- 블록 페이지 시작 -->
<div class="text-center">
  <ul class="pagination justify-content-center pagination-sm">
    <c:if test="${pageVO.pag > 1}">
      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/webMessage/webMessage?mSw=${mSw}&mFlag=${param.mFlag}&pag=1">첫페이지</a></li>
    </c:if>
    <c:if test="${pageVO.curBlock > 0}">
      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/webMessage/webMessage?mSw=${mSw}&mFlag=${param.mFlag}&pag=${(pageVO.curBlock-1)*pageVO.blockSize + 1}">이전블록</a></li>
    </c:if>
    <c:forEach var="i" begin="${(pageVO.curBlock)*pageVO.blockSize + 1}" end="${(pageVO.curBlock)*pageVO.blockSize + pageVO.blockSize}" varStatus="st">
      <c:if test="${i <= pageVO.totPage && i == pageVO.pag}">
    		<li class="page-item active"><a class="page-link bg-secondary border-secondary" href="${ctp}/webMessage/webMessage?mSw=${mSw}&mFlag=${param.mFlag}&pag=${i}">${i}</a></li>
    	</c:if>
      <c:if test="${i <= pageVO.totPage && i != pageVO.pag}">
    		<li class="page-item"><a class="page-link text-secondary" href="${ctp}/webMessage/webMessage?mSw=${mSw}&mFlag=${param.mFlag}&pag=${i}">${i}</a></li>
    	</c:if>
    </c:forEach>
    <c:if test="${pageVO.curBlock < pageVO.lastBlock}">
      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/webMessage/webMessage?mSw=${mSw}&mFlag=${param.mFlag}&pag=${(pageVO.curBlock+1)*pageVO.blockSize + 1}">다음블록</a></li>
    </c:if>
    <c:if test="${pageVO.pag < pageVO.totPage}">
      <li class="page-item"><a class="page-link text-secondary" href="${ctp}/webMessage/webMessage?mSw=${mSw}&mFlag=${param.mFlag}&pag=${pageVO.totPage}">마지막페이지</a></li>
    </c:if>
  </ul>
</div>
<!-- 블록 페이지 끝 -->

<p><br/></p>
</body>
</html>