package com.spring.javaGroupS.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaGroupS.service.SurveyService;
import com.spring.javaGroupS.vo.SurveyBasicSurveyVO;
import com.spring.javaGroupS.vo.SurveyQuestionContentVO;
import com.spring.javaGroupS.vo.SurveyQuestionVO;
import com.spring.javaGroupS.vo.SurveyRealAnswerVO;
import com.spring.javaGroupS.vo.SurveyVO;

@Controller
@RequestMapping("/survey")
public class SurveyController {

	@Autowired
	SurveyService surveyService;
	
	@RequestMapping(value = "/surveyInput", method = RequestMethod.GET)
	public String surveyInputGet() {
		return "admin/survey/surveyInput";
	}
	
	@RequestMapping(value = "/surveyInput", method = RequestMethod.POST)
	public String surveyInputPost(Model model, SurveyVO vo) {
		if(vo.getShowSw() == null) vo.setShowSw("0");
		else vo.setShowSw("1");
		surveyService.setSurveyInput(vo);
		int idx = surveyService.getLastIdx();
		return "redirect:/message/surveyInput?idx="+idx;
	}
	
	@RequestMapping(value = "/surveyList", method = RequestMethod.GET)
	public String surveyListGet(Model model) {
		List<SurveyVO> vos = surveyService.getSurveyList(99);
		model.addAttribute("vos", vos);
		
		return "admin/survey/surveyList";
	}
	
	@RequestMapping(value = "/surveyUpdate", method = RequestMethod.GET)
	public String surveyUpdateGet(Model model,
			@RequestParam(name="idx", defaultValue = "0", required = false) int idx) {
		model.addAttribute("idx", idx);
		model.addAttribute("vo", surveyService.getSurvey(idx));
		
		List<SurveyQuestionVO> questionVos = surveyService.getSurveyQuestions(idx);
		model.addAttribute("questionVos", questionVos);
		
		List<SurveyQuestionContentVO> answerVos = surveyService.getSurveyAnswers(idx, "", "");
		model.addAttribute("answerVos", answerVos);
		
		return "admin/survey/surveyUpdate";
	}
	
	@RequestMapping(value = "/surveyUpdate", method = RequestMethod.POST)
	public String surveyUpdatePost(SurveyVO vo) {
		if(vo.getShowSw() == null) vo.setShowSw("0");
		else vo.setShowSw("1");
		surveyService.setSurveyUpdate(vo);
		return "redirect:/message/surveyUpdateOk?idx=" + vo.getIdx();
	}
	
	@ResponseBody
	@RequestMapping(value = "/surveyAnswerInputOK", method = RequestMethod.POST)
	public String surveyAnswerInputOKPost(SurveyQuestionContentVO vo) {
		surveyService.setSurveyAnswerInput(vo);
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/surveyAnswerDeleteOK", method = RequestMethod.POST)
	public String surveyAnswerDeleteOKPost(int idx) {
		surveyService.setSurveyAnswerDelete(idx);
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/surveyQuestionDeleteOK", method = RequestMethod.POST)
	public String surveyQuestionDeleteOKPost(int idx) {
		surveyService.setSurveyQuestionDelete(idx);
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/surveyAnswerInput", method = RequestMethod.POST)
	public String surveyAnswerInputPost(SurveyQuestionVO vo) {
		surveyService.setSurveyQuestionInput(vo);
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/surveyQuestionUpdateOK", method = RequestMethod.POST)
	public String surveyQuestionUpdateOKPost(SurveyQuestionVO vo) {
		surveyService.setSurveyQuestionUpdate(vo);
		return "";
	}
	
	@RequestMapping(value = "/basicSurvey", method = RequestMethod.GET)
	public String basicSurveyGet(Model model, int idx) {
		SurveyVO vo = surveyService.getSurvey(idx);
		model.addAttribute("idx", idx);
		model.addAttribute("vo", vo);
		return "admin/survey/basicSurvey";
	}
	
	@RequestMapping(value = "/basicSurvey", method = RequestMethod.POST)
	public String basicSurveyPost(HttpSession session, SurveyBasicSurveyVO vo, int sIdx) {
		session.setAttribute("sBasicSurveyVO", vo);
		return "redirect:/survey/survey?sIdx="+sIdx;
	}
	
	@RequestMapping(value = "/survey", method = RequestMethod.GET)
	public String surveyGet(Model model, int sIdx) {
		SurveyVO vo = surveyService.getSurvey(sIdx);
		model.addAttribute("vo", vo);
		
		List<SurveyQuestionVO> questionVos = surveyService.getSurveyQuestions(sIdx);
		model.addAttribute("questionVos", questionVos);
		
		List<SurveyQuestionContentVO> answerVos = surveyService.getSurveyAnswers(sIdx, "", "");
		model.addAttribute("answerVos", answerVos);
		
		return "admin/survey/survey";
	}
	
	@Transactional
	@RequestMapping(value = "/survey", method = RequestMethod.POST)
	public String surveyPost(Model model, HttpServletRequest request, HttpSession session, SurveyRealAnswerVO vo, String answer) {
		String detailAnswer = vo.getDetailAnswer();
		SurveyBasicSurveyVO basicSurveyVo =  (SurveyBasicSurveyVO) session.getAttribute("sBasicSurveyVO");
		//System.out.println("basicSurveyVo : " + basicSurveyVo);
		//int bIdx = surveyService.setBasicSurveyInput(basicSurveyVo);
		surveyService.setBasicSurveyInput(basicSurveyVo);
		int bIdx = surveyService.getBasicSurveyIdx();
		System.out.println("vo : " + vo);
		System.out.println("bIdx : " + bIdx);
		List<SurveyQuestionVO> qvos = surveyService.getSurveyQuestions(vo.getSIdx());
		
		vo.setBIdx(bIdx);
		if(answer != null) {
			for(String ans : answer.split(",")) {
				
				 vo.setQIdx(Integer.parseInt(ans.split("/")[0])); 
				 vo.setAIdx(Integer.parseInt(ans.split("/")[1])); 
				 vo.setDetailAnswer("");
				 //System.out.println("1.vo:"+vo);
				 surveyService.setSurveyRealAnswerInput(vo);
			}
		}
		
		for(SurveyQuestionVO qvo : qvos) {
			if(qvo.getAnswerSw()==2) {
				int radioAnswer = Integer.parseInt(request.getParameter("radioAnswer"+qvo.getIdx()));
				vo.setQIdx(qvo.getIdx());
				vo.setAIdx(radioAnswer);
				vo.setDetailAnswer("");
				//System.out.println("2.vo:"+vo);
				surveyService.setSurveyRealAnswerInput(vo);
			}
		}
		
		for(String datail : detailAnswer.split(",")) {
			vo.setQIdx(Integer.parseInt(datail.split("/")[0]));
			vo.setDetailAnswer(datail.split("/")[1]);
			vo.setAIdx(0);
			if(vo.getQIdx()==0) continue;
			//System.out.println("3.vo:"+vo);
			surveyService.setSurveyRealAnswerInput(vo);
		}
		model.addAttribute("idx",vo.getSIdx());		
		
		return "admin/survey/surveySuccess";
	}
	
	@RequestMapping(value = "/surveyResult", method = RequestMethod.GET)
	public String surveyResultGet(int idx, Model model,
			@RequestParam(name="part",defaultValue = "") String part,
			@RequestParam(name="smallPart",defaultValue = "")String smallPart) {
		model.addAttribute("idx", idx);
	  model.addAttribute("vo",surveyService.getSurvey(idx)) ;
	  
  	List<SurveyQuestionVO> questionVos =  surveyService.getSurveyQuestions(idx);
  	model.addAttribute("questionVos", questionVos); 
  	
  	List<SurveyQuestionContentVO> answerVos = surveyService.getSurveyAnswers(idx,part,smallPart);
  	model.addAttribute("answerVos", answerVos); 
		
  	//선택지가 없는경우도 있다
  	List<SurveyRealAnswerVO> realAnswerVos = surveyService.getRealAnswer(idx,part,smallPart);
  	
  	model.addAttribute("realAnswerVos", realAnswerVos); 
  	model.addAttribute("part", part); 
  	model.addAttribute("smallPart", smallPart); 
		
  	if(!part.equals("")) {
	  	List<String> smalls = surveyService.getRealAnswerSmallPart(idx,part);
	  	model.addAttribute("smalls", smalls); 
  	}
  	
		return "admin/survey/surveyResult";
	}
	
	@ResponseBody
	@RequestMapping(value="/surveyGetSmallpart",method =RequestMethod.POST,produces="application/text; charset=utf8")
	public String surveyGetSmallPart(int idx, String part) {
		List<String> smalls = surveyService.getRealAnswerSmallPart(idx,part);
		
		String str ="";
		for(String small:smalls) {
			str+=small+"_";
		}
		
		if(smalls.size() == 0) return "0";
		else return str.substring(0, str.length()-1);
	}
}
