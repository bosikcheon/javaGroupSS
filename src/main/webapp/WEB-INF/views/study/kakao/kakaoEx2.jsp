<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>kakaoEx2.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
  	function addressSearch() {
  		let address = myform.address.value;
  		if(address == "") {
  			alert("검색할 지점을 선택하세요");
  			return false;
  		}
  		myform.submit();
  	}
  	
  	function addressDelete() {
  		let address = myform.address.value;
  		if(address == "") {
  			alert("검색할 지점을 선택하세요");
  			return false;
  		}
  		let ans = confirm("검색한 지점명을 MyDB에서 삭제하시겠습니까?");
  		if(!ans) return false;
  		
  		$.ajax({
  			type : "post",
  			url  : "${ctp}/study/kakao/kakaoAddressDelete",
  			data : {address : address},
  			success:function(res) {
  				if(res != "0") {
  					alert("선택한 지점이 MyDB에서 삭제 되었습니다.");
  					location.href = "${ctp}/study/kakao/kakaoEx2";
  				}
  				else alert("삭제 실패~~");
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
  <h2>MyDB에 저장된 지명으로 검색</h2>
  <hr/>
  <%-- <div>=${vo}=</div> --%>
  <form name="myform">
    <select name="address" id="address">
      <option value="">지역선택</option>
      <c:forEach var="addressVO" items="${addressVos}">
        <option <c:if test="${addressVO.address == vo.address}">selected</c:if>>${addressVO.address}</option>
      </c:forEach>
    </select>
	  <input type="button" value="지점선택" onclick="addressSearch()"/>
	  <input type="button" value="지점삭제" onclick="addressDelete()"/>
  </form>
  
  <hr/>
  <div id="map" style="width:100%;height:500px;"></div>
  
  
  <!-- 카카오맵.... -->
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=158c673636c9a17a27b67c95f2c6be5c"></script>
  <script>
	  var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	  mapOption = { 
	      center: new kakao.maps.LatLng(${vo.latitude}, ${vo.longitude}), // 지도의 중심좌표
	      level: 3 // 지도의 확대 레벨
	  };
	
		var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
		
		//지도를 클릭한 위치에 표출할 마커입니다
		var marker = new kakao.maps.Marker({ 
		  // 지도 중심좌표에 마커를 생성합니다 
		  position: map.getCenter() 
		});
		//지도에 마커를 표시합니다
		marker.setMap(map);
		
		//지도에 클릭 이벤트를 등록합니다
		//지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
		kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
		  
		  // 클릭한 위도, 경도 정보를 가져옵니다 
		  var latlng = mouseEvent.latLng; 
		  
		  // 마커 위치를 클릭한 위치로 옮깁니다
		  marker.setPosition(latlng);
		  
		  var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
		  message += '경도는 ' + latlng.getLng() + ' 입니다';
		  message += '<p>선택한 지점의 장소명 : <input type="text" name="address" id="address"/> &nbsp';
		  message += '<input type="button" value="장소DB저장" onclick="addressSave('+latlng.getLat()+','+latlng.getLng()+')" class="btn btn-success btn-sm"/>';
		  message += '</p>';
		  
		  var resultDiv = document.getElementById('clickLatlng'); 
		  resultDiv.innerHTML = message;
		  
		});
  </script>
  <hr/>
  <jsp:include page="kakaoMenu.jsp" />
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>