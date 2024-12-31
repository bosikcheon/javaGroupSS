<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>seleniumForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
    h6{
      position: fixed;
      right: 1rem;
      bottom: -50px;
      transition: 0.7s ease;
    }
    .on {
      opacity: 0.8;
      cursor: pointer;
      bottom: 0;
    }
  </style>
  <script>
    'use strict';
    
		function crawling1() {
			$("#spinnerIcon").show();
			
			$.ajax({
				type : "post",
    		url  : "${ctp}/study/crawling/selenium",
				success:function(vos) {
    			if(vos != "") {
    				let str = '<table class="table table-bordered text-center">';
    				str += '<tr class="table-secondary"><th>번호</th><th>영화제목</th><th>포스터</th><th>예매율</th></tr>';
    				for(let i=0; i<vos.length; i++) {
    					str += '<tr>';
    					str += '<td>'+(i+1)+'</td>';
    					str += '<td>'+vos[i].title+'</td>';
    					str += '<td>'+vos[i].image+'</td>';
    					str += '<td>'+vos[i].percent+'</td>';
    					str += '</tr>';
    				}
    				str += '</table>';
    				$("#demo").html(str);
    				$("#spinnerIcon").hide();
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
			});
		}
		
    // 화살표클릭시 화면 상단으로 부드럽게 이동하기
    $(window).scroll(function(){
    	if($(this).scrollTop() > 100) {
    		$("#topBtn").addClass("on");
    	}
    	else {
    		$("#topBtn").removeClass("on");
    	}
    	
    	$("#topBtn").click(function(){
    		window.scrollTo({top:0, behavior: "smooth"});
    	});
    });
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2>SELENIUM 를 이용한 웹 크롤링</h2>
	<hr/>
	<div><a href="javascript:location.reload()" class="btn btn-warning form-control">다시검색</a></div>
	<hr/>
	<form name="myform">
	  <div class="input-group mb-2">
	    <div class="input-group-text"><span>CGV 상영관 무비차트</span></div>
	  	<!-- <input type="text" name="url" id="url" value="http://www.cgv.co.kr/movies/" class="form-control"/> -->
	    <div class="input-group-text" id="spinnerIcon" style="display:none"><span class="spinner-border"></span></div>
	    <input type="button" value="크롤링1(CGV상영관)" onclick="crawling1()" class="btn btn-success"/>
	  </div>
	</form>
	<hr/>
	<div id="demo"></div>
	
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
<!-- 위로가기 버튼 -->
<h6 id="topBtn" class="text-right"><img src="${ctp}/images/top.gif" title="위로이동"/></h6>
</body>
</html>