package com.spring.javaGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaGroupS.vo.PdsVO;
import com.spring.javaGroupS.vo.ReviewVO;

public interface PdsDAO {

	int getTotRecCnt(@Param("part") String part);

	List<PdsVO> getPdsList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("part") String part);

	int setPdsInput(@Param("vo") PdsVO vo);

	int setPdsDownNumCheck(@Param("idx") int idx);

	int setPdsDeleteCheck(@Param("idx") int idx);

	PdsVO getPdsContent(@Param("idx") int idx);

	int setReviewInputOk(@Param("vo") ReviewVO vo);

	List<ReviewVO> getReviewList(@Param("part") String part, @Param("idx") int idx);

	double getReviewAvg(@Param("part") String part, @Param("idx") int idx);

	int setReviewDelete(@Param("idx") int idx);

	int setReviewReplyInputOk(@Param("vo") ReviewVO vo);

	int setReviewReplyDelete(@Param("replyIdx") int replyIdx);
	
}
