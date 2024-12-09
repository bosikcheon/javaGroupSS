package com.spring.javaGroupS.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.javaGroupS.vo.UserVO;

//@Repository
public interface UserDAO {

	List<UserVO> getUserList();

	int setUserDeleteOk(int idx);

	UserVO getUserIdxSearch(int idx);

	int getUserUpdateOk(UserVO vo);

	UserVO getUserIdSearch(String mid);

	int setUserInputOk(UserVO vo);

	int getUserCnt();

	List<UserVO> getUserSearchList(String mid);

}
