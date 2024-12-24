package com.spring.javaGroupS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaGroupS.pagination.PageProcess;
import com.spring.javaGroupS.pagination.PageVO;
import com.spring.javaGroupS.service.MemberService;
import com.spring.javaGroupS.service.WebMessageService;
import com.spring.javaGroupS.vo.MemberVO;
import com.spring.javaGroupS.vo.WebMessageVO;

@Controller
@RequestMapping("/webMessage")
public class WebMessageController {

	@Autowired
	WebMessageService webMessageService;
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/webMessage", method = RequestMethod.GET)
	public String webMessageGet(Model model, HttpSession session,
			@RequestParam(name="mSw", defaultValue = "1", required = false) int mSw,
			@RequestParam(name="mFlag", defaultValue = "1", required = false) int mFlag,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "8", required = false) int pageSize,
			//@RequestParam(name="receiveSw", defaultValue = "", required = false) String receiveSw,
			@RequestParam(name="idx", defaultValue = "0", required = false) int idx
		) {
		
		String mid = (String) session.getAttribute("sMid");
		
		if(mSw == 0) {}			// 메세지 작성
		else if(mSw == 6) {	// 메세지 내용보기
			WebMessageVO vo = webMessageService.getWmMessageContent(idx, mid);
			model.addAttribute("vo", vo);
		}
		else if(mSw == 9) {	// 휴지통지우기
			List<WebMessageVO> vos = webMessageService.getWmMessageList(mid, 5, 0, pageSize);
			if(vos.size() != 0) {
				if(webMessageService.setWmDeleteAll(mid) != 0) return "redirect:/message/wmMessageResetOk";
				else return "redirect:/message/wmMessageResetOk";
			}
			else return "redirect:/message/wmMessageEmpty";
		}
		else {
			// 페이징처리..... pageVO 가져오기....
			PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "webMessage", mid, mSw+"");
			List<WebMessageVO> vos = webMessageService.getWmMessageList(mid, mSw, pageVO.getStartIndexNo(), pageSize);
			model.addAttribute("vos", vos);
			model.addAttribute("pageVO", pageVO);
		}
		
		model.addAttribute("mSw", mSw);
		model.addAttribute("mFlag", mFlag);
		
		return "webMessage/webMessage";
	}
	
	@RequestMapping(value = "/wmInputOk", method = RequestMethod.POST)
	public String wmInputOkPost(WebMessageVO vo) {
		MemberVO  vo2 = memberService.getMemberIdCheck(vo.getReceiveId());
		if(vo2 == null) return "redirect:/message/wmMemberIdNo";
		
		if(webMessageService.setWmInputOk(vo) != 0) return "redirect:/message/wmMemberInputOk";
		return "redirect:/message/wmMemberInputNo";
	}
	
	@RequestMapping(value = "/webDeleteCheck", method = RequestMethod.GET)
	public String webDeleteCheckGet(Model model, int idx, int mSw, int mFlag) {
		model.addAttribute("mSw", mSw);
		if(webMessageService.setWebDeleteCheck(idx, mFlag) != 0) return "redirect:/message/wmMessageDeleteOk";
		return "redirect:/message/wmMessageDeleteNo";
	}
	
	@ResponseBody
	@RequestMapping(value="/msgDelete", method=RequestMethod.POST)
	public String msgDeletePost(int idx, int mFlag) {
		return webMessageService.setWebDeleteCheck(idx, mFlag) + "";
	}
	
}
