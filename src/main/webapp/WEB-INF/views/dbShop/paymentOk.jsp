<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>sample.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
  <script>
  	IMP.init("imp87432616");
  	IMP.request_pay(
		  {
			  pg: "html5_inicis.INIpayTest",
		    pay_method: "card",
		    merchant_uid: "javaGroupS20_" + new Date().getTime(),
		    name: "${payMentVO.name}",
		    amount: ${payMentVO.amount},
		    buyer_email: "${payMentVO.buyer_email}",
		    buyer_name: "${payMentVO.buyer_name}",
		    buyer_tel: "${payMentVO.buyer_tel}",
		    buyer_addr: "${payMentVO.buyer_addr}",
		    buyer_postcode: "${payMentVO.buyer_postcode}",
		  },
		  function (res) {
			  var paySw = "no";
			  if(res.success) {
	        var msg = '결제가 완료되었습니다.';
	        msg += '\n고유ID : ' + res.imp_uid;
	        msg += '\n상점 거래ID : ' + res.merchant_uid;
	        msg += '\n결제 금액 : ' + res.paid_amount;
	        msg += '\n카드 승인번호 : ' + res.apply_num;
	        paySw = 'ok';
			  }
			  else {
				  var msg = "결재에 실패하였습니다.";
				  msg += "에러내용 : " + res.error_msg;
			  }
			  alert(msg);
		    if(paySw == 'no') {
			    alert("다시 주문구매창으로 이동합니다.");
		    	location.href='${ctp}/dbShop/dbProductList';
		    }
		    else {
					var temp = "";
					temp += '?name=${payMentVO.name}';
					temp += '&amount=${payMentVO.amount}';
					temp += '&buyer_email=${payMentVO.buyer_email}';
					temp += '&buyer_name=${payMentVO.buyer_name}';
					temp += '&buyer_tel=${payMentVO.buyer_tel}';
					temp += '&buyer_addr=${payMentVO.buyer_addr}';
					temp += '&buyer_postcode=${payMentVO.buyer_postcode}';
					temp += '&imp_uid=' + res.imp_uid;
					temp += '&merchant_uid=' + res.merchant_uid;
					temp += '&paid_amount=' + res.paid_amount;
					temp += '&apply_num=' + res.apply_num;
					
					location.href='${ctp}/dbShop/paymentResult'+temp;
		    }
		  },
		);
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <p>
    <img src="${ctp}/images/payment.gif" />
  </p>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>