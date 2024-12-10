package com.spring.javaGroupS.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaGroupS.service.DbtestService;
import com.spring.javaGroupS.service.StudyService;
import com.spring.javaGroupS.vo.UserVO;

//@RestController
@Controller
@RequestMapping("/study")
public class StudyController {

	@Autowired
	StudyService studyService;
	
	@Autowired
	DbtestService dbtestService;
	
	@RequestMapping(value="/ajax/ajaxForm", method = RequestMethod.GET)
	public String ajaxFormGet() {
		return "study/ajax/ajaxForm";
	}
	
	@RequestMapping(value="/ajax/ajaxTest1", method = RequestMethod.GET)
	public String ajaxTest1Get(Model model, int idx) {
		model.addAttribute("idx", idx);
		return "study/ajax/ajaxForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest2", method = RequestMethod.GET)
	public String ajaxTest2Get(int idx) {
		System.out.println("idx : " + idx);
		//return "study/ajax/ajaxForm";
		return idx + "";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest3", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String ajaxTest3Post(String str) {
		System.out.println("str : " + str);
		return str;
	}
	
	@RequestMapping(value="/ajax/ajaxTest4_1", method = RequestMethod.GET)
	public String ajaxTest4_1Get() {
		return "study/ajax/ajaxTest4_1";
	}
	
	// 배열형식(문자열)의 자료처리
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_1", method = RequestMethod.POST)
	public String[] ajaxTest4_1Post(String dodo) {
//		String[] strArray = new String[100];
//		strArray = studyService.getCityStringArray(dodo);
//		return strArray;
		return studyService.getCityStringArray(dodo);
	}
	
	@RequestMapping(value="/ajax/ajaxTest4_2", method = RequestMethod.GET)
	public String ajaxTest4_2Get() {
		return "study/ajax/ajaxTest4_2";
	}
	
	// ArrayList의 자료처리
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest4_2Post(String dodo) {
		return studyService.getCityStringArrayList(dodo);
	}
	
	@RequestMapping(value="/ajax/ajaxTest4_3", method = RequestMethod.GET)
	public String ajaxTest4_3Get() {
		return "study/ajax/ajaxTest4_3";
	}
	
	// Map형식(Key,value)의 자료처리
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_3", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest4_3Post(String dodo) {
		ArrayList<String> vos = studyService.getCityStringArrayList(dodo);
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		return map;
	}
	
	@RequestMapping(value="/ajax/ajaxTest4_4", method = RequestMethod.GET)
	public String ajaxTest4_4Get(Model model) {
		ArrayList<String> midVos = dbtestService.getDbtestMidList();
		model.addAttribute("midVos", midVos);
		return "study/ajax/ajaxTest4_4";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_4", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String ajaxTest4_4Post(String mid) {
		UserVO vo = dbtestService.getDbtestIdCheckForm(mid);
		String str = "<h3>회원정보</h3>";
		str += "아이디 : " + vo.getMid() + "<br>";
		str += "비밀번호 : " + vo.getPwd() + "<br>";
		str += "성명 : " + vo.getName() + "<br>";
		str += "나이 : " + vo.getAge() + "<br>";
		str += "성별 : " + vo.getGender() + "<br>";
		str += "주소 : " + vo.getAddress() + "<br>";
		return str;
	}
}
