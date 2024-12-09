package com.spring.javaGroupS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaGroupS.service.UserService;
import com.spring.javaGroupS.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/userMain", method = RequestMethod.GET)
	public String userMainGet(Model model) {
		// 전체회원 인원수를 구해서 넘겨준다.
		int cnt = userService.getUserCnt();
		model.addAttribute("cnt", cnt);
		return "user/userMain";
	}
	
	@RequestMapping(value = "/userInput", method = RequestMethod.GET)
	public String userInputGet() {
		return "user/userInput";
	}
	
	@RequestMapping(value = "/userInput", method = RequestMethod.POST)
	public String userInputPost(UserVO vo) {
		// 아이디 중복체크
		UserVO vo2 = userService.getUserIdSearch(vo.getMid());
		if(vo2 != null) return "redirect:/message/userDuplication";
		
		// 회원 가입처리
		int res = userService.setUserInputOk(vo);
		if(res != 0) return "redirect:/message/userInputOk";
		else return "redirect:/message/userInputNo";
	}
	
	// 회원 전체조회
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String userListGet(Model model) {
		List<UserVO> vos = userService.getUserList();
		model.addAttribute("vos", vos);
		return "user/userList";
	}
	
	// 개별 회원 삭제
	@RequestMapping(value = "/userDeleteOk", method = RequestMethod.GET)
	public String userDeleteOkGet(int idx) {
		int res = userService.setUserDeleteOk(idx);
		if(res != 0) return "redirect:/message/userDeleteOk";
		else return "redirect:/message/userDeleteNo";
	}
	
	// 개별 회원 정보 수정폼 보기
	@RequestMapping(value = "/userUpdate", method = RequestMethod.GET)
	public String userUpdateGet(Model model, int idx) {
		UserVO vo = userService.getUserIdxSearch(idx);
		model.addAttribute("vo", vo);
		return "user/userUpdate";
	}

	// 개별 회원 정보 수정 처리
	@RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
	public String userUpdatePost(UserVO vo) {
		int res = userService.getUserUpdateOk(vo);
		if(res != 0) return "redirect:/message/userUpdateOk";
		else return "redirect:/message/userUpdateNo?idx="+vo.getIdx();
	}
	
	
	// 개별 회원 조회폼 보기
	@RequestMapping(value = "/userSearch", method = RequestMethod.GET)
	public String userSearchGet() {
		return "user/userSearch";
	}
	
	// 개별 회원 조회 처리
	@RequestMapping(value = "/userSearchOk", method = RequestMethod.GET)
	public String userSearchOkGet(Model model, String mid) {
		List<UserVO> vos = userService.getUserSearchList(mid);
		model.addAttribute("vos", vos);
		return "user/userSearch";
	}
	
}
