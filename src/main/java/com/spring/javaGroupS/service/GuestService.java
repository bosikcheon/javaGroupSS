package com.spring.javaGroupS.service;

import java.util.List;

import com.spring.javaGroupS.vo.GuestVO;

public interface GuestService {

	List<GuestVO> getGuestList(int startIndexNo, int pageSize);

	int setGuestInput(GuestVO vo);

	int setGuestDeleteOk(int idx);

	int getTotRecCnt();

}
