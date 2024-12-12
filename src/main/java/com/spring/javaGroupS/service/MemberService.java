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

	int getTotRecCnt();

	List<MemberVO> getMemberList(int startIndexNo, int pageSize);

}
