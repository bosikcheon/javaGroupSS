package com.spring.javaGroupS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.GuestDAO;
import com.spring.javaGroupS.vo.GuestVO;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	GuestDAO guestDAO;

	@Override
	public List<GuestVO> getGuestList(int startIndexNo, int pageSize) {
		return guestDAO.getGuestList(startIndexNo, pageSize);
	}

	@Override
	public int setGuestInput(GuestVO vo) {
		return guestDAO.setGuestInput(vo);
	}

	@Override
	public int setGuestDeleteOk(int idx) {
		return guestDAO.setGuestDeleteOk(idx);
	}

	@Override
	public int getTotRecCnt() {
		return guestDAO.getTotRecCnt();
	}
	
}
