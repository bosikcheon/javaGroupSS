package com.spring.javaGroupS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.SurveyDAO;
import com.spring.javaGroupS.vo.SurveyBasicSurveyVO;
import com.spring.javaGroupS.vo.SurveyQuestionContentVO;
import com.spring.javaGroupS.vo.SurveyQuestionVO;
import com.spring.javaGroupS.vo.SurveyRealAnswerVO;
import com.spring.javaGroupS.vo.SurveyVO;

@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	SurveyDAO surveyDAO;

	@Override
	public void setSurveyInput(SurveyVO vo) {
		surveyDAO.setSurveyInput(vo);
	}

	@Override
	public int getLastIdx() {
		return surveyDAO.getLastIdx();
	}

	@Override
	public List<SurveyVO> getSurveyList(int showSw) {
		return surveyDAO.getSurveyList(showSw);
	}

	@Override
	public SurveyVO getSurvey(int idx) {
		return surveyDAO.getSurvey(idx);
	}

	@Override
	public List<SurveyQuestionVO> getSurveyQuestions(int idx) {
		return surveyDAO.getSurveyQuestions(idx);
	}

	@Override
	public List<SurveyQuestionContentVO> getSurveyAnswers(int idx, String part, String smallPart) {
		return surveyDAO.getSurveyAnswers(idx, part, smallPart);
	}

	@Override
	public void setSurveyUpdate(SurveyVO vo) {
		surveyDAO.setSurveyUpdate(vo);
	}

	@Override
	public void setSurveyAnswerInput(SurveyQuestionContentVO vo) {
		surveyDAO.setSurveyAnswerInput(vo);
	}

	@Override
	public void setSurveyQuestionDelete(int idx) {
		surveyDAO.setSurveyQuestionDelete(idx);
	}

	@Override
	public void setSurveyAnswerDelete(int idx) {
		surveyDAO.setSurveyAnswerDelete(idx);
	}

	@Override
	public void setSurveyQuestionInput(SurveyQuestionVO vo) {
		surveyDAO.setSurveyQuestionInput(vo);
	}

	@Override
	public void setSurveyQuestionUpdate(SurveyQuestionVO vo) {
		surveyDAO.setSurveyQuestionUpdate(vo);
	}

	@Override
	public int setBasicSurveyInput(SurveyBasicSurveyVO basicSurveyVo) {
		return surveyDAO.setBasicSurveyInput(basicSurveyVo);
	}

	@Override
	public void setSurveyRealAnswerInput(SurveyRealAnswerVO vo) {
		surveyDAO.setSurveyRealAnswerInput(vo);
	}

	@Override
	public int getBasicSurveyIdx() {
		return surveyDAO.getBasicSurveyIdx();
	}

	@Override
	public List<SurveyRealAnswerVO> getRealAnswer(int idx, String part, String smallPart) {
		return surveyDAO.getRealAnswer(idx, part, smallPart);
	}

	@Override
	public List<String> getRealAnswerSmallPart(int idx, String part) {
		return surveyDAO.getRealAnswerSmallPart(idx, part);
	}
	
}
