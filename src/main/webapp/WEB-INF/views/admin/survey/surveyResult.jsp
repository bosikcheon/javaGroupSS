<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>surveyResult.jsp</title>
  <script src="${ctp}/ckeditor/ckeditor.js"></script>
 	<jsp:include page="/WEB-INF/views/include/bs5.jsp"></jsp:include>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
    // Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages':['corechart']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);

    // Callback that creates and populates a data table,
    // instantiates the pie chart, passes in the data and
    // draws it.
    var data="";
    var options=""; 
    var chart="";
    function drawChart() {
  	    <c:forEach var="questionVo" items="${questionVos}">
  	    	<c:if test="${questionVo.answerSw!=0}">
					
		        // Create the data table.
		        data = new google.visualization.DataTable();
		        data.addColumn('string', 'Topping');
		        data.addColumn('number', 'Slices');
		        data.addRows([
		        	<c:forEach var="answerVo" items="${answerVos}">
		        		<c:if test="${answerVo.QIdx==questionVo.idx}">
			          ['${answerVo.acontent}', ${answerVo.realAnswerCnt==null?0:answerVo.realAnswerCnt}],
			          </c:if>
		          </c:forEach>
		        ]);
		
		        // Set chart options
		        options = {'title':'',
		                       'width':300,
		                       'height':300};
		
		        // Instantiate and draw our chart, passing in some options.
		        chart = new google.visualization.PieChart(document.getElementById('chart_pie${questionVo.idx}'));
		        chart.draw(data, options);
		        
		        
		        // Create the data table.
		        data = new google.visualization.DataTable();
		        data.addColumn('string', 'Topping');
		        data.addColumn('number', '인원');
		        data.addRows([
		        	<c:forEach var="answerVo" items="${answerVos}">
		        		<c:if test="${answerVo.QIdx==questionVo.idx}">
			          ['${answerVo.acontent}', ${answerVo.realAnswerCnt==null?0:answerVo.realAnswerCnt}],
			          </c:if>
		          </c:forEach>
		        ]);
		
		        // Set chart options
		        options = {'title':'',
		                       'width':300,
		                       'height':300};
		
		        // Instantiate and draw our chart, passing in some options.
		        chart = new google.visualization.BarChart(document.getElementById('chart_bar${questionVo.idx}'));
		        chart.draw(data, options);
        </c:if>
      </c:forEach>
    }
    
    $(function(){
  	  $("#part").change(function(){
  		 let part= $("#part").val();
  		 
  		 $.ajax({
  			 type:"post",
  			 url:"surveyGetSmallpart",
  			 data:{idx:"${param.idx}",part:part},
  			 success:function(res){
  				 if(res == "0") alert("아직 투표하신분이 없습니다.");
  				 else {
	  				 res = res.split("_");
	  				 let str="";
	  				 str +='<select name="smallPart" id="smallPart" class="form-control" style="display:inline; width:40%">';
	  				 for(let i=0;i<res.length;i++){
	  					 str+="<option>"+res[i]+"</option>";
	  				 }
	  				 str +="";
	  				 str +="";
	  				 str +="";
	  				 str +="";
	  				 str +="</select>";
	  				 $("#box").html(str);
  				 }
  			 },
  			 error:function(){
  			   alert("오류");
  			 }
  		 });
  	  });
  	  
    });
    
    function fCheck(){
  	  let part= $("#part").val();	// part는 '성별/연령대/거주지'
  	  let smallPart= $("#smallPart").val();	// smallPart는 part에서 관련된자료 목록...
  	  location.href="surveyResult?idx=${vo.idx}&part="+part+"&smallPart="+smallPart;
    }
  </script>
</head>
<body class="bg-light">
<div class="container-fluid p-3">
	<div class="container-fluid p-5 border shadow-sm" style="background-color:#fff">
	<h2>설문조사 통계</h2>
	<hr/>
	<div class="row">
		<div class="col-md-5">
		  <table class="table table-borderless">
		  	<tr>
		  		<td><h2 class="text-center">${vo.title}</h2></td>
		  	</tr>
		  	<c:set var="cnt" value="1"/>
			  <c:forEach var="questionVo" items="${questionVos}">
			  	<tr><th>${cnt}. ${questionVo.qcontent}</th></tr>
			  	<c:forEach var="answerVo" items="${answerVos}">
			  		<c:if test="${questionVo.idx==answerVo.QIdx}">
				  		<tr><td>- ${answerVo.acontent}</td></tr>
			  		</c:if>
			  	</c:forEach>
		  		<c:if test="${questionVo.answerSw==0}">
		  			<tr>
		  				<td>
		  					<input type="hidden" name="detailQuestionIdx" value="${questionVo.idx}" class="form-control"/>
		  					<input type="text" name="detailAnswer" class="form-control"/>
		  				</td>
		  			</tr>
		  		</c:if> 
		  		<c:set var="cnt" value="${cnt+1}"/>
			  </c:forEach>
		  </table>
		</div>
		<div class="col-md-7  bg-light p-4">
			<select name="part" id="part" class="form-control" style="display:inline; width:40%">
				<option value="">구분</option>
				<option value="gender"  ${part=='gender'?'selected':''}>성별</option>
				<option value="age" ${part=='age'?'selected':''}>연령대</option>
				<option value="address" ${part=='address'?'selected':''}>거주지</option>
			</select>
			<c:if test="${empty smalls}">
				<span id="box">
					<select name="smallPart" id="smallPart" class="form-control" style="display:inline; width:40%">
						<option value="">구분</option>
					</select>
				</span>
			</c:if>
			<c:if test="${!empty smalls}">
				<span id="box">
					<select name="smallPart" id="smallPart" class="form-control" style="display:inline; width:40%">
						<c:forEach var="small" items="${smalls}">
							<option ${small==smallPart ? 'selected' : ''} >${small}</option>
						</c:forEach>
					</select>
				</span>
			</c:if>
			<input type="button" value="조회" onclick="fCheck()" class="btn btn-success"/>
			<input type="button" value="돌아가기" onclick="location.href='surveyList';" class="btn btn-primary"/>
		  <table class="table table-borderless ">
		  	<c:set var="cnt" value="1"/>
			  <c:forEach var="questionVo" items="${questionVos}">
			  	<tr><th>${cnt}. ${questionVo.qcontent}</th></tr>
			  	<c:if test="${questionVo.answerSw != 0}">	<!-- 질문항목이 0(서술형)이 아닌것은 차트가 보이도록 처리한다. -->
			  		<tr><td>
			  			<div class="row">
			  				<div class="col"><div id="chart_pie${questionVo.idx}"></div></div>
			  				<div class="col"><div id="chart_bar${questionVo.idx}"></div></div>
			  			</div>
			  		</td></tr>
			  	</c:if>
			  	<c:if test="${questionVo.answerSw==0}">	<!-- 질문항목이 0(서술형)인것은 여러행이 보이도록 처리한다. -->
			  		<tr>
			  			<td>
				  			<div class="border bg-white" style="width:600px;height:300px;overflow:auto">
			  				<table class="table table-borderless ">
						  		<c:forEach var="realAnswerVo" items="${realAnswerVos}">
						  			<c:if test="${questionVo.idx==realAnswerVo.QIdx}">
					  					<tr>
					  						<td>
					  							<c:if test="${realAnswerVo.detailAnswer != ''}">
								  					${realAnswerVo.detailAnswer}<br/>
								  				</c:if>
									  		</td>
									  	</tr>
						  			</c:if>
						  		</c:forEach>
					  		</table>
					  		</div>
			  			</td>
			  		</tr>
			  	</c:if>
		  		<c:set var="cnt" value="${cnt+1}"/>
			  </c:forEach>
		  </table>
		  </div>
		  </div>
	  </div>
	</div>
<p><br/><br/></p>
</body>
</html>