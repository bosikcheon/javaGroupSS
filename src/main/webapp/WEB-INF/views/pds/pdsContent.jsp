<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%  pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pdsContent.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
    th {
      text-align: center;
      background-color: #eee !important;
    }
    h6{
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
    
    /* 별점에 스타일 설정하기 */
    #reviewForm fieldset {
      direction: rtl;
    }
    #reviewForm input[type=radio] {
      display: none;
    }
    #reviewForm label {
      font-size: 1.6em;
      color: transparent;
      text-shadow: 0 0 0 #f0f0f0;
    }
    #reviewForm label:hover {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    #reviewForm label:hover ~ label {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    #reviewForm input[type=radio]:checked ~ label {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    
  </style>
  <script>
    'use strict';
    
    $(function() {
    	$("#reviewShowBtn").hide();
    	$("#reviewHideBtn").show();
    	$("#reviewBox").show();
    });
    
    // 리뷰 보이기 버튼 클릭
    function reviewShow() {
    	$("#reviewShowBtn").hide();
    	$("#reviewHideBtn").show();
    	$("#reviewBox").show();
    }
    
    // 리뷰 가리기 버튼 클릭
    function reviewHide() {
    	$("#reviewShowBtn").show();
    	$("#reviewHideBtn").hide();
    	$("#reviewBox").hide();
    }
    
    // 화살표클릭시 화면 상단이동(부드럽게)
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
    
    // 리뷰 등록하기
    function reviewCheck() {
    	let star = reviewForm.star.value;
    	let review = reviewForm.review.value;
    	
    	if(star == "") {
    		alert("별점을 부여해 주세요");
    	}
    	
    	let query = {
    			part : 'pds',
    			partIdx: ${vo.idx},
    			mid    : '${sMid}',
    			nickName    : '${sNickName}',
    			star     : star,
    			content: review
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "reviewInputOk",
    		data : query,
    		success:function(res) {
    			if(res != "0") alert("리뷰가 등록되었습니다.");
    			else alert("리뷰 등록 실패~~");
  				location.reload();
    		},
    		error : function() {
    			alert('전송오류!!');
    		}
    	});
    }
    
    // 리뷰 삭제하기
    function reviewDelete(idx) {
    	let ans = confirm("리뷰를 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "reviewDelete",
    		type : "post",
    		data : {idx : idx},
    		success:function(res) {
    			if(res != "0") {
    				alert('리뷰가 삭제되었습니다.');
    				location.reload();
    			}
    			else alert("리뷰 삭제 실패~~\n하위 댓글이 존재합니다.");
    		},
    		error : function() {
    			alert("하위 댓글이 존재합니다. 확인하세요.");
    		}
    	});
    }
    
    // 리뷰 댓글 달기폼 보여주기
    function reviewReply(idx, nickName, content) {
    	if(content == "") content = "글내용 없음"; 
    	$("#myModal #reviewIdx").val(idx);
    	$("#myModal #reviewReplyNickName").text(nickName);
    	$("#myModal #reviewReplyContent").html(content);
    }
    
    // 리뷰 댓글 달기
    function reviewReplyCheck() {
    	let replyContent = reviewReplyForm.replyContent.value;
    	let reviewIdx = reviewReplyForm.reviewIdx.value;
    	
    	if(replyContent.trim() == "") {
    		alert("리뷰 댓글을 입력하세요");
    		return false;
    	}
    	
    	let query = {
    			reviewIdx : reviewIdx,
    			replyMid  : '${sMid}',
    			replyNickName : '${sNickName}',
    			replyContent  : replyContent
    	}
    	
    	$.ajax({
    		url  : "reviewReplyInputOk",
    		type : "post",
    		data : query,
    		success:function(res) {
    			if(res != "0") {
    				alert("댓글이 등록되었습니다.");
    				location.reload();
    			}
    			else alert("댓글 등록 실패~~");
    		},
    		error : function() {
    			alert("전송 오류!");
    		}
    	});
    }
    
    // 리뷰 댓글 삭제
    function reviewReplyDelete(replyIdx) {
    	let ans = confirm("리뷰 댓글을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "reviewReplyDelete",
    		data : {replyIdx : replyIdx},
    		success:function(res) {
    			if(res != "0") {
    				alert('리뷰댓글이 삭제 되었습니다.');
    				location.reload();
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
  <h2 class="text-center">자료실 내용 상세보기</h2>
  <br/>
  <table class="table table-bordered text-center">
    <tr>
      <th>올린이</th>
      <td>${vo.nickName}</td>
      <th>올린날짜</th>
      <td>${vo.FDate}</td>
    </tr>
    <tr>
      <th>파일명</th>
      <td>
				<c:set var="fNames" value="${fn:split(vo.FName,'/')}"/>
        <c:set var="fSNames" value="${fn:split(vo.FSName,'/')}"/>
        <c:forEach var="fName" items="${fNames}" varStatus="st">
          <c:if test="${sLevel != 1}"><a href="${ctp}/pds/${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})" class="text-decoration-none text-dark link-primary">${fName}</a><br/></c:if>
          <c:if test="${sLevel == 1}">${fName}<br/></c:if>
        </c:forEach>          
        (<fmt:formatNumber value="${vo.FSize/1024}" pattern="#,##0" />KByte)
      </td>
      <th>다운횟수</th>
      <td>${vo.downNum}</td>
    </tr>
    <tr>
      <th>분류</th>
      <td>${vo.part}</td>
      <th>접속아이피</th>
      <td>${vo.hostIp}</td>
    </tr>
    <tr>
      <th>상세내역</th>
      <td colspan="3" class="text-start">
      	${fn:replace(vo.content,newLine,"<br/>")}
      </td>
    </tr>
  </table>
  <br/>
  <div class="text-center"><a href="pdsList?part=${pageVO.part}&pag=${pageVO.pag}&pageSize=${pageVO.pageSize}" class="btn btn-info text-center">돌아가기</a></div>
  <hr/>
  
  <!-- 리뷰 작성하기/리스트보기 시작 -->
  <div>
    <form name="reviewForm" id="reviewForm">
      <fieldset style="border:0px">
        <div class="text-left">
          <input type="radio" name="star" value="5" id="start1"><label for="start1">★</label>
          <input type="radio" name="star" value="4" id="start2"><label for="start2">★</label>
          <input type="radio" name="star" value="3" id="start3"><label for="start3">★</label>
          <input type="radio" name="star" value="2" id="start4"><label for="start4">★</label>
          <input type="radio" name="star" value="1" id="start5"><label for="start5">★</label>
          : 별점을 선택해 주세요 ■
        </div>
      </fieldset>
      <div class="m-0 p-0">
        <textarea rows="3" name="review" id="review" class="form-control mb-1" placeholder="별점후기를 남겨주시면 100포인트를 지급합니다."></textarea>
      </div>
      <div>
        <input type="button" value="별점/리뷰등록" onclick="reviewCheck()" class="btn btn-primary form-control"/>
      </div>
    </form>
  </div>
  <hr/>
  <div class="row">
    <div class="col">
      <input type="button" value="리뷰보이기" id="reviewShowBtn" onclick="reviewShow()" class="btn btn-success">
      <input type="button" value="리뷰가리기" id="reviewHideBtn" onclick="reviewHide()" class="btn btn-warning">
    </div>
    <div class="col text-end me-1">
      <b>리뷰평점 : <fmt:formatNumber value="${reviewAvg}" pattern="#,##0.0" /></b>
    </div>
  </div>
  <hr/>
  <div id="reviewBox">
    <c:forEach var="vo" items="${rVos}" varStatus="st">
    	<c:if test="${imsiIdx != vo.idx}">	<!-- 같은 리뷰에 두개이상의 댓글이 달릴경우 처리.. -->
	      <div class="row mt-3">
	        <div class="col ml-2">
	          <b>${vo.nickName}</b>
	          <span style="font-size:11px">${fn:substring(vo.reviewDate, 0, 10)}</span>
	          <c:if test="${vo.mid == sMid || sLevel == 0}"><a href="javascript:reviewDelete(${vo.idx})" title="리뷰삭제" class="badge bg-danger text-decoration-none" style="font-size:10px">x</a></c:if>
	          <c:if test="${vo.mid != sMid}"><a href="#" onclick="reviewReply('${vo.idx}','${vo.nickName}','${fn:replace(vo.content,newLine,'<br>')}')" title="댓글달기" data-bs-toggle="modal" data-bs-target="#myModal" class="badge bg-secondary" style="font-size:10px">▤</a></c:if>
	        </div>
	        <div class="col text-end me-2">
	          <c:forEach var="i" begin="1" end="${vo.star}" varStatus="iSt"><font color="gold">★</font></c:forEach>
	          <c:forEach var="i" begin="1" end="${5 - vo.star}" varStatus="iSt">☆</c:forEach>
	        </div>
	      </div>
	      <div class="row border m-1 p-2">
	        <c:if test="${!empty vo.content}">${fn:replace(vo.content, newLine , '<br/>')}</c:if>
	        <c:if test="${empty vo.content}"><font color="#ccc">내용없음</font></c:if>
	      </div>
      </c:if>
      <c:set var="imsiIdx" value="${vo.idx}"/>
      <c:if test="${!empty vo.replyContent}">
        <div class="d-flex text-secondary">
          <div class="mt-2 ml-3">└▶ </div>
          <div class="mt-2 ml-2">${vo.replyNickName}
            <span style="font-size:11px">${fn:substring(vo.replyDate,0,10)}</span>
            <c:if test="${vo.replyMid == sMid || sLevel == 0}"><a href="javascript:reviewReplyDelete(${vo.replyIdx})" title="댓글삭제" class="badge bg-warning text-decoration-none m-0 p-1">x</a></c:if>
            <br/>${fn:replace(vo.replyContent,newLine,"<br/>")}
          </div>
        </div>
      </c:if>
    </c:forEach>
  </div>
  <!-- 리뷰 작성하기/리스트보기 끝 -->
  
  <br/>
  <!-- 자료실에 등록된 자료가 사진이라면, 아래쪽에 사진들을 모두 보여준다. -->
  <div class="text-center">
    <c:forEach var="fSName" items="${fSNames}" varStatus="st">
    	${st.count}. ${fNames[st.index]}<br/>
    	<c:set var="len" value="${fn:length(fSName)}" />
    	<c:set var="ext" value="${fn:substring(fSName, len-3, len)}" />
    	<c:set var="extLower" value="${fn:toLowerCase(ext)}" />
    	<c:if test="${extLower == 'jpg' || extLower == 'gif' || extLower == 'png'}">
    	  <img src="${ctp}/pds/${fSName}" width="90%" />
    	</c:if>
    	<hr/>
    </c:forEach>
  </div>
  
  <!-- 위로가기 버튼 -->
  <h6 id="topBtn" class="text-right"><img src="${ctp}/images/top.gif" title="위로이동"/></h6>
  
</div>

<!-- 댓글달기를 위한 모달처리 -->
<div class="modal fade" id="myModal" style="font-size:0.9em;">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">>> 리뷰에 댓글달기</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <form name="reviewReplyForm" id="reviewReplyForm" class="was-vilidated">
          <table class="table table-bordered">
            <tr>
              <th style="width:25%">원본글작성자</th>
              <td style="width:75%"><span id="reviewReplyNickName"></span></td>
            </tr>
            <tr>
              <th>원본글</th>
              <td><span id="reviewReplyContent"></span></td>
            </tr>
          </table>
          <hr/>
          댓글 작성자 : ${sNickName}<br/>
          댓글 내용 : <textarea rows="3" name="replyContent" id="replyContent" class="form-control" required></textarea><br/>
          <input type="button" value="리뷰댓글등록" onclick="reviewReplyCheck()" class="btn btn-success form-control"/>
          <input type="hidden" name="reviewIdx" id="reviewIdx"/>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>