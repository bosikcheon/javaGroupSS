<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>memberMain.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>회 원 전 용 방</h2>
  <hr/>
  <div>현재 <font color="blue"><b>${sNickName}(<font color="red">${strLevel}</font>)</b></font>님 로그인 중입니다.</div>
  <hr/>
  <c:if test="${sLevel == 1}">
  	<div><font color="red">정회원 등업조건 : <u>총 방문횟수 <b>10회</b>이상</u>, <u>방명록에 글쓰기 <b>2회</b> 이상</u></font></div>
  </c:if>
  <hr/>
  <div>총 방문횟수 : <font color="blue"><b>${mVo.visitCnt}</b></font></div>
  <div>오늘 방문횟수 : <font color="blue"><b>${mVo.todayCnt}</b></font></div>
  <div>최종 방문일 : <font color="blue"><b>${fn:substring(mVo.lastDate,0,19)}</b></font></div>
  <div>총 보유 포인트 : <font color="blue"><b>${mVo.point}</b></font></div>
  <hr/>
  <div>방명록에 작성한글 수 : <font color="blue">총 <a href="#" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#myModal"><b>${fn:length(guestVos)}</b></a> 건</font></div>
  <hr/>
  <div>회원 사진 : <img src="${ctp}/member/${mVo.photo}" width="300px" /></div>
</div>

<!-- The Modal -->

<div class="modal fade" id="myModal">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header">
        <h3 class="modal-title">방명록에 올린글(총 : ${fn:length(guestVos)}건)</h3>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <c:forEach var="vo" items="${guestVos}" varStatus="st">
	        <h5>${st.count}. 방문일자 : ${fn:substring(vo.visitDate,0,19)}</h5>
	        <p>${fn:replace(vo.content, newLine, '<br/>')}</p>
	        <c:if test="${!st.last}"><hr/></c:if>
        </c:forEach>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>