package com.spring.javaGroupS.service;

import java.util.List;

import com.spring.javaGroupS.vo.UserVO;

public interface UserService {

	List<UserVO> getUserList();

	int setUserDeleteOk(int idx);

	UserVO getUserIdxSearch(int idx);

	int getUserUpdateOk(UserVO vo);

	UserVO getUserIdSearch(String mid);

	int setUserInputOk(UserVO vo);

	int getUserCnt();

	List<UserVO> getUserSearchList(String mid);

}
