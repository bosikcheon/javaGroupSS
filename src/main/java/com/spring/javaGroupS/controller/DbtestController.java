package com.spring.javaGroupS.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.javaGroupS.service.DbtestService;
import com.spring.javaGroupS.vo.UserVO;

@Controller
@RequestMapping("/dbtest")
public class DbtestController {

	@Autowired
	private DbtestService dbtestService;
	
	@RequestMapping("/dbtestList")
	public String dbtestListGet(Model model) {
		ArrayList<UserVO> vos = dbtestService.getDbtestList();
		model.addAttribute("vos", vos);
		return "dbtest/dbtestList";
	}
	
	@RequestMapping("/dbtestInputOk")
	public String dbtestInputOkGet(UserVO vo) {
		UserVO vo2 = dbtestService.getDbtestIdCheckForm(vo.getMid());
		if(vo2 != null) return "redirect:/message/dbtestMidDuplication";
		
		int res = dbtestService.setDbtestInputOk(vo);
		if(res != 0) return "redirect:/message/dbtestInputOk";
		else return "redirect:/message/dbtestInputNo";
	}
	
	// 아이디 중복체크
	@RequestMapping("/dbtestIdCheckForm")
	public String dbtestInputOkGet(Model model, String mid) {
		UserVO vo = dbtestService.getDbtestIdCheckForm(mid);
		
		String idCheck = "";
		
		if(vo != null) idCheck = "NO";
		else idCheck = "OK";
		
		model.addAttribute("idCheck", idCheck);
		model.addAttribute("mid", mid);
		return "dbtest/dbtestIdCheckForm";
	}
	
	@RequestMapping("/dbtestDeleteOk")
	public String dbtestDeleteOkGet(int idx) {
		int res = dbtestService.setDbtestDeleteOk(idx);
		if(res != 0) return "redirect:/message/dbtestDeleteOk";
		else return "redirect:/message/dbtestDeleteNo";
	}
	
	@RequestMapping("/dbtestUpdateOk")
	public String dbtestUpdateOkGet(UserVO vo) {
		int res = dbtestService.setDbtestUpdateOk(vo);
		if(res != 0) return "redirect:/message/dbtestUpdateOk";
		else return "redirect:/message/dbtestUpdateNo";
	}
	
	@RequestMapping("/dbtestSearch/{mid}")
	public String dbtestSearchGet(Model model, UserVO vo, @PathVariable String mid) {
		ArrayList<UserVO> vos = dbtestService.getDbtestSearch(mid);
		model.addAttribute("vos", vos);
		model.addAttribute("mid", mid);
		return "dbtest/dbtestList";
	}
}
