<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberIdSearch.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/userTable.css" />
  <script>
    'use strict';
    
    $(function(){
    	$("#certificationBox").hide();
    });
    
    function emailCheck() {
    	$("#certificationBox").show();
    	let spin = "<div class='text-center'><div class='spinner-border text-muted'></div>인증번호를 메일로 발송중입니다. 잠시만 기다려주세요<div class='spinner-border text-muted'></div></div>";
    	$("#certificationBox").html(spin);
    	let email = $("#email").val();
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/member/emailCheck",
    		data : {email : email},
    		success:function(res) {
    			let str = '';
    			if(res != "0") {
    				str += '<div class="input-group">';
    				str += '<span class="input-group-text">인증번호</span>';
    				str += '<input type="text" name="checkKey" id="checkKey" class="form-control"/>';
    				str += '<input type="button" value="확인" onclick="emailCheckOk()" class="btn btn-success"/>';
    				str += '</div>';
    				$("#certificationBox").html(str);
	    			alert("인증번호를 메일로 발송하였습니다.\n확인후 아래 인증번호란에 입력해주세요.");
    			}
    			else {
    				alert("이메일주소를 확인하시고 다시 전송하세요");
    				location.reload();
    			}
    		}
    	});
    }
    
    // 메일로 전송받은 인증키 확인처리하기
    function emailCheckOk() {
    	let email = $("#email").val();
    	let checkKey = $("#checkKey").val();
    	if(checkKey.trim() == "") {
    		alert("전송받은 메일에서 인증받은 인증키를 입력해주세요");
    		$("#checkKey").focus();
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/member/emailCheckOk",
    		data : {
    			email : email,
    			checkKey : checkKey
    		},
    		success:function(res) {
    			if(res == "") alert("인증번호를 확인하고 다시 입력해주세요.");
    			else {
    				let str = '<b>가입된 아이디 입니다.</b><br/>';
    				for(let i=0 ; i<res.length; i++) {
    					str += (i+1) + ': ' + res[i].mid.substring(0,2);
	  					let len = res[i].mid.length;
    					for(let j=2; j<res[i].mid.length-1; j++) {
	    					if(j == 3 && len > 4) str += res[i].mid.substring(3, 4);
	    					else str += "*";
    					}
    					str += res[i].mid.substring(res[i].mid.length-1) + "<br/>";
    				}
    				str += '<a href="memberLogin" class="btn btn-success mt-2">로그인창으로</a>';
    				$("#idCheckBox").html(str);
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
<div class="container text-center">
  <h3>아이디 찾기</h3>
  <br/>
  <p>가입시에 등록하신 이메일 주소를 입력하세요</p>
  <table class="table table-bordered" id="userTable">
    <tr>
      <th class="align-middle">이메일주소</th>
      <td>
        <div class="input-group mt-2 mb-2">
        	<input type="text" name="email" id="email" placeholder="이메일 주소를 입력하세요" autofocus class="form-control"/>
        	<input type="button" value="인증번호전송" onclick="emailCheck()" class="btn btn-primary"/>
        </div>
      </td>
    </tr>
  </table>
  <br/>
  <div id="certificationBox"></div>
  <br/>
  <div id="idCheckBox"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>