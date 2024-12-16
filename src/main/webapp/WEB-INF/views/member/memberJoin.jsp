<%@ page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String today = LocalDate.now().toString();
  pageContext.setAttribute("today", today);
%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>memberJoin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/js/woo.js"></script>
  <link rel="stylesheet" type="text/css" href="${ctp}/css/userTable.css" />
  <style>
    th {
      text-align: center;
      background-color: #eee;
    }
  </style>
  <script>
    'use strict';
    
    let idCheckSw = 0;
    let nickCheckSw = 0;
    
  	// 정규식을 이용한 유효성검사처리.....
  	let regMid = /^[a-zA-Z0-9_]{4,20}$/;	// 아이디는 4~20의 영문 대/소문자와 숫자와 밑줄 가능
    let regNickName = /^[가-힣0-9_]{2,20}$/;			// 닉네임은 한글, 숫자, 밑줄만 2~20자 가능
    let regName = /^[가-힣a-zA-Z0-9]{2,20}$/;				// 이름은 한글/영문/숫자 2~20자 가능
    
    $(function(){
			$(".memberInput").hide();    	
    });
    
    
    function fCheck() {
    	let mid = myform.mid.value;
    	let pwd = myform.pwd.value;
    	let nickName = myform.nickName.value;
    	let name = myform.name.value;
    	
    	let tel2 = myform.tel2.value.trim();
    	let tel3 = myform.tel3.value.trim();
    	if(tel2 == "") tel2 = " ";
    	if(tel3 == "") tel3 = " ";
    	
    	let tel = myform.tel1.value + "-" + tel2 + "-" + tel3;
    	let email = myform.email1.value + "@" + myform.email2.value;
    	let address = myform.postcode.value+" /"+myform.address.value+" /"+myform.detailAddress.value+" /"+myform.extraAddress.value+" ";
    	
    	if(!regMid.test(mid)) {
    		alert("아이디는 4~20자리의 영문 소/대문자와 숫자, 언더바(_)만 사용가능합니다.");
    		myform.mid.focus();
    		return false;
    	}
    	else if(pwd.length < 4 || pwd.length > 20) {
        alert("비밀번호는 4~20 자리로 작성해주세요.");
        myform.pwd.focus();
        return false;
      }
      else if(!regNickName.test(nickName)) {
        alert("닉네임은 2자리 이상 한글만 사용가능합니다.");
        myform.nickName.focus();
        return false;
      }
      else if(!regName.test(name)) {
        alert("성명은 2자리 이상 한글과 영문대소문자만 사용가능합니다.");
        myform.name.focus();
        return false;
      }
			// 이메일 주소형식체크
			
			// 전화번호 형식 체크
    	
    	myform.tel.value = tel;
    	myform.email.value = email;
    	myform.address2.value = address;
    	
		  // 아이디/닉네임 중복버튼 눌렀는지 체크
    	if(idCheckSw == 0) {
    		alert("아이디 중복체크버튼을 눌러주세요");
    		document.getElementById("midBtn").focus();
    		return false;
    	}
    	else if(nickCheckSw == 0) {
    		alert("닉네임 중복체크버튼을 눌러주세요");
    		document.getElementById("nickNameBtn").focus();
    		return false;
    	}
    	
    	myform.mid.disabled = false;
    	myform.nickName.disabled = false;
  		myform.email.value = email;
  		myform.tel.value = tel;
  		myform.address.value = address;
  		//myform.submit();
    	
    }
    
    // 아이디 중복체크
    function idCheck() {
    	let mid = myform.mid.value;
    	
    	if(!regMid.test(mid)) {
    		alert("아이디는 4~20자리의 영문 소/대문자와 숫자, 언더바(_)만 사용가능합니다.");
    		myform.mid.focus();
    		return false;
    	}
    	else {
    		$.ajax({
    			type : "post",
    			url  : "${ctp}/member/memberIdCheck",
    			data : {mid : mid},
    			success:function(res) {
    				if(res != "0") alert("이미 사용중인 아이디 입니다.\n다른 아이디를 사용하세요.");
    				else {
    					alert("사용 가능한 아이디 입니다.");
			    		idCheckSw = 1;
			    		myform.mid.disabled = true;
    				}
    			},
    			error : function() {
    				alert("전송오류!");
    			}
    		});
    	}
    }
    
    // 닉네임 중복체크
    function nickNameCheck() {
    	let nickName = myform.nickName.value;
    	if(!regNickName.test(nickName)) {
        alert("닉네임은 2자리 이상 한글만 사용가능합니다.");
        myform.nickName.focus();
        return false;
      }
    	else {
    		$.ajax({
    			type : "get",
    			url  : "${ctp}/member/memberNickNameCheck",
    			data : {nickName : nickName},
    			success:function(res) {
    				if(res != "0") alert("이미 사용중인 닉네임 입니다.\n다른 닉네임을 사용하세요.");
    				else {
    					alert("사용 가능한 닉네임 입니다.");
    					nickCheckSw = 1;
			    		myform.nickName.disabled = true;
    				}
    			},
    			error : function() {
    				alert("전송오류!");
    			}
    		});
    	}
    }
    
    // 이메일 인증체크
    function emailCheck() {
    	// 필수입력란의 체크를 모두 마친후 인증번호를 메일로 발송한다.
    	let email1 = $("#email1").val();
    	if(email1.trim() == "") {
    		alert("이메일을 입력하세요");
    		$("#email1").focus();
    		return false;
    	}
    	let email = email1+"@"+$("#email2").val();
    	let spin = "<div class='text-center'><div class='spinner-border text-muted'></div> 메일 발송중입니다. 잠시만 기다려주세요 <div class='spinner-border text-muted'></div></div>";
    	$("#demoSpin").html(spin);
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/member/memberEmailCheck",
    		data : {email : email},
    		success:function(res) {
    			if(res != 0) {
    				alert("인증번호가 발송되었습니다.\n메일 확인후 인증번호를 입력해주세요");
    				let str = '<div class="input-group">';
    				str += '<input type="text" name="checkKey" id="checkKey" class="form-control"/>';
    				str += '<input type="button" value="인증번호확인" onclick="emailCheckOk()" class="btn btn-info"/>';
    				str += '</div>';
    				$("#demoSpin").html(str);
    			}
    			else alert("인증번호받기 버튼을 다시 눌러주세요");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 메일로 전송받은 인증키에대한 인증확인처리
    function emailCheckOk() {
    	let checkKey = $("#checkKey").val();
    	if(checkKey.trim() == "") {
    		alert("전송받은 메일에서 인증받은 인증키를 입력해주세요");
    		$("#checkKey").focus();
    		return false;
    	}
    	//$(".memberInput").show();
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/member/memberEmailCheckOk",
    		data : {checkKey : checkKey},
    		success:function(res) {
    			if(res != "0") {
		    		alert("인증 확인되었습니다.\n계속해서 추가 인적사항을 기입해 주세요.");
		    		// 추가로 입력할부분을 show() 시켜준다.
		    		$(".memberInput").show();
		    		$("#addContent").show();
		    	}
    			else alert("인증실패~\n메일주소를 확인하시고 다시 인증메일을 전송해 주세요.");
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
  <form name="myform" method="post" onsubmit="return fCheck()">
    <h3 class="text-center">회 원 가 입</h3>
    <table class="table table-bordered" id="userTable">
      <tr>
        <th class="text-center"><label for="mid" class="form-label">아이디</label></th>
        <td>
          <div class="input-group">
	          <input type="text" name="mid" id="mid" placeholder="아이디를 입력하세요" class="form-control me-1" autofocus required />
          	<input type="button" value="아이디 중복체크" onclick="idCheck()" id="midBtn" class="btn btn-secondary" />
          </div>
        </td>
      </tr>
      <tr>
        <th class="text-center"><label for="pwd" class="form-label">비밀번호</label></th>
        <td><input type="password" name="pwd" id="pwd" placeholder="비밀번호를 입력하세요" class="form-control" required /></td>
      </tr>
      <tr>
        <th class="text-center"><label for="name" class="form-label">성명</label></th>
        <td><input type="text" name="name" id="name" placeholder="성명을 입력하세요" class="form-control" required /></td>
      </tr>
      <tr>
        <th class="text-center"><label for="nickName" class="form-label">닉네임</label></th>
        <td>
          <div class="input-group">
	          <input type="text" name="nickName" id="nickName" placeholder="닉네임을 입력하세요" class="form-control me-1" required />
          	<input type="button" value="닉네임 중복체크" onclick="nickNameCheck()" id="nickNameBtn" class="btn btn-secondary" />
          </div>
        </td>
      </tr>
      <tr>
        <th class="text-center">성별</th>
        <td >
          <input type="radio" name="gender" id="male" value="남자" checked class="ms-1 me-1" /><label for="male" class="form-label me-4">남자</label>
          <input type="radio" name="gender" id="female" value="여자" class="me-1" /><label for="female" class="form-label">여자</label>
        </td>
      </tr>
      <tr>
        <th class="text-center"><label for="birthday" class="form-label">생일</label></th>
        <td><input type="date" name="birthday" id="birthday" value="${today}" class="form-control"></td>
      </tr>
      <tr class="mb-2">
        <th class="text-center">이메일</th>
        <td>
          <div class="input-group">
	          <input type="text" name="email1" id="email1" placeholder="이메일을 입력하세요" class="form-control" required />
	          <span class="input-group-text">@</span>
	          <select id="email2" name="email2" class="form-control me-2">
	            <option value="naver.com">naver.com</option>
	            <option value="hanmail.net">hanmail.net</option>
	            <option value="daum.net">daum.net</option>
	            <option value="gmail.com">gmail.com</option>
	          </select>
	          <input type="button" value="인증번호받기" onclick="emailCheck()" class="btn btn-success" />
          </div>
          <br/>
          <div id="demoSpin"></div>
        </td>
    	</tr>
    </table>
  	<div id="addContent" style="display:none">
	    <table class="table table-bordered" id="userTable">
	      <tr>
	        <th class="text-center"><label for="tel1" class="form-label">전화번호</label></th>
	        <td>
	          <div class="input-group">
	            <div class="input-group-prepend">
		        	<select id="tel1" name="tel1" class="form-control mr-1">
		            <option>010</option>
		            <option value="02">서울</option>
		            <option value="041">천안</option>
		            <option value="042">대전</option>
		            <option value="043">청주</option>
		            <option value="031">인천</option>
		          </select>
		          </div>
		          -<input type="text" name="tel2" id="tel2" maxlength="4" class="form-control ml-1 mr-1">
		          -<input type="text" name="tel3" id="tel3" maxlength="4" class="form-control ml-1">
	          </div>
	        </td>
	      </tr>
	      <tr class="mb-2">
	        <th class="text-center"><label for="address" class="form-label">주소</label></th>
	        <td>
	          <div class="input-group mb-1">
			      	<input type="text" name="postcode" id="sample6_postcode" onclick="sample6_execDaumPostcode()" placeholder="우편번호" class="form-control me-1" readonly>
							<input type="button" value="우편번호 찾기" onclick="sample6_execDaumPostcode()" class="btn btn-secondary">
						</div>
						<input type="text" name="address" id="sample6_address" onclick="sample6_execDaumPostcode()" placeholder="주소" class="form-control mb-1" readonly>
						<div class="input-group mb-1">
							<input type="text" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소" class="form-control me-1">
							<input type="text" name="extraAddress" id="sample6_extraAddress" onclick="sample6_execDaumPostcode()" placeholder="참고항목" class="form-control" readonly>
						</div>
	        </td>
	      </tr>
	      <tr class="mb-2">
	        <th class="text-center"><label for="content" class="form-label">자기소개</label></th>
	        <td><textarea rows="4" name="content" id="content" class="form-control" placeholder="자기소개를 간단히 입력하세요"></textarea></td>
	      </tr>
	      <tr class="mb-2">
	        <th class="text-center"><label for="photo" class="form-label">사진</label></th>
	        <!-- <td><input type="file" name="photo" id="photo" class="form-control-file border" /></td> -->
	        <td><input type="file" name="photo" id="photo" class="form-control" /></td>
	      </tr>
	      <tr class="mb-2">
	        <th class="text-center"><label for="userInfor" class="form-label">정보공개</label></th>
	        <td>
	          <input type="radio" name="userInfor" id="YES" value="공개" class="me-1" checked /><label for="YES">공개</label> &nbsp;&nbsp;
	          <input type="radio" name="userInfor" id="NO" value="비공개" class="me-1" /><label for="NO">비공개</label>
	        </td>
	      </tr>
	    </table>
    </div>
    <div class="text-center">
	    <button type="submit" class="btn btn-success memberInput me-2">회원가입</button>
	    <button type="reset" class="btn btn-info memberInput me-2">다시입력</button>
    	<button type="button" onclick="location.href='${ctp}/member/memberLogin';" class="btn btn-warning mb-2">돌아가기</button>
	  </div>
    <input type="hidden" name="tel" id="tel" />
    <input type="hidden" name="email" id="email" />
    <input type="hidden" name="address2" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>