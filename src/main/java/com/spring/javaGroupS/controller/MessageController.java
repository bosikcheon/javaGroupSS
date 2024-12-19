package com.spring.javaGroupS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String getMessage(Model model, 
			@PathVariable String msgFlag,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="idx", defaultValue = "0", required = false) int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		
		if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("message", "로그인 실패~~~");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("message", mid + "회원님 로그인 되셨습니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberLogout")) {
			model.addAttribute("message", "로그아웃 되셨습니다.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberLevelNo")) {
			model.addAttribute("message", "회원등급을 확인하세요.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("loginNo")) {
			model.addAttribute("message", "로그인후 사용하세요.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("userInputOk")) {
			model.addAttribute("message", "회원에 가입 되셨습니다.");
			model.addAttribute("url", "user/userMain");
		}
		else if(msgFlag.equals("userInputNo")) {
			model.addAttribute("message", "회원에 가입 실패~~");
			model.addAttribute("url", "user/userInput");
		}
		else if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("message", "회원정보가 삭제처리 되었습니다.");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("message", "회원 삭제 실패~~");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("message", "회원정보가 수정 되었습니다.");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userUpdateNo")) {
			model.addAttribute("message", "회원 수정 실패~~");
			model.addAttribute("url", "user/userUpdate?idx="+idx);
		}
		else if(msgFlag.equals("userDuplication")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n새로운 아이디를 입력하세요");
			model.addAttribute("url", "user/userInput");
		}
		else if(msgFlag.equals("dbtestDuplication")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n새로운 아이디를 입력하세요");
			model.addAttribute("url", "dbtest/dbtestIdCheckForm");
		}
		else if(msgFlag.equals("dbtestInputOk")) {
			model.addAttribute("message", "회원에 가입 되었습니다.");
			model.addAttribute("url", "dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestInputOk")) {
			model.addAttribute("message", "회원 가입 실패~~");
			model.addAttribute("url", "dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestDeleteOk")) {
			model.addAttribute("message", "회원목록에서 삭제되었습니다.");
			model.addAttribute("url", "dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestDeleteNo")) {
			model.addAttribute("message", "회원 삭제 실패");
			model.addAttribute("url", "dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestUpdateOk")) {
			model.addAttribute("message", "회원정보가 수정 되었습니다.");
			model.addAttribute("url", "dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestUpdateNo")) {
			model.addAttribute("message", "회원 수정 실패");
			model.addAttribute("url", "dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestMidDuplication")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n새로운 아이디를 입력하세요");
			model.addAttribute("url", "dbtest/dbtestList");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("message", "방명록에 글이 등록 되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("message", "방명록 글 등록 실패");
			model.addAttribute("url", "guest/guestInput");
		}
		else if(msgFlag.equals("adminOk")) {
			model.addAttribute("message", "관리자 인증에 성공하셨습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("message", "관리자 인증 실패");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminLevelNo")) {
			model.addAttribute("message", "관리자만 접속가능합니다.");
			model.addAttribute("url", "index");
		}
		else if(msgFlag.equals("adminOut")) {
			model.addAttribute("message", "관리자 로그아웃");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteOk")) {
			model.addAttribute("message", "방명록의 글이 삭제 되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteNo")) {
			model.addAttribute("message", "방명록의 글 삭제 실패~~");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("message", "메일이 전송되었습니다.\\n받는사람 메일주소를 확인해 주세요.");
			model.addAttribute("url", "study/mail/mailForm");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n새로운 아이디로 가입해주세요.");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("message", "닉네임이 중복되었습니다.\\n새로운 닉네임으로 가입해주세요.");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("message", "회원가입이 완료되었습니다.\\로그인후 사용해 주세요.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("message", "회원가입 실패~~\\다시 회원 가입후 사용해 주세요.");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("memberPwdChangeOk")) {
			model.addAttribute("message", "비밀번호가 변경되었습니다.\\n다시 로그인해 주세요.");
			model.addAttribute("url", "member/memberLogout");
		}
		else if(msgFlag.equals("memberPwdChangeNo")) {
			model.addAttribute("message", "비밀번호가 변경 실패~~");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberUpdate")) {
			model.addAttribute("message", "회원정보 수정창으로 이동합니다.");
			model.addAttribute("url", "member/memberUpdate");
		}
		else if(msgFlag.equals("nickNameCheckNo")) {
			model.addAttribute("message", "닉네임이 중복되었습니다.\\n확인하세요.");
			model.addAttribute("url", "member/memberUpdate");
		}
		else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("message", "정보가 수정되었습니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberUpdateNo")) {
			model.addAttribute("message", "정보 수정실패~~~");
			model.addAttribute("url", "member/memberUpdate");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("message", "게시판에 글이 등록되었습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("message", "게시판에 글 등록 실패~~");
			model.addAttribute("url", "board/boardInput");
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("message", "게시판에 글이 삭제 되었습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("message", "게시판에 글 삭제 실패~~");
			model.addAttribute("url", "board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("message", "게시판에 글이 수정되었습니다.");
			model.addAttribute("url", "board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("message", "게시판 글 수정실패~~");
			model.addAttribute("url", "board/boardUpdate?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("message", "파일 업로드 성공");
			model.addAttribute("url", "study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("message", "파일 업로드 실패~~");
			model.addAttribute("url", "study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("multiFileUploadOk")) {
			model.addAttribute("message", "파일 업로드 성공");
			model.addAttribute("url", "study/fileUpload/multiFile");
		}
		else if(msgFlag.equals("multiFileUploadNo")) {
			model.addAttribute("message", "파일 업로드 실패~~");
			model.addAttribute("url", "study/fileUpload/multiFile");
		}
		else if(msgFlag.equals("pdsInputOk")) {
			model.addAttribute("message", "자료실 업로드 성공");
			model.addAttribute("url", "pds/pdsList");
		}
		else if(msgFlag.equals("pdsInputNo")) {
			model.addAttribute("message", "자료실 업로드 실패~~");
			model.addAttribute("url", "pds/pdsInput");
		}
		
		
		
		return "include/message";
	}
	
}
