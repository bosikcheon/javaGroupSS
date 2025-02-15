<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>dbProduct.jsp(상품등록)</title>
	<jsp:include page="/WEB-INF/views/include/bs5.jsp"/>
  <script src="${ctp}/ckeditor/ckeditor.js"></script>
  <script>
    'use strict';
    
    // 상품 등록하기전에 모든 사항 체크후 전송처리한다.
    function fCheck() {
    	let categoryMainCode = myform.categoryMainCode.value;
    	let categoryMiddleCode = myform.categoryMiddleCode.value;
    	let categorySubCode = myform.categorySubCode.value;
    	let productName = myform.productName.value;
			let mainPrice = myform.mainPrice.value;
			let detail = myform.detail.value;
			let file = myform.file.value;	
			let ext = file.substring(file.lastIndexOf(".")+1);
			let uExt = ext.toUpperCase();
			let regExpPrice = /^[0-9|_]*$/;
			
			if(categorySubCode == "") {
				alert("상품 소분류(세분류)를 입력하세요!");
				return false;
			}
			else if(product == "") {
				alert("상품명(모델명)을 입력하세요!");
				return false;
			}
			else if(file == "") {
				alert("상품 메인 이미지를 등록하세요");
				return false;
			}
			else if(uExt != "JPG" && uExt != "GIF" && uExt != "PNG" && uExt != "JPEG") {
				alert("업로드 가능한 파일이 아닙니다.");
				return false;
			}
			else if(mainPrice == "" || !regExpPrice.test(mainPrice)) {
				alert("상품금액은 숫자로 입력하세요.");
				return false;
			}
			else if(detail == "") {
				alert("상품의 초기 설명을 입력하세요");
				return false;
			}
			else if(document.getElementById("file").value != "") {
				var maxSize = 1024 * 1024 * 10;  // 10MByte까지 허용
				var fileSize = document.getElementById("file").files[0].size;
				if(fileSize > maxSize) {
					alert("첨부파일의 크기는 10MB 이내로 등록하세요");
					return false;
				}
				else {
					myform.submit();
				}
			}
    }
    
    function categoryMainChange() {
    	var categoryMainCode = myform.categoryMainCode.value;
			$.ajax({
				type : "post",
				url  : "${ctp}/dbShop/categoryMiddleName",
				data : {categoryMainCode : categoryMainCode},
				success:function(data) {
					var str = "";
					str += "<option value=''>중분류</option>";
					for(var i=0; i<data.length; i++) {
						str += "<option value='"+data[i].categoryMiddleCode+"'>"+data[i].categoryMiddleName+"</option>";
					}
					$("#categoryMiddleCode").html(str);
				},
				error : function() {
					alert("전송오류!");
				}
			});
  	}
    
    function categoryMiddleChange() {
    	var categoryMainCode = myform.categoryMainCode.value;
    	var categoryMiddleCode = myform.categoryMiddleCode.value;
			$.ajax({
				type : "post",
				url  : "${ctp}/dbShop/categorySubName",
				data : {
					categoryMainCode : categoryMainCode,
					categoryMiddleCode : categoryMiddleCode
				},
				success:function(data) {
					var str = "";
					str += "<option value=''>소분류</option>";
					for(var i=0; i<data.length; i++) {
						str += "<option value='"+data[i].categorySubCode+"'>"+data[i].categorySubName+"</option>";
					}
					$("#categorySubCode").html(str);
				},
				error : function() {
					alert("전송오류!");
				}
			});
  	}
  </script>
</head>
<body>
<br/>
<div class="container">
  <div id="product">
    <h3>상품등록</h3>
    <form name="myform" method="post" enctype="multipart/form-data">
      <div class="input-group mb-2">
        <div class="input-group-text"><label for="categoryMainCode">대분류</label></div>
        <select id="categoryMainCode" name="categoryMainCode" class="form-control" onchange="categoryMainChange()">
          <option value="">대분류를 선택하세요</option>
          <c:forEach var="mainVO" items="${mainVos}">
          	<option value="${mainVO.categoryMainCode}">${mainVO.categoryMainName}</option>
          </c:forEach>
        </select>
      </div>
      <div class="input-group mb-2">
        <div class="input-group-text"><label for="categoryMiddleCode">중분류</label></div>
        <select id="categoryMiddleCode" name="categoryMiddleCode" class="form-control" onchange="categoryMiddleChange()">
          <option value="">중분류명</option>
		  	  <c:forEach var="middleVO" items="${middleVos}">
		  	    <option value=""></option>
		  	  </c:forEach>
        </select>
      </div>
      <div class="input-group mb-2">
        <div class="input-group-text"><label for="categorySubCode">소분류</label></div>
        <select id="categorySubCode" name="categorySubCode" class="form-control">
          <option value="">중분류명</option>
        </select>
      </div>
      <div class="input-group mb-2">
        <div class="input-group-text"><label for="productName">상품(모델)명</label></div>
        <input type="text" name="productName" id="productName" class="form-control" placeholder="상품 모델명을 입력하세요" required />
      </div>
      <div class="input-group mb-2">
        <div class="input-group-text"><label for="file">메인이미지</label></div>
        <input type="file" name="file" id="file" class="form-control" accept=".jpg,.gif,.png,.jpeg" required />
        (업로드 가능파일:jpg, jpeg, gif, png)
      </div>
      <div class="input-group mb-2">
      	<div class="input-group-text"><label for="mainPrice">상품기본가격</label></div>
      	<input type="text" name="mainPrice" id="mainPrice" class="form-control" required />
      </div>
      <div class="input-group mb-3">
      	<div class="input-group-text"><label for="detail">상품기본설명</label></div>
      	<input type="text" name="detail" id="detail" class="form-control" required />
      </div>
      <div class="mb-2">
      	<label for="content">상품상세설명</label>
      	<textarea rows="5" name="content" id="CKEDITOR" class="form-control" required></textarea>
      </div>
      <script>
		    CKEDITOR.replace("content",{
		    	uploadUrl:"${ctp}/imageUpload",
		    	filebrowserUploadUrl: "${ctp}/imageUpload",
		    	height:460
		    });
		  </script>
		  <input type="button" value="상품등록" onclick="fCheck()" class="btn btn-secondary"/> &nbsp;
    </form>
  </div>
</div>
<p><br/></p>
</body>
</html>