package com.spring.javaGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaGroupS.vo.SurveyBasicSurveyVO;
import com.spring.javaGroupS.vo.SurveyQuestionContentVO;
import com.spring.javaGroupS.vo.SurveyQuestionVO;
import com.spring.javaGroupS.vo.SurveyRealAnswerVO;
import com.spring.javaGroupS.vo.SurveyVO;

public interface SurveyDAO {

	void setSurveyInput(@Param("vo") SurveyVO vo);

	int getLastIdx();

	List<SurveyVO> getSurveyList(@Param("showSw") int showSw);

	SurveyVO getSurvey(@Param("idx") int idx);

	List<SurveyQuestionVO> getSurveyQuestions(@Param("idx") int idx);

	List<SurveyQuestionContentVO> getSurveyAnswers(@Param("idx") int idx, @Param("part") String part, @Param("smallPart") String smallPart);

	void setSurveyUpdate(@Param("vo") SurveyVO vo);

	void setSurveyAnswerInput(@Param("vo") SurveyQuestionContentVO vo);

	void setSurveyQuestionDelete(@Param("idx") int idx);

	void setSurveyAnswerDelete(@Param("idx") int idx);

	void setSurveyQuestionInput(@Param("vo") SurveyQuestionVO vo);

	void setSurveyQuestionUpdate(@Param("vo") SurveyQuestionVO vo);

	int setBasicSurveyInput(@Param("vo") SurveyBasicSurveyVO basicSurveyVo);

	void setSurveyRealAnswerInput(@Param("vo") SurveyRealAnswerVO vo);

	int getBasicSurveyIdx();

	List<SurveyRealAnswerVO> getRealAnswer(@Param("idx") int idx, @Param("part") String part, @Param("smallPart") String smallPart);

	List<String> getRealAnswerSmallPart(@Param("idx") int idx, @Param("part") String part);

}
