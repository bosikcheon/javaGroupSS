package com.spring.javaGroupS.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaGroupS.vo.ChartVO;
import com.spring.javaGroupS.vo.CrimeVO;
import com.spring.javaGroupS.vo.KakaoAddressVO;
import com.spring.javaGroupS.vo.QrcodeVO;
import com.spring.javaGroupS.vo.TransactionVO;
import com.spring.javaGroupS.vo.User2VO;
import com.spring.javaGroupS.vo.UserVO;

public interface StudyService {

	String[] getCityStringArray(String dodo);

	ArrayList<String> getCityStringArrayList(String dodo);

	int fileUpload(MultipartFile fName, String mid);

	int multiFileUpload(MultipartHttpServletRequest mFile, HttpServletRequest request);

	void setTransactionUserInput(UserVO vo);

	void setTransactionUser2Input(UserVO vo);

	void setTransactionUserInput2(UserVO vo);

	void setTransactionUserInput3(UserVO vo);

	int setValidatorInput(TransactionVO vo);

	void setTransactionUserInput4(User2VO vo);

	String setThumbnailCreate(MultipartFile file, HttpServletRequest request);

	KakaoAddressVO getKakaoAddressSearch(String address);

	int setKakaoAddressInput(KakaoAddressVO vo);

	List<KakaoAddressVO> getKakaoAddressList();

	int setKakaoAddressDelete(String address);

	List<ChartVO> getMemberVisitCount();

	void setSaveCrimeData(CrimeVO vo);

	int setDeleteCrimeData(int year);

	ArrayList<CrimeVO> getListCrimeData(int year);

	CrimeVO getYearPoliceCheck(int year, String police);

	String setQrcodeCreate(String realPath);

	String setqrcodeCreate1(String realPath, QrcodeVO vo);

	String setqrcodeCreate2(String realPath, QrcodeVO vo);

	String setqrcodeCreate3(String realPath, QrcodeVO vo);

}
