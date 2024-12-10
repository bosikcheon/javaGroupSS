package com.spring.javaGroupS.service;

import java.util.ArrayList;

import com.spring.javaGroupS.vo.UserVO;

public interface DbtestService {

	ArrayList<UserVO> getDbtestList();

	int setDbtestInputOk(UserVO vo);

	UserVO getDbtestIdCheckForm(String mid);

	int setDbtestDeleteOk(int idx);

	int setDbtestUpdateOk(UserVO vo);

	ArrayList<UserVO> getDbtestSearch(String mid);

	ArrayList<String> getDbtestMidList();

}
