<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!-- kakaoMenu.jsp -->
<div>
  <p>
    <a href="${ctp}/study/kakao/kakaoEx1" class="btn btn-success">마커표시/DB저장</a>
    <a href="${ctp}/study/kakao/kakaoEx2" class="btn btn-primary">MyDB에 저장된 지명검색</a>
    <a href="${ctp}/study/kakao/kakaoEx3" class="btn btn-info">KakaoDB에 저장된 키워드검색</a>
    <a href="" class="btn btn-secondary">주변검색</a>
    <a href="" class="btn btn-warning">마커클러스터러</a>
  </p>
</div>