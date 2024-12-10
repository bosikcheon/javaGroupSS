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
			@RequestParam(name="idx", defaultValue = "0", required = false) int idx
		) {
		
		if(msgFlag.equals("loginNo")) {
			model.addAttribute("message", mid + "회원님 로그인 실패~~~");
			//model.addAttribute("url", "/1127/t21");
			model.addAttribute("url", "/1127/t21/bbbb/5555");
		}
		else if(msgFlag.equals("loginNo2")) {
			model.addAttribute("message", mid + "회원님 로그인 실패~~~");
			model.addAttribute("url", "/1128/login");
		}
		else if(msgFlag.equals("loginOk")) {
			model.addAttribute("message", mid + "회원님 로그인 되셨습니다.");
			model.addAttribute("url", "/1128/loginMain");
		}
		else if(msgFlag.equals("logout")) {
			model.addAttribute("message", "로그아웃 되셨습니다.");
			model.addAttribute("url", "/1128/login");
		}
		else if(msgFlag.equals("userInputOk")) {
			model.addAttribute("message", "회원에 가입 되셨습니다.");
			model.addAttribute("url", "/user/userMain");
		}
		else if(msgFlag.equals("userInputNo")) {
			model.addAttribute("message", "회원에 가입 실패~~");
			model.addAttribute("url", "/user/userInput");
		}
		else if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("message", "회원정보가 삭제처리 되었습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("message", "회원 삭제 실패~~");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("message", "회원정보가 수정 되었습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userUpdateNo")) {
			model.addAttribute("message", "회원 수정 실패~~");
			model.addAttribute("url", "/user/userUpdate?idx="+idx);
		}
		else if(msgFlag.equals("userDuplication")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n새로운 아이디를 입력하세요");
			model.addAttribute("url", "/user/userInput");
		}
		else if(msgFlag.equals("dbtestDuplication")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n새로운 아이디를 입력하세요");
			model.addAttribute("url", "/dbtest/dbtestIdCheckForm");
		}
		else if(msgFlag.equals("dbtestInputOk")) {
			model.addAttribute("message", "회원에 가입 되었습니다.");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestInputOk")) {
			model.addAttribute("message", "회원 가입 실패~~");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestDeleteOk")) {
			model.addAttribute("message", "회원목록에서 삭제되었습니다.");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestDeleteNo")) {
			model.addAttribute("message", "회원 삭제 실패");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestUpdateOk")) {
			model.addAttribute("message", "회원정보가 수정 되었습니다.");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestUpdateNo")) {
			model.addAttribute("message", "회원 수정 실패");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestMidDuplication")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n새로운 아이디를 입력하세요");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("message", "방명록에 글이 등록 되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("message", "방명록 글 등록 실패");
			model.addAttribute("url", "/guest/guestInput");
		}
		else if(msgFlag.equals("adminOk")) {
			model.addAttribute("message", "관리자 인증에 성공하셨습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("message", "관리자 인증 실패");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("adminOut")) {
			model.addAttribute("message", "관리자 로그아웃");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteOk")) {
			model.addAttribute("message", "방명록의 글이 삭제 되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteNo")) {
			model.addAttribute("message", "방명록의 글 삭제 실패~~");
			model.addAttribute("url", "/guest/guestList");
		}
		
		
		
		return "include/message";
	}
	
}
