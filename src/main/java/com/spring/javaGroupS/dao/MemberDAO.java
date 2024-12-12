package com.spring.javaGroupS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaGroupS.vo.MemberVO;

public interface MemberDAO {

	MemberVO getMemberIdCheck(@Param("mid") String mid);

	void setMemberInforUpdate(@Param("mid") String mid, @Param("todayCnt") int todayCnt);

	MemberVO getMemberNickNameCheck(@Param("nickName") String nickName);

	int setMemberJoinOk(@Param("vo") MemberVO vo);

	ArrayList<MemberVO> getMemberTotalList();

	int getTotRecCnt();

	List<MemberVO> getMemberList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

}
