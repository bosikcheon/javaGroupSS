<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-black w3-card">
    <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <a href="http://192.168.50.20:9090/javaGroupS" class="w3-bar-item w3-button w3-padding-large">HOME</a>
    <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Guest</a>
    <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Board</a>
    <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">PDS</a>
    <div class="w3-dropdown-hover w3-hide-small">
      <button class="w3-padding-large w3-button" title="More">Study1 <i class="fa fa-caret-down"></i></button>     
      <div class="w3-dropdown-content w3-bar-block w3-card-4">
        <a href="${ctp}/user/userMain" class="w3-bar-item w3-button">UserList</a>
        <a href="${ctp}/dbtest/dbtestList" class="w3-bar-item w3-button">DB Test</a>
        <a href="#" class="w3-bar-item w3-button">Ajax Test</a>
      </div>
    </div>
    <div class="w3-dropdown-hover w3-hide-small">
      <button class="w3-padding-large w3-button" title="More">Study2 <i class="fa fa-caret-down"></i></button>     
      <div class="w3-dropdown-content w3-bar-block w3-card-4">
        <a href="#" class="w3-bar-item w3-button">Study2-1</a>
        <a href="#" class="w3-bar-item w3-button">Study2-2</a>
        <a href="#" class="w3-bar-item w3-button">Study2-2</a>
      </div>
    </div>
    <div class="w3-dropdown-hover w3-hide-small">
      <button class="w3-padding-large w3-button" title="More">MyPage <i class="fa fa-caret-down"></i></button>     
      <div class="w3-dropdown-content w3-bar-block w3-card-4">
        <a href="#" class="w3-bar-item w3-button">회원리스트</a>
        <a href="#" class="w3-bar-item w3-button">비밀번호변경</a>
        <a href="#" class="w3-bar-item w3-button">회원정보수정</a>
        <a href="#" class="w3-bar-item w3-button">회원탈퇴</a>
        <a href="#" class="w3-bar-item w3-button">관리자메뉴</a>
      </div>
    </div>
    <a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a>
  </div>
</div>

<!-- Navbar on small screens (remove the onclick attribute if you want the navbar to always show on top of the content when clicking on the links) -->
<div id="navDemo" class="w3-bar-block w3-black w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top:46px">
  <a href="#band" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">BAND</a>
  <a href="#tour" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">TOUR</a>
  <a href="#contact" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">CONTACT</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">MERCH</a>
</div>