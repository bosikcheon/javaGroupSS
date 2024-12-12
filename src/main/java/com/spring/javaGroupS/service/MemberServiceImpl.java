package com.spring.javaGroupS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.MemberDAO;
import com.spring.javaGroupS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
		return memberDAO.getMemberIdCheck(mid);
	}

	@Override
	public void setMemberInforUpdate(String mid, int todayCnt) {
		memberDAO.setMemberInforUpdate(mid, todayCnt);
	}

	@Override
	public MemberVO getMemberNickNameCheck(String nickName) {
		return memberDAO.getMemberNickNameCheck(nickName);
	}

	@Override
	public int setMemberJoinOk(MemberVO vo) {
		return memberDAO.setMemberJoinOk(vo);
	}

	@Override
	public ArrayList<MemberVO> getMemberTotalList() {
		return memberDAO.getMemberTotalList();
	}

	@Override
	public int getTotRecCnt() {
		return memberDAO.getTotRecCnt();
	}

	@Override
	public List<MemberVO> getMemberList(int startIndexNo, int pageSize) {
		return memberDAO.getMemberList(startIndexNo, pageSize);
	}

}
