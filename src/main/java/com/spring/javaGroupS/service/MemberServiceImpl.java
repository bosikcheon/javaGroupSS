package com.spring.javaGroupS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	public List<MemberVO> getMemberList(int startIndexNo, int pageSize, String flag) {
		return memberDAO.getMemberList(startIndexNo, pageSize, flag);
	}

	@Override
	public int setMemberPwdCheckOk(String mid, String pwd) {
		return memberDAO.setMemberPwdCheckOk(mid, pwd);
	}

	@Override
	public int setMemberUpdateOk(MemberVO vo) {
		return memberDAO.setMemberUpdateOk(vo);
	}

	@Override
	public int setMemberDeleteCheck(String mid) {
		return memberDAO.setMemberDeleteCheck(mid);
	}

	@Override
	public int getTotRecCnt(String flag) {
		return memberDAO.getTotRecCnt(flag);
	}

	@Override
	public List<MemberVO> getMemberLevelList(int startIndexNo, int pageSize, int level) {
		return memberDAO.getMemberLevelList(startIndexNo, pageSize, level);
	}

	@Override
	public ArrayList<MemberVO> getMemberEmailCheck(String email) {
		return memberDAO.getMemberEmailCheck(email);
	}

	@Override
	public int getInforCheck(String mid, String email) {
		return memberDAO.getInforCheck(mid, email);
	}

	@Override
	public int getLevelCnt(int level) {
		return memberDAO.getLevelCnt(level);
	}

	@Override
	public MemberVO getMemberIdDuplicationCheck(String mid) {
		return memberDAO.getMemberIdDuplicationCheck(mid);
	}

	@Override
	public MemberVO getMemberNickNameEmailCheck(String nickName, String email) {
		return memberDAO.getMemberNickNameEmailCheck(nickName, email);
	}

	@Override
	public void setKakaoMemberInput(String mid, String nickName, String email, String pwd) {
		memberDAO.setKakaoMemberInput(mid, nickName, email, pwd);
	}

	@Override
	public String getFileUpload(MultipartFile fName, String mid) {
//		String oFileName = fName.getOriginalFilename();
		
		return null;
	}

}
