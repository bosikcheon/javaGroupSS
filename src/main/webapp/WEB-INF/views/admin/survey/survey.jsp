<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>survey.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
  	'use strict';
  	
  	function fCheck(){
  		let detailAnswer= myform.detailAnswer.value;
  		let detailQuestionIdx= myform.detailQuestionIdx.value;
  		myform.detailAnswer.value = detailQuestionIdx+"/ "+detailAnswer.replaceAll(",","_").replaceAll("/","_");
  		
  		for(let i=0;i<myform.detailAnswer.length;i++){
  			detailAnswer= myform.detailAnswer[i].value;
  			myform.detailAnswer[i].value = myform.detailQuestionIdx[i].value+"/ "+ detailAnswer.replaceAll(",","_").replaceAll("/","_");
  			
  		}
  		 myform.submit(); 
  	}
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
<form name="myform" method="post">
  <h3>☞ ${vo.title}</h3>
  <table class="table table-borderless">
    <c:set var="cnt" value="1"/>
	  <c:forEach var="questionVo" items="${questionVos}">
	  	<tr><th>${cnt}. ${questionVo.qcontent}</th></tr>
	  	<c:forEach var="answerVo" items="${answerVos}">
	  		<c:if test="${questionVo.idx==answerVo.QIdx}">
		  		<c:if test="${questionVo.answerSw==1}">
		  			<tr><td><input type="checkbox" name="answer" value="${questionVo.idx}/${answerVo.idx}"/> ${answerVo.acontent}</td></tr>
	  			</c:if>
		  		<c:if test="${questionVo.answerSw==2}">
		  			<tr><td><input type="radio" checked name="radioAnswer${questionVo.idx}" id="answer${questionVo.idx}" value="${answerVo.idx}"/> ${answerVo.acontent}</td></tr>
	  			</c:if>
	  		</c:if>
		  </c:forEach>
			<tr><td class="m-0 p-0"><hr class="m-0 p-0"/></td></tr>
				<c:if test="${questionVo.answerSw==0}">
					<tr>
						<td>
							<input type="hidden" name="detailQuestionIdx" value="${questionVo.idx}" class="form-control"/>
							<input type="text" name="detailAnswer" class="form-control"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<input type="hidden" name="detailQuestionIdx" value="0" class="form-control"/>
						<input type="hidden" name="detailAnswer" value="" class="form-control"/>
					</td> 
				</tr>
			<c:set var="cnt" value="${cnt+1}"/>
	  </c:forEach>
  </table>
  <div class="form-control btn btn-success" onclick="fCheck()">제출하기</div>
  <input type="hidden" name="sIdx" value="{sIdx}"/>
</form>
</div>
<p><br/></p>
</body>
</html>