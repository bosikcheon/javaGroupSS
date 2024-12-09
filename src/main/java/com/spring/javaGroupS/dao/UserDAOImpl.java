package com.spring.javaGroupS.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.javaGroupS.vo.UserVO;

//@Component
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<UserVO> getUserList() {
		List<UserVO> vos = sqlSession.selectList("userNS.getUserList");
		return vos;
	}

	@Override
	public int setUserDeleteOk(int idx) {
		return sqlSession.delete("userNS.setUserDeleteOk", idx);
	}

	@Override
	public UserVO getUserIdxSearch(int idx) {
		UserVO vo = sqlSession.selectOne("userNS.getUserIdxSearch", idx);
		return vo;
	}

	@Override
	public int getUserUpdateOk(UserVO vo) {
		return sqlSession.update("userNS.getUserUpdateOk", vo);
	}

	@Override
	public UserVO getUserIdSearch(String mid) {
		UserVO vo = sqlSession.selectOne("userNS.getUserIdSearch", mid);
		return vo;
	}

	@Override
	public int setUserInputOk(UserVO vo) {
		return sqlSession.insert("userNS.setUserInputOk", vo);
	}

	@Override
	public int getUserCnt() {
		int cnt = sqlSession.selectOne("userNS.getUserCnt");
		return cnt;
	}

	@Override
	public List<UserVO> getUserSearchList(String mid) {
		List<UserVO> vos = sqlSession.selectList("userNS.getUserSearchList", mid);
		return vos;
	}


}
