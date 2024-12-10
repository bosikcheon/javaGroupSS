package com.spring.javaGroupS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javaGroupS.service.GuestService;
import com.spring.javaGroupS.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {
	
	@Autowired
	GuestService guestService;
	
	// guestList 호출하기(방명록 전체 리스트호출)
	@GetMapping("/guestList")
	public String guestListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "3", required = false) int pageSize
		) {
		
		int totRecCnt = guestService.getTotRecCnt();
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		List<GuestVO> vos = guestService.getGuestList(startIndexNo, pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totRecCnt", totRecCnt);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "guest/guestList";
	}
	
	// guestInput 폼호출하기
	@GetMapping("/guestInput")
	public String guestInputGet() {
		return "guest/guestInput";
	}
	
	// guestInput 폼호출하기
	@PostMapping("/guestInput")
	public String guestInputPost(GuestVO vo) {
		int res = guestService.setGuestInput(vo);
		if(res != 0) return "redirect:/message/guestInputOk";
		else return "redirect:/message/guestInputNo";
	}
	
	// guestAdmin 관리자 폼호출하기
	@GetMapping("/guestAdmin")
	public String guestAdminGet() {
		return "guest/guestAdmin";
	}
	
	// 관리자 인증처리하기
	@PostMapping("/guestAdmin")
	public String guestAdminPost(HttpSession session, String mid, String pwd) {
		if(mid.equals("admin") && pwd.equals("1234")) {
			session.setAttribute("sAdmin", "adminOk");
			return "redirect:/message/adminOk";
		}
		else return "redirect:/message/adminNo";
	}
	
	// guestAdmin 관리자 폼호출하기
	@GetMapping("/guestAdminOut")
	public String guestAdminOutGet(HttpSession session) {
		session.invalidate();
		return "redirect:/message/adminOut";
	}
	
	// 방명록 삭제하기
	@GetMapping("/guestDeleteOk")
	public String guestDeleteOkGet(int idx) {
		int res = guestService.setGuestDeleteOk(idx);
		if(res != 0) return "redirect:/message/guestDeleteOk";
		else return "redirect:/message/guestDeleteNo";
	}
}
