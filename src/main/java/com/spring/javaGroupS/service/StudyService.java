package com.spring.javaGroupS.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

}
