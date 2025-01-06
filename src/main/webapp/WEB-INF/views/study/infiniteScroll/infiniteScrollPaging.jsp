<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<c:forEach var="vo" items="${vos}" varStatus="st">
  <hr/>
  <div>번호 : ${vo.idx}</div>
  <table class="table table-bordered mt-5">
    <tr>
      <th>글쓴이</th>
      <td>${vo.nickName}</td>
      <th>글조회수</th>
      <td>${vo.readNum}</td>
    </tr>
    <tr>
      <th>글쓴날짜</th>
      <td>${fn:substring(vo.WDate,0,19)}</td>
      <th>접속IP</th>
      <td>${vo.hostIp}</td>
    </tr>
    <tr>
      <th>글제목</th>
      <td colspan="3">
        ${vo.title}
        (<a href="javascript:goodCheck3(${vo.idx})" title="좋아요"><font color='blue'>♥</font></a>(${vo.good}))
      </td>
    </tr>
    <tr>
      <th>글내용</th>
      <td colspan="3" style="height:240px;">${fn:replace(vo.content, newLine, '<br/>')}</td>
    </tr>
  </table>
</c:forEach>