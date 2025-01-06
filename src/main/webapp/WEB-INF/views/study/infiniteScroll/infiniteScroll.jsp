<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>infiniteScroll.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
		h6 {
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
    
    let lastScroll = 0;
    let curPage = 1;
    
    $(document).scroll(function(){
    	let currentScroll = $(this).scrollTop();
    	let documentHeight = $(document).height();
    	let nowHeight = $(this).scrollTop() + $(window).height();
    	
    	if(currentScroll > lastScroll) {
    		if(documentHeight < (nowHeight + (documentHeight*0.05))) {
    			//console.log("다음페이지 가져오기");
    			curPage++;
    			
    			$.ajax({
  	    		url  : "infiniteScrollPaging",
  	    		type : "post",
  	    		data : {pag : curPage},
  	    		success:function(res) {
  	    			$("#list-wrap").append(res);
  	    		}
  	    	});
    		}
    	}
    	lastScroll = currentScroll;
    });
    
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
    
    // 좋아요 처리(중복 불허)
    function goodCheck3(idx) {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/board/boardGoodCheck3",
    		data : {idx : idx},
    		success:function(res) {
    			if(res != "0") location.reload();
    			else alert("이미 좋아요 버튼을 클릭하셨습니다.");
    		},
    		error : function() {
    			alert('전송오류');
    		}
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">글 내용 보기</h2>
  <div><input type="button" value="돌아가기" onclick="location.href='infiniteScroll'" class="btn btn-warning"/></div>
  <section id="list-wrap">
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
  </section>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
<h6 id="topBtn" class="text-right mr-3"><img src="${ctp}/images/top.gif" title="위로이동"/></h6>
</body>
</html>