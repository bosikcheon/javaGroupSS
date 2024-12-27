package com.spring.javaGroupS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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

	ArrayList<MemberVO> getMemberEmailCheck(String email);

	int getInforCheck(String mid, String email);

	int getLevelCnt(int level);

	MemberVO getMemberIdDuplicationCheck(String mid);

	MemberVO getMemberNickNameEmailCheck(String nickName, String email);

	void setKakaoMemberInput(String mid, String nickName, String email, String pwd);

	String getFileUpload(MultipartFile fName, String mid);

}
