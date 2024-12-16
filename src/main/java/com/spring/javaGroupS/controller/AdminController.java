package com.spring.javaGroupS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaGroupS.service.AdminService;
import com.spring.javaGroupS.service.BoardService;
import com.spring.javaGroupS.service.GuestService;
import com.spring.javaGroupS.service.MemberService;
import com.spring.javaGroupS.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	GuestService guestService;
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/adminMain", method = RequestMethod.GET)
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@RequestMapping(value = "/adminLeft", method = RequestMethod.GET)
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	
	@RequestMapping(value = "/adminContent", method = RequestMethod.GET)
	public String adminContentGet(Model model) {
		int guestRecentCnt = guestService.getRecentCnt();	// 최신 1주일이내 방명록 게시글
		int boardRecentCnt = boardService.getRecentCnt();	// 최신 1주일이내 게시판 게시글
		int level3Cnt = memberService.getLevelCnt(3);			// 신규회원수
		int level99Cnt = memberService.getLevelCnt(99);		// 탈회신청회원수
	
		model.addAttribute("guestRecentCnt", guestRecentCnt);
		model.addAttribute("boardRecentCnt", boardRecentCnt);
		model.addAttribute("level3Cnt", level3Cnt);
		model.addAttribute("level99Cnt", level99Cnt);
		
		return "admin/adminContent";
	}
	
	// 회원 전체 리스트(페이징 처리)
	@RequestMapping(value = "/member/memberList", method = RequestMethod.GET)
	public String memberListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name="level", defaultValue = "999", required = false) int level
		) {
		
		int totRecCnt = memberService.getTotRecCnt("admin");
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		//List<MemberVO> vos = memberService.getMemberList(startIndexNo, pageSize, "admin");
		List<MemberVO> vos = memberService.getMemberLevelList(startIndexNo, pageSize, level);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totRecCnt", totRecCnt);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		model.addAttribute("level", level);
		
		return "admin/member/memberList";
	}

	// 관리자가 레벨을 수동 변경처리
	@ResponseBody
	@RequestMapping(value = "/member/memberLevelChange", method = RequestMethod.POST)
	public String memberLevelChangeGet(int idx, int level) {
		return adminService.setMemberLevelChange(idx, level) + "";
	}
	
}
