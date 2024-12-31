<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    const API_KEY = '7WMGwpEENfXvFnxY1efwZ4263gPHczyuehE7RyufhGeO4SZPOKxDisyWglB%2BjylPIXZJu8Xxs8BCWVbLqr9PdA%3D%3D';
    
    async function getCrimeData() {
    	let year = $("#year").val();
    	
    	let apiYear = '';
    	
    	if(year == 2015) apiYear = "/15084592/v1/uddi:fbbfd36d-d528-4c8e-aa9b-d5cdbdeec669";
    	else if(year == 2016) apiYear = "/15084592/v1/uddi:21ec6fa1-a033-413b-b049-8433f5b446ff";
    	else if(year == 2017) apiYear = "/15084592/v1/uddi:67117bd9-5ee1-4e07-ae4a-bfe0861ee116";
    	else if(year == 2018) apiYear = "/15084592/v1/uddi:2d687e27-b5c3-4bdb-9b77-c644dcafcbc7";
    	else if(year == 2019) apiYear = "/15084592/v1/uddi:b6cc7731-181b-48e1-9a6c-ae81388e46b0";
    	else if(year == 2020) apiYear = "/15084592/v1/uddi:fdde1218-987c-49ba-9326-8e3ba276141e";
    	else if(year == 2021) apiYear = "/15084592/v1/uddi:943e757d-462b-4b3a-ab9f-9a8553637ca2";
    	else if(year == 2022) apiYear = "/15084592/v1/uddi:5e08264d-acb3-4842-b494-b08f318aa14c";
    	else if(year == 2023) apiYear = "/15084592/v1/uddi:18a0493e-32bb-433d-b291-aedadffe1027";
    	
    	let url = "https://api.odcloud.kr/api";
    	url += apiYear;
    	url += "?serviceKey=" + API_KEY;
    	url += "&page=1&perPage=300";
    	
    	let response = await fetch(url);
    	//console.log("response :", response);
    	
    	let res = await response.json();
    	console.log("res :", res);
    	
    	/*
    	let str = res.data.map((item, i) => 
    	  (i+1) + "."
    	  + "발생년도 : " + item.발생년도 +"년"
    	  + "경찰서명 : " + item.경찰서 +"년"
    	  + "강도 : " + item.강도 +"년"
    	  + "절도 : " + item.절도 +"년"
    	  + "살인 : " + item.살인 +"년"
    	  + "폭력 : " + item.폭력 +"년"
    	  + "<br/>"
    	);
    	*/
    	
    	let str ='<table class="table table-bordered table-hover text-center"';
    	str += '<tr class="table-secondary">';
    	str += '<th>번호</th><th>발생년도</th><th>경찰서명</th><th>강도</th><th>절도</th><th>살인</th><th>폭력</th>';
    	str += '</tr>';
    	str += res.data.map((item, i) => 
    		'<tr><td>'+ (i+1) + '</td>'
    		+ '<td>'+(item.발생년도 || item.발생연도)+'</td>'
    		+ '<td>'+item.경찰서+'</td>'
    		+ '<td>'+item.강도+'</td>'
    		+ '<td>'+item.절도+'</td>'
    		+ '<td>'+item.살인+'</td>'
    		+ '<td>'+item.폭력+'</td></tr>'
    	).join('');
    	str += '</table>';
    	$("#demo").html(str);
    }
    
    
    async function saveCrimeData() {
    	let year = $("#year").val();
    	
    	let apiYear = '';
    	
    	if(year == 2015) apiYear = "/15084592/v1/uddi:fbbfd36d-d528-4c8e-aa9b-d5cdbdeec669";
    	else if(year == 2016) apiYear = "/15084592/v1/uddi:21ec6fa1-a033-413b-b049-8433f5b446ff";
    	else if(year == 2017) apiYear = "/15084592/v1/uddi:67117bd9-5ee1-4e07-ae4a-bfe0861ee116";
    	else if(year == 2018) apiYear = "/15084592/v1/uddi:2d687e27-b5c3-4bdb-9b77-c644dcafcbc7";
    	else if(year == 2019) apiYear = "/15084592/v1/uddi:b6cc7731-181b-48e1-9a6c-ae81388e46b0";
    	else if(year == 2020) apiYear = "/15084592/v1/uddi:fdde1218-987c-49ba-9326-8e3ba276141e";
    	else if(year == 2021) apiYear = "/15084592/v1/uddi:943e757d-462b-4b3a-ab9f-9a8553637ca2";
    	else if(year == 2022) apiYear = "/15084592/v1/uddi:5e08264d-acb3-4842-b494-b08f318aa14c";
    	else if(year == 2023) apiYear = "/15084592/v1/uddi:18a0493e-32bb-433d-b291-aedadffe1027";
    	
    	let url = "https://api.odcloud.kr/api";
    	url += apiYear;
    	url += "?serviceKey=" + API_KEY;
    	url += "&page=1&perPage=300";
    	
    	let response = await fetch(url);
    	//console.log("response :", response);
    	
    	let res = await response.json();
    	console.log("res :", res);
    	
    	/*
    	let str = res.data.map((item, i) => 
    	  (i+1) + "."
    	  + "발생년도 : " + item.발생년도 +"년"
    	  + "경찰서명 : " + item.경찰서 +"년"
    	  + "강도 : " + item.강도 +"년"
    	  + "절도 : " + item.절도 +"년"
    	  + "살인 : " + item.살인 +"년"
    	  + "폭력 : " + item.폭력 +"년"
    	  + "<br/>"
    	);
    	*/
    	
    	let str ='<table class="table table-bordered table-hover text-center"';
    	str += '<tr class="table-secondary">';
    	str += '<th>번호</th><th>발생년도</th><th>경찰서명</th><th>강도</th><th>절도</th><th>살인</th><th>폭력</th>';
    	str += '</tr>';
    	str += res.data.map((item, i) => 
    		'<tr><td>'+ (i+1) + '</td>'
    		+ '<td>'+(item.발생년도 || item.발생연도)+'</td>'
    		+ '<td>'+item.경찰서+'</td>'
    		+ '<td>'+item.강도+'</td>'
    		+ '<td>'+item.절도+'</td>'
    		+ '<td>'+item.살인+'</td>'
    		+ '<td>'+item.폭력+'</td></tr>'
    	).join('');
    	str += '</table>';
    	$("#demo").html(str);
    	
    	// 화면에 출력된 자료들을 모두 DB에 저장시킨다.
    	let query = "";
    	for(let i=0; i<res.data.length; i++) {
    		if(res.data[i].경찰서 != null) {
    			query = {
    					year : year,
    					police : res.data[i].경찰서,
    					murder : res.data[i].살인,
    					robbery : res.data[i].강도,
    					theft 	: res.data[i].절도,
    					violence: res.data[i].폭력
    			}
    		}
    		
    		$.ajax({
    			type : "post",
    			url  : "${ctp}/study/restapi/saveCrimeData",
    			data : query,
    			error: function() {
    				alert("전송오류!");
    				breaK;
    			}
    		});
    	}
    	alert(year + "년도 자료가 DB에 저장되었습니다.");
    }
    
    // DB에 저장된 자료 삭제처리하기
    function deleteCrimeData() {
    	let year = $("#year").val();
    	let ans = confirm(year + "년도 자료를 삭제하시겠습니까?");
    	if(!ans) alert("삭제취소");
    	
    	$.ajax({
    		type  : "post",
    		url   : "${ctp}/study/restapi/deleteCrimeData",
    		data  : {year : year},
    		success:function(res) {
    			if(res != "0") alert(year + '년도 자료가 삭제되었습니다.');
    			else alert(year + '년도 자료가 없습니다. 확인후 다시 처리하세요.');
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	}); 
    }
    
    // DB에 저장된 자료를 선택년도별 출력처리
    function listCrimeData() {
    	let year = $("#year").val();
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/restapi/listCrimeData",
    		data : {year : year},
    		success:function(vos) {
    			if(vos == "") {
    				alert(year + "년도 검색된 자료가 없습니다.");
    				return false;
    			}
    			console.log(vos);
    			
    			let str = '';
    			str += '<table class="table table-bordered table-hover text-center">';
  	    	str += '<th>번호</th><th>발생년도</th><th>경찰서명</th><th>강도</th><th>절도</th><th>살인</th><th>폭력</th>';
  	    	str += '</tr>';
					for(let i=0; i<vos.length; i++) {
	    			str += '<tr>';
	    			str += '<td>'+(i+1)+'</td>';
	    			str += '<td>'+vos[i].year+'</td>';
	    			str += '<td>'+vos[i].police+'</td>';
	    			str += '<td>'+vos[i].robbery+'</td>';
	    			str += '<td>'+vos[i].theft+'</td>';
	    			str += '<td>'+vos[i].murder+'</td>';
	    			str += '<td>'+vos[i].violence+'</td>';
	    			str += '</tr>';
					}
    			str += '</table>';
    			$("#demo").html(str);
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 경찰서별(지역명별) DB자료 출력
    function yearPoliceCheck() {
    	//let year = $("#year").val();
    	//let police = $("#police").val();
    	myform.action = "${ctp}/study/restapi/yearPoliceCheck";
    	myform.submit();
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>경찰청 강력범죄 발생 현황 리스트</h2>
  <hr/>
  <form name="myform" method="post">
    <div class="mb-3">
      <select name="year" id="year">
        <c:forEach var="i" begin="2015" end="2023" varStatus="st">
          <option value="${i}" ${year == i ? 'selected' : ''}>${i}년도</option>
        </c:forEach>
      </select>
      <input type="button" value="공공데이터조회" onclick="getCrimeData()" class="btn btn-success me-2"/>
      <input type="button" value="DB저장" onclick="saveCrimeData()" class="btn btn-primary me-2"/>
      <input type="button" value="DB출력" onclick="listCrimeData()" class="btn btn-info me-2"/>
      <input type="button" value="DB삭제" onclick="deleteCrimeData()" class="btn btn-danger me-2"/>
    </div>
    <div class="input-group">
      <div class="input-group-text">경찰서지역명</div>
      <!-- <select name="police" id="police" onchange="policeCheck()"> -->
      <select name="police" id="police">
        <option ${police == '서울' ? 'selected' : ''}>서울</option>
        <option ${police == '부산' ? 'selected' : ''}>부산</option>
        <option ${police == '대구' ? 'selected' : ''}>대구</option>
        <option ${police == '인천' ? 'selected' : ''}>인천</option>
        <option ${police == '광주' ? 'selected' : ''}>광주</option>
        <option ${police == '대전' ? 'selected' : ''}>대전</option>
        <option ${police == '울산' ? 'selected' : ''}>울산</option>
        <option ${police == '세종' ? 'selected' : ''}>세종</option>
        <option ${police == '경기' ? 'selected' : ''}>경기</option>
        <option ${police == '강원' ? 'selected' : ''}>강원</option>
        <option ${police == '충북' ? 'selected' : ''}>충북</option>
        <option ${police == '충남' ? 'selected' : ''}>충남</option>
        <option ${police == '전북' ? 'selected' : ''}>전북</option>
        <option ${police == '전남' ? 'selected' : ''}>전남</option>
        <option ${police == '경북' ? 'selected' : ''}>경북</option>
        <option ${police == '경남' ? 'selected' : ''}>경남</option>
        <option ${police == '제주' ? 'selected' : ''}>제주</option>
      </select>
      <input type="button" value="년도/지역(경찰서)출력" onclick="yearPoliceCheck()" class="btn btn-secondary ms-3"/>
      <input type="button" value="다시검색" onclick="location.href='restapiTest4';" class="btn btn-warning ms-3"/>
    </div>
  </form>
  <hr/>
  <div id="demo"></div>
  <hr/>
  <!-- 년도별/지역별 범죄 발생건수 -->
  <%-- <c:if test="${empty vos}">
  	<script>if('${year}' == null || '${year}' == '') alert("${year}년도 자료가 없습니다.");</script>
  </c:if> --%>
  <c:if test="${!empty analyzeVO}">
    <h3><font color="red"><b>${year}</b></font>년 <font color="blue"><b>${police}</b></font>지역 범죄자 통계</h3>
    <table class="table table-hover">
      <tr class="table-secondary">
        <th>구분</th><th>발생년도</th><th>살인</th><th>강도</th><th>절도</th><th>폭력</th>
      </tr>
      <tr>
        <td>총계</td>
        <td>${year}</td>
        <td>${analyzeVO.totMurder}</td>
        <td>${analyzeVO.totRobbery}</td>
        <td>${analyzeVO.totTheft}</td>
        <td>${analyzeVO.totViolence}</td>
      </tr>
      <tr>
        <td>평균</td>
        <td>${year}</td>
        <td>${analyzeVO.avgMurder}</td>
        <td>${analyzeVO.avgRobbery}</td>
        <td>${analyzeVO.avgTheft}</td>
        <td>${analyzeVO.avgViolence}</td>
      </tr>
    </table>
    <hr/>
    <div id="piechart" style="width: 900px; height: 500px;"></div>
  </c:if>
</div>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
  google.charts.load('current', {'packages':['corechart']});
  google.charts.setOnLoadCallback(drawChart);

  function drawChart() {

    var data = google.visualization.arrayToDataTable([
        ['Task', '범죄 유형'],
        ['살인', ${analyzeVO.totMurder}],
        ['강도', ${analyzeVO.totRobbery}],
        ['절도', ${analyzeVO.totTheft}],
        ['폭력', ${analyzeVO.totViolence}]
    ]);

    var options = {
      title: '${year}년 ${police} 지역 범죄자 통계'
    };

    var chart = new google.visualization.PieChart(document.getElementById('piechart'));

    chart.draw(data, options);
  }
</script>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>