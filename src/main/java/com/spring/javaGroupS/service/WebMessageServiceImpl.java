package com.spring.javaGroupS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.WebMessageDAO;
import com.spring.javaGroupS.vo.WebMessageVO;

@Service
public class WebMessageServiceImpl implements WebMessageService {
	
	@Autowired
	WebMessageDAO webMessageDAO;

	@Override
	public List<WebMessageVO> getWmMessageList(String mid, int mSw, int startIndexNo, int pageSize) {
		return webMessageDAO.getWmMessageList(mid, mSw, startIndexNo, pageSize);
	}

	@Override
	public WebMessageVO getWmMessageContent(int idx, String mid) {
		String receiveSw = webMessageDAO.getWmMessageReceiveSw(idx);
		if(receiveSw.equals("n")) webMessageDAO.setWmUpdate(idx, mid);
		
		return webMessageDAO.getWmMessageContent(idx, mid);
	}

	@Override
	public int setWmInputOk(WebMessageVO vo) {
		return webMessageDAO.setWmInputOk(vo);
	}

	@Override
	public int setWebDeleteCheck(int idx, int mFlag) {
		return webMessageDAO.setWebDeleteCheck(idx, mFlag);
	}

	@Override
	public int setWmDeleteAll(String mid) {
		int res = webMessageDAO.setWmDeleteAll(mid);	// update처리
		if(res != 0) webMessageDAO.setWmCompeleteDelete();	// delete처리
		return res;
	}

}
