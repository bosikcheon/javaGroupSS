<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>dbProductContent.jsp(상품정보 상세보기)</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp"/>
  <script>
    'use strict';

    let idxArray = new Array();

    // 옵션박스에서, 옵션항목을 선택하였을때 처리하는 함수
    $(function(){
      $("#selectOption").change(function(){
        let selectOption = $(this).val();		// (옵션값은 '옵션고유번호:옵션명_옵션가격' 형식으로 넘어오고 있다.)
        let idx = selectOption.substring(0,selectOption.indexOf(":"));
        let optionName = selectOption.substring(selectOption.indexOf(":")+1,selectOption.indexOf("_"));
        let optionPrice = selectOption.substring(selectOption.indexOf("_")+1);
        let commaPrice = numberWithCommas(optionPrice);

        if($("#layer"+idx).length == 0) {
          idxArray[idx] = idx;
          let str = '';
          str += '<div class="layer row" id="layer'+idx+'"><div class="col-5">'+optionName+'</div><div class="col-7">';
          str += '<input type="number" class="text-center numBox" id="numBox'+idx+'" name="optionNum" onchange="numChange('+idx+')" value="1" min="1"/> &nbsp;';
          str += '<input type="text" id="imsiPrice'+idx+'" class="price" value="'+commaPrice+'" readonly />';
          str += '<input type="hidden" id="price'+idx+'" value="'+optionPrice+'"/> &nbsp;';
          str += '<input type="button" class="btn btn-outline-danger btn-sm mt-1" onclick="remove('+idx+')" value="삭제"/>';
          str += '<input type="hidden" name="statePrice" id="statePrice'+idx+'" value="'+optionPrice+'"/>';
          str += '<input type="hidden" name="optionIdx" value="'+idx+'"/>';
          str += '<input type="hidden" name="optionName" value="'+optionName+'"/>';
          str += '<input type="hidden" name="optionPrice" value="'+optionPrice+'"/>';
          str += '</div></div>';
          $("#product1").append(str);
          onTotal();
        }
        else {
          alert("이미 선택한 옵션입니다.");
        }
      });
    });

    // 추가시킨 옵션 삭제처리
    function remove(idx) {
      $("div").remove("#layer"+idx);

      if($(".price").length) onTotal();
      else location.reload();
    }

    // 상품 총 금액 (재)계산
    function onTotal() {
      let total = 0;
      for(let i=0; i<idxArray.length; i++) {
        if($("#layer"+idxArray[i]).length != 0) {
          total +=  parseInt(document.getElementById("price"+idxArray[i]).value);
          document.getElementById("totalPriceResult").value = total;
        }
      }
      document.getElementById("totalPrice").value = numberWithCommas(total);
    }

    // 수량 변경시 처리하는 함수
    function numChange(idx) {
      let price = document.getElementById("statePrice"+idx).value * document.getElementById("numBox"+idx).value;
      document.getElementById("imsiPrice"+idx).value = numberWithCommas(price);
      document.getElementById("price"+idx).value = price;
      onTotal();
    }

    // 장바구니에 구매한 상품 담기(호출)
    function cart() {
      if(document.getElementById("totalPrice").value==0) {
        alert("옵션을 선택해주세요");
        return false;
      }
      else {
        document.myform.submit();
      }
    }

    // 직접 주문하기
    function order() {
      let totalPrice = document.getElementById("totalPrice").value;
      if('${sMid}' == "") {
        alert("로그인 후 이용 가능합니다.");
        location.href = "${ctp}/member/memLogin";
      }
      else if(totalPrice=="" || totalPrice==0) {
        alert("옵션을 선택해주세요");
        return false;
      }
      else {
        document.getElementById("flag").value = "order";
        alert("현재 선택한 상품을 직접 주문처리 합니다.");
        //document.myform.submit();
      }
    }

    // 천단위마다 콤마를 표시해 주는 함수
    function numberWithCommas(x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
    }
  </script>
  <style>
    .layer  {
      border:0px;
      width:100%;
      padding:10px;
      margin-left:1px;
      background-color:#eee;
    }
    .numBox {width:60px}
    .price  {
      width:140px;
      background-color:#eee;
      text-align:right;
      font-size:1.2em;
      border:0px;
      outline: none;
    }
    .totalPrice {
      text-align:right;
      margin-right:10px;
      color:#f63;
      font-size:1.5em;
      font-weight: bold;
      border:0px;
      outline: none;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
  <div class="row">
    <div class="col p-3 text-center" style="border:1px solid #ccc">
      <!-- 상품메인 이미지 -->
      <div>
        <img src="${ctp}/product/${productVO.FSName}" width="100%"/>
      </div>
    </div>
    <div class="col p-3 text-right">
      <!-- 상품 기본정보 -->
      <div class="text-left"><h3>${productVO.detail}</h3></div>
      <div>
        <h3><font color="orange"><fmt:formatNumber value="${productVO.mainPrice}"/>원</font></h3>
        <h4>${productVO.productName}</h4>
      </div>

			<!-- 옵션항목처리(옵션값은 '옵션고유번호:옵션명_옵션가격' 형식으로 처리하고 있다.) -->
      <div>
        <form name="optionForm">
          <select size="1" class="form-control" id="selectOption">
            <option value="" disabled selected>상품옵션선택</option>
            <option value="0:기본품목_${productVO.mainPrice}">기본품목</option>
            <c:forEach var="vo" items="${optionVos}">
              <option value="${vo.idx}:${vo.optionName}_${vo.optionPrice}">${vo.optionName}</option>
            </c:forEach>
          </select>
        </form>
      </div>
      <br/>
      <div>
        <form name="myform" method="post">
          <input type="hidden" name="mid" value="${sMid}"/>
          <input type="hidden" name="productIdx" value="${productVO.idx}"/>
          <input type="hidden" name="productName" value="${productVO.productName}"/>
          <input type="hidden" name="mainPrice" value="${productVO.mainPrice}"/>
          <input type="hidden" name="thumbImg" value="${productVO.FSName}"/>
          <input type="hidden" name="totalPrice" id="totalPriceResult"/>
          <input type="hidden" name="flag" id="flag"/>

          <div id="product1"></div>
        </form>
      </div>

      <div>
        <hr/>
        <div class="text-left"><font size="4" color="black">총상품금액</font></div>
        <p class="text-right">
          <b><input type="text" class="totalPrice text-right" id="totalPrice" value="<fmt:formatNumber value='0'/>" readonly /></b>
        </p>
      </div>
      <br/>
      <div class="text-center">
        <button class="btn btn-success btn-sm" onclick="cart()">장바구니담기</button>
        <button class="btn btn-info btn-sm" onclick="order()">주문하기</button>
        <button class="btn btn-primary btn-sm" onclick="location.href='${ctp}/dbShop/dbProductList';">계속쇼핑하기</button>
        <button class="btn btn-warning btn-sm" onclick="location.href='${ctp}/dbShop/dbCartList';">장바구니보기</button>
      </div>
    </div>
  </div>
  <br/><br/>
	
  <div id="content" class="text-center"><br/>
    ${productVO.content}
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>