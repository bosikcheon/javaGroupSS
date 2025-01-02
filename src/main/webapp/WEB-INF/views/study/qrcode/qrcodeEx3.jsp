<%@ page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>qrcodeEx3.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    function qrcodeCreate3() {
    	let movieCinema = $("#movieCinema").val();
    	let movieName = $("#movieName").val();
    	let movieDate = $("#movieDate").val();
    	let movieTime = $("#movieTime").val();
    	let movieAdult = $("#movieAdult").val();
    	let movieChild = $("#movieChild").val();
    	
    	if(movieCinema.trim() == "" || movieName.trim() == "" || movieDate.trim() == "" || movieTime.trim() == "" || movieAdult.trim() == "" || movieChild.trim() == "") {
    		alert("영화 예매 정보를 입력해 주세요");
    		$("#movieAdult").focus();
    		return false;
    	}
    	
    	let query = {
    			mid : '${sMid}',
    			name : '${sNickName}',
    			movieCinema   : movieCinema,
    			movieName  : movieName,
    			movieDate  : movieDate,
    			movieTime  : movieTime,
    			movieAdult  : movieAdult,
    			movieChild  : movieChild
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/qrcode/qrcodeCreate3",
    		data : query,
    		success:function(res) {
    			if(res != "") {
    				let str = 'QR 코드명 : ' + res + '<br/>';
    				str += '<img src="${ctp}/qrcode/'+res+'.png" />';
    				$("#demo").html(str);
    			}
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
  <h2>개인정보를 QR코드로 생성하기</h2>
  <form name="myfrom">
    <table class="table table-bordered text-center">
      <tr>
        <th>상영관</th>
        <td>
          <select name="movieCinema" id="movieCinema" class="form-control">
            <option value="">상영관을 선택하세요</option>
            <option>CGV(서울)</option>
            <option>CGV(청주)</option>
            <option>CGV(제주)</option>
            <option>메가박스(서울)</option>
            <option>메가박스(청주)</option>
            <option>메가박스(제주)</option>
            <option>롯대시네마(서울)</option>
            <option>롯대시네마(청주)</option>
            <option>롯대시네마(제주)</option>
          </select>
        </td>
      </tr>
      <tr>
        <th>영화명</th>
        <td>
        	<select name="movieName" id="movieName" class="form-control">
            <option value="">영화명을 선택하세요</option>
            <option>하얼빈</option>
            <option>보고타</option>
            <option>뽀로로극장판</option>
            <option>동화지만청불입니다.</option>
            <option>수퍼소닉3</option>
            <option>시빌워:분열의시대</option>
            <option>페라리</option>
            <option>더폴:디렉터스컷</option>
            <option>소방관</option>
          </select>
        </td>
      </tr>
      <tr>
        <th>상영일자</th>
        <td>
        	<input type="date" name="movieDate" id="movieDate" value="<%=LocalDate.now() %>" required class="form-control"/>
        </td>
      </tr>
      <tr>
        <th>상영시간</th>
        <td>
        	<select name="movieTime" id="movieTime" class="form-control">
            <option value="">상영시간을 선택하세요</option>
            <option>10시00분</option>
            <option>12시30분</option>
            <option>15시00분</option>
            <option>17시30분</option>
            <option>20시00분</option>
            <option>22시30분</option>
          </select>
        </td>
      </tr>
      <tr>
        <th>인원수</th>
        <td>
        	<input type="number" name="movieAdult" id="movieAdult" value="1" min="1" required class="form-control"/><br/>
        	<input type="number" name="movieChild" id="movieChild" value="0" min="0" required class="form-control"/>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="button" value="QR코드생성" onclick="qrcodeCreate3()" class="btn btn-success me-2"/>
          <input type="reset" value="다시입력" class="btn btn-warning me-2"/>
          <input type="button" value="돌아가기" onclick="location.href='qrcodeForm';" class="btn btn-info"/>
        </td>
      </tr>
    </table>
  </form>
  <hr/>
  <div>생성된 QR코드 :<br/>
    <div id="demo"></div>
  </div>
  <hr/>
  <h3>발행한 QR코드를 DB에서 확인하기</h3>
  <div class="input-group">
    <div >아이디</div> 
    <input type="text" name="mid" id="mid" value="${sMid}" />
    <input type="button" value="확인" onclick="" class="btn btn-success"/>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>