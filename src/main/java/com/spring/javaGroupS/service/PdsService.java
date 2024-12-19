package com.spring.javaGroupS.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaGroupS.vo.PdsVO;
import com.spring.javaGroupS.vo.ReviewVO;

public interface PdsService {

	List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part);

	int setPdsInput(HttpServletRequest request, MultipartHttpServletRequest mFile, PdsVO vo);

	int setPdsDownNumCheck(int idx);

	int setPdsDeleteCheck(HttpServletRequest request, int idx, String fSName);

	PdsVO getPdsContent(int idx);

	int setReviewInputOk(ReviewVO vo);

	List<ReviewVO> getReviewList(String part, int idx);

	double getReviewAvg(String part, int idx);

	int setReviewDelete(int idx);

	int setReviewReplyInputOk(ReviewVO vo);

	int setReviewReplyDelete(int replyIdx);

}
