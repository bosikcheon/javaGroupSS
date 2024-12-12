package com.spring.javaGroupS.service;

import java.util.ArrayList;
import java.util.List;

import com.spring.javaGroupS.vo.MemberVO;

public interface MemberService {

	MemberVO getMemberIdCheck(String mid);

	void setMemberInforUpdate(String mid, int todayCnt);

	MemberVO getMemberNickNameCheck(String nickName);

	int setMemberJoinOk(MemberVO vo);

	ArrayList<MemberVO> getMemberTotalList();

	int getTotRecCnt(String flag);

	List<MemberVO> getMemberList(int startIndexNo, int pageSize, String flag);

	int setMemberPwdCheckOk(String mid, String pwd);

	int setMemberUpdateOk(MemberVO vo);

	int setMemberDeleteCheck(String mid);

	List<MemberVO> getMemberLevelList(int startIndexNo, int pageSize, int level);

}
