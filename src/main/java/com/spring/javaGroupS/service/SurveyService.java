package com.spring.javaGroupS.service;

import java.util.List;

import com.spring.javaGroupS.vo.SurveyBasicSurveyVO;
import com.spring.javaGroupS.vo.SurveyQuestionContentVO;
import com.spring.javaGroupS.vo.SurveyQuestionVO;
import com.spring.javaGroupS.vo.SurveyRealAnswerVO;
import com.spring.javaGroupS.vo.SurveyVO;

public interface SurveyService {

	void setSurveyInput(SurveyVO vo);

	int getLastIdx();

	List<SurveyVO> getSurveyList(int showSw);

	SurveyVO getSurvey(int idx);

	List<SurveyQuestionVO> getSurveyQuestions(int idx);

	List<SurveyQuestionContentVO> getSurveyAnswers(int idx, String part, String smallPart);

	void setSurveyUpdate(SurveyVO vo);

	void setSurveyAnswerInput(SurveyQuestionContentVO vo);

	void setSurveyQuestionDelete(int idx);

	void setSurveyAnswerDelete(int idx);

	void setSurveyQuestionInput(SurveyQuestionVO vo);

	void setSurveyQuestionUpdate(SurveyQuestionVO vo);

	int setBasicSurveyInput(SurveyBasicSurveyVO basicSurveyVo);

	void setSurveyRealAnswerInput(SurveyRealAnswerVO vo);

	int getBasicSurveyIdx();

	List<SurveyRealAnswerVO> getRealAnswer(int idx, String part, String smallPart);

	List<String> getRealAnswerSmallPart(int idx, String part);

}
