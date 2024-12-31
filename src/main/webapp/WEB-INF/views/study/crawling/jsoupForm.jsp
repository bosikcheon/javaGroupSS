<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jsoupForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    // 네이버 뉴스 각각 검색처리
    function crawling1() {
    	let url = document.getElementById("url").value;
    	let selector = document.getElementById("selector").value;
    	
    	if(url.trim() == "" || selector.trim() == "") {
    		alert("웹크롤링할 url과 셀렉터를 입력하세요");
    		document.getElementById("url").focus();
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/crawling/jsoup",
    		data : {
    			url : url,
    			selector : selector
    		},
    		success:function(vos) {
    			//console.log("vos: ", vos);
    			if(vos != "") {
    				let str = '';
    				for(let i=0; i<vos.length; i++) {
    					console.log("vos[i]: ", vos[i]);
    					str += vos[i] + "<br/>";
    				}
    				$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 네이버뉴스 vos에 vo로 담아서 처리
    function crawling2() {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/crawling/jsoup2",
    		success:function(vos) {
    			if(vos != "") {
    				let str = '<table class="table table-bordered text-center">';
    				str += '<tr class="table-secondary"><th>번호</th><th>제목</th><th>사진</th><th>언론사</th><th>검증</th></tr>';
    				for(let i=0; i<vos.length; i++) {
    					str += '<tr>';
    					str += '<td>'+(i+1)+'</td>';
    					str += '<td>'+vos[i].item1+'</td>';
    					str += '<td>'+vos[i].item2+'</td>';
    					str += '<td>'+vos[i].item3+'</td>';
    					str += '<td>'+vos[i].item4+'</td>';
    					str += '</tr>';
    				}
    				str += '</table>';
    				$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 다음 엔터테인먼트의 기사(사진) 검색
    function crawling3() {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/crawling/jsoup3",
    		success:function(vos) {
    			if(vos != "") {
    				let str = '<table class="table table-bordered text-center">';
    				str += '<tr class="table-secondary"><th>번호</th><th>제목</th><th>사진</th><th>언론사</th><th>검증</th></tr>';
    				for(let i=0; i<vos.length; i++) {
    					str += '<tr>';
    					str += '<td>'+(i+1)+'</td>';
    					str += '<td>'+vos[i].item1+'</td>';
    					str += '<td>'+vos[i].item2+'</td>';
    					str += '<td>'+vos[i].item3+'</td>';
    					str += '</tr>';
    				}
    				str += '</table>';
    				$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 네이버 검색어로 검색처리
    function crawling4() {
    	let searchString = document.getElementById("searchString").value;
    	let page = document.getElementById("page").value;
    	if(searchString.trim() == "" || page.trim() == "") {
    		alert("검색어와 검색페이지번호를 입력하세요");
    		document.getElementById("searchString").focus();
    		return false;
    	}
    	
    	let search = "https://search.naver.com/search.naver?nso=&page="+page+"&query="+searchString+"&sm=tab_pge&start="+(page*15+1)+"&where=web";
    	let searchSelector = "div.total_wrap";
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/crawling/jsoup4",
    		data : {
    			search : search,
    			searchSelector : searchSelector
    		},
    		success:function(vos) {
    			if(vos != '') {
    				let str = '';
	    			for(let i=0; i<vos.length; i++) {
	    				str += vos[i] + "<br/>";
	    			}
	    			$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 멜론 차트검색
    function crawling5() {
    	$("#spinnerIcon").show();
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/crawling/jsoup5",
    		success:function(vos) {
    			if(vos != "") {
    				let str = '<table class="table table-bordered text-center">';
    				str += '<tr class="table-secondary"><th>순위</th><th>앨범사진</th><th>타이틀곡</th><th>가수</th></tr>';
    				for(let i=0; i<vos.length; i++) {
    					str += '<tr>';
    					str += '<td>'+vos[i].item1+'</td>';
    					str += '<td>'+vos[i].item2+'</td>';
    					str += '<td>'+vos[i].item3+'</td>';
    					str += '<td>'+vos[i].item4+'</td>';
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
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2>JSOUP를 이용한 웹 크롤링</h2>
	<hr/>
	<div><a href="javascript:location.reload()" class="btn btn-warning form-control">다시검색</a></div>
	<hr/>
	<form name="myform">
	  <div class="input-group mb-2">
	    <div class="input-group-text"><span>크롤링할 웹 주소</span></div>
	  	<input type="text" name="url" id="url" value="https://news.naver.com/" class="form-control"/>
	  </div>
	  <div class="input-group mb-2">
	    <div class="input-group-text"><span>크롤링할 셀렉터</span></div>
	  	<!-- <input type="text" name="selector" id="selector" value="strong.cnf_news_title" class="form-control"/> -->
	  	<input type="text" name="selector" id="selector" value="div.cnf_news_thumb" class="form-control"/>
	  </div>
	  <div class="mb-3">
	    <input type="button" value="크롤링1(네이버뉴스검색-각각)" onclick="crawling1()" class="btn btn-success"/>
	    <input type="button" value="크롤링2(네이버뉴스검색-한번에)" onclick="crawling2()" class="btn btn-primary"/>
	    <input type="button" value="크롤링3(다음엔터테인먼트)" onclick="crawling3()" class="btn btn-info"/>
	  </div>
	  <div class="input-group mb-2">
	    <div class="input-group-text"><span>네이버 검색어</span></div>
	    <input type="text" name="searchString" id="searchString" value="오징어게임2" class="form-control"/>
	  </div>
	  <div class="input-group mb-3">
	    <div class="input-group-text"><span>검색 페이지</span></div>
	    <input type="text" name="page" id="page" value="2" class="form-control"/>
	    <input type="button" value="검색" onclick="crawling4()" class="btn btn-secondary"/>
	  </div>
	  <div class="input-group mb-3">
	    <div class="input-group-text"><span>멜론차트순위검색</span></div>
	    <input type="button" value="크롤링5(멜론차트)" onclick="crawling5()" class="btn btn-info"/>
	    <div class="input-group-text" id="spinnerIcon" style="display:none"><span class="spinner-border"></span></div>
	  </div>
	</form>
	<hr/>
	<div id="demo"></div>
  <hr/>
  <h2>크롤링/스크래핑</h2>
  <pre>
  - 크롤링(crawling)은 웹 페이지의 정보를 자동으로 수집하고 저장하는 작업을 말한다.
    크롤링을 하는 소프트웨어를 크롤러(crawler)라고 부르며, 크롤러를 사용해 웹 사이트의 구조와 링크를 따라가며 데이터를 수집한다.
    인터넷에 존재하는 방대한 양의 정보를 사람이 일일히 파악하는 것이 불가능하다는 점에서 유용하며,
    데이터 분석팀, 마케팅팀, 고객 관리팀 등 다양한 산업군에서 새로운 인사이트를 찾기 위해 사용된다.
  - 스크래핑(scraping)은 특정 웹 페이지에서 필요한 정보만 선택적으로 추출하는 작업을 뜻한다.
  </pre>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>