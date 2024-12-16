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

	int getTotRecCnt(String flag);

	List<MemberVO> getMemberList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("flag") String flag);

	int setMemberPwdCheckOk(@Param("mid") String mid, @Param("pwd") String pwd);

	int setMemberUpdateOk(@Param("vo") MemberVO vo);

	int setMemberDeleteCheck(@Param("mid") String mid);

	List<MemberVO> getMemberLevelList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("level") int level);

	ArrayList<MemberVO> getMemberEmailCheck(@Param("email") String email);

	int getInforCheck(@Param("mid") String mid, @Param("email") String email);

}
