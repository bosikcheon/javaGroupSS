package com.spring.javaGroupS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.spring.javaGroupS.vo.UserVO;

@Repository
public interface DbtestDAO {

	ArrayList<UserVO> getDbtestList();

	int setDbtestInputOk(@Param("vo") UserVO vo);

	UserVO getDbtestIdCheckForm(@Param("mid") String mid);

	int setDbtestDeleteOk(@Param("idx") int idx);

	int setDbtestUpdateOk(@Param("vo") UserVO vo);

	ArrayList<UserVO> getDbtestSearch(@Param("mid") String mid);

	ArrayList<String> getDbtestMidList();

}
