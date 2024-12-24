package com.spring.javaGroupS.service;

import java.util.List;

import com.spring.javaGroupS.vo.WebMessageVO;

public interface WebMessageService {

	List<WebMessageVO> getWmMessageList(String mid, int mSw, int startIndexNo, int pageSize);

	WebMessageVO getWmMessageContent(int idx, String mid);

	int setWmInputOk(WebMessageVO vo);

	int setWebDeleteCheck(int idx, int mFlag);

	int setWmDeleteAll(String mid);
	
}
