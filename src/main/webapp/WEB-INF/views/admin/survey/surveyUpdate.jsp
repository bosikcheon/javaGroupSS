<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>surveyUpdate.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strinc';
    
	  function answerInput(idx){
	    let acontent =$("#answerInput"+idx).val();
	  	$.ajax({
	  		type:"post",
	  		url:"surveyAnswerInputOK",
	  		data:{sIdx:"${vo.idx}",
	  			qIdx:idx,
	  			acontent:acontent},
	  		success: function(){location.reload();},
	  		error: function(){alert("오류!!");}
	  	});
	  }
	  
	  function answerDelete(idx) {
		  $.ajax({
	  		type:"post",
	  		url:"surveyAnswerDeleteOK",
	  		data:{idx:idx},
	  		success: function(){location.reload();},
	  		error: function(){alert("전송오류~~");}
	  	});
	  }
	  
	  function questionDelete(idx){
	  	$.ajax({
	  		type:"post",
	  		url:"surveyQuestionDeleteOK",
	  		data:{idx:idx},
	  		success: function(){location.reload();},
	  		error: function(){alert("답변을 먼저 삭제해주세요.");}
	  	});
	  }
	  
	  function fCheck(){
		  let qcontent = $("#questionInput").val();
		  let answerSw= $("#questionAnswerSw").val();
		  let idx="${vo.idx}";
	  	$.ajax({
	  		type:"post",
	  		url:"surveyAnswerInput",
	  		data:{qcontent : qcontent,
	  			answerSw : answerSw,
	  			sIdx : idx},
	  		success: function(){location.reload();},
	  		error: function(){alert("오류!!");}
	  	});
	  }
	  
	  function questionUpdate(idx){
		  let qcontent = $("#qcontent"+idx).val();
	  	$.ajax({
	  		type:"post",
	  		url:"surveyQuestionUpdateOK",
	  		data:{idx:idx,qcontent:qcontent},
	  		success: function(){location.reload();},
	  		error: function(){alert("오류!!");}
	  	});
	  }
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
<form name="myform" method="post">
  <div>
		<table class="table table-borderless">
			<tr>
				<td><font size="6"><b>설문조사 등록창</b></font> (설문조사에 관한 주제 내용을 기록합니다.)</td>
				<td class="text-right"><input type="checkbox" name="showSw" ${vo.showSw == '1' ? 'checked' : ''} /> 홈화면에 설문지 띄우기<br/>
				  <input type="button" value="리스트로돌아가기" onclick="location.href='surveyList';" class="btn btn-info mt-2 p-1"/>
				</td>
			</tr>
		</table>
		<table class="table table-bordered">
			<tr>
				<th>설문조사명</th>
				<td><input type="text" name="title" value="${vo.title}" class="form-control"/></td>
			</tr>
			<tr>
				<th>기간설정</th>
				<td>시작일시 <input type="date" name="startDate" value="${fn:substring(vo.startDate,0,10)}" class="form-control" style="display:inline;width:33%"/> 
				- 마감일시 <input type="date" name="endDate" value="${fn:substring(vo.endDate,0,10)}" class="form-control" style="display:inline;width:33%"/>
				<input type="submit" class="btn btn-warning"  value="수정"/></td> 
			</tr>
		</table>
		<hr/>
		<table class="table table-borderless">
			<tr class="border-top">
				<td><h3>설문항목 관리</h3></td>
				<td>
					<table class="table table-borderless">
				  	<c:set var="cnt" value="1"/>
					  <c:forEach var="questionVo" items="${questionVos}">
					  	<tr>
					  		<th>
					  		  <div class="input-group bg-body-secondary p-2">
					  			  <div class="input-group-text">${cnt}.</div>
					  			  <input type="text" class="form-control" id="qcontent${questionVo.idx}" value="${questionVo.qcontent}"/>
					  			  <input type="button" class="btn btn-warning" value="현 설문항목수정" onclick="questionUpdate(${questionVo.idx})"/>
					  			  <input type="button" class="btn btn-danger" value="삭제" onclick="questionDelete(${questionVo.idx})"/>
					  			</div>
				  			</th>
				  		</tr>
					  	<c:forEach var="answerVo" items="${answerVos}">
					  		<c:if test="${questionVo.idx==answerVo.QIdx}">
						  		<tr>
						  			<td>
						  				<div class="input-group">
							  				<input type="text"  value="${answerVo.acontent}" class="form-control"/>
						  					<span class="btn btn-outline-warning" style="color:gray" onclick="answerDelete(${answerVo.idx})">보기항목삭제</span>
					  					</div>
						  			</td>
						  		</tr>
					  		</c:if>
					  	</c:forEach>
				  		<c:set var="cnt" value="${cnt+1}"/>
				  		<c:if test="${questionVo.answerSw!=0}">
				  			<tr>
				  				<td>
				  					<div class="input-group">
					  					<input type="text" class="form-control" id="answerInput${questionVo.idx}"/>
					  					<span class="btn btn-outline-info" style="color:gray" onclick="answerInput(${questionVo.idx})">보기항목등록/추가</span>
				  					</div>
				  				</td>
				  			</tr>
				  		</c:if>
				  		<c:if test="${questionVo.answerSw==0}">
				  				<tr><td><hr/><h4>서술형 설문조사 입니다.</h4></td></tr>
			  			</c:if>
					  </c:forEach>
					</table>
					<c:if test="${!empty questionVos}"><hr/></c:if>
					<div class="input-group">
						<select class="form-control me-2" id="questionAnswerSw" name="questionAnswerSw">
							<option value="2">선택형(복수응답 불가)</option>
							<option value="1">선택형(복수응답 가능)</option>
							<option value="0">서술형</option>
						</select>
						<input type="text" id="questionInput" name="questionInput" class="form-control"/>
						<input type="button" class="form-control btn btn-dark"  onclick="fCheck()"  value="설문항목 등록/추가"/>
					</div>
				</td>
			</tr>
		</table>
  </div>
</form>
</div>
<p><br/></p>
</body>
</html>