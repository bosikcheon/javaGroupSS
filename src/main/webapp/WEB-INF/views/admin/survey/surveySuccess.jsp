<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>surveySuccess.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<p><br/></p>
<div class="container">
<form name="myform" method="post">
	<br/>
	<div class="container-fluid message">
	  <h1 style="font-family:'ChosunNm'" class="mb-3">
	    <b>☞ 설문조사 완료</b>
	  </h1>
	  <div class=" text-center p-4" >
	  	<img src="${ctp}/images/chicago.jpg" style="width:100%" class="jb-box"><br/>
	  	<img src="${ctp}/images/sanfran.jpg" style="width:100%" class="jb-box">
	  	<h5 class="mt-5">소중한 의견 감사드립니다.<input type="button" value="창닫기" onclick="window.close()" class="btn btn-success btn-sm ml-2"/></h5>
	  	(제출하신 답변은 본 사이트의 발전을 위해 사용됩니다.)
	  </div>
	</div>
	<p><br/></p>
	<%-- <input type="hidden" name="delItems" value="${param.del}"/> --%>
</form>
</div>
<p><br/></p>
</body>
</html>