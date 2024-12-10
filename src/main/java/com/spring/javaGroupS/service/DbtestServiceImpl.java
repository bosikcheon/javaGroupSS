package com.spring.javaGroupS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.DbtestDAO;
import com.spring.javaGroupS.vo.UserVO;

@Service
public class DbtestServiceImpl implements DbtestService {

	@Autowired
	DbtestDAO dbtestDAO;

	@Override
	public ArrayList<UserVO> getDbtestList() {
		return dbtestDAO.getDbtestList();
	}

	@Override
	public int setDbtestInputOk(UserVO vo) {
		return dbtestDAO.setDbtestInputOk(vo);
	}

	@Override
	public UserVO getDbtestIdCheckForm(String mid) {
		return dbtestDAO.getDbtestIdCheckForm(mid);
	}

	@Override
	public int setDbtestDeleteOk(int idx) {
		return dbtestDAO.setDbtestDeleteOk(idx);
	}

	@Override
	public int setDbtestUpdateOk(UserVO vo) {
		return dbtestDAO.setDbtestUpdateOk(vo);
	}

	@Override
	public ArrayList<UserVO> getDbtestSearch(String mid) {
		return dbtestDAO.getDbtestSearch(mid);
	}

	@Override
	public ArrayList<String> getDbtestMidList() {
		return dbtestDAO.getDbtestMidList();
	}
	
	
}
