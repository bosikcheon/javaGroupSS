package com.spring.javaGroupS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaGroupS.vo.ChartVO;
import com.spring.javaGroupS.vo.CrimeVO;
import com.spring.javaGroupS.vo.KakaoAddressVO;
import com.spring.javaGroupS.vo.TransactionVO;
import com.spring.javaGroupS.vo.User2VO;
import com.spring.javaGroupS.vo.UserVO;

public interface StudyDAO {

	void setTransactionUserInput(@Param("vo") UserVO vo);

	void setTransactionUser2Input(@Param("vo") UserVO vo);

	void setTransactionUserInput3(@Param("vo") UserVO vo);

	int setValidatorInput(@Param("vo") TransactionVO vo);

	void setTransactionUserInput4(@Param("vo") User2VO vo);

	KakaoAddressVO getKakaoAddressSearch(@Param("address") String address);

	int setKakaoAddressInput(@Param("vo") KakaoAddressVO vo);

	List<KakaoAddressVO> getKakaoAddressList();

	int setKakaoAddressDelete(@Param("address") String address);

	List<ChartVO> getMemberVisitCount();

	void setSaveCrimeData(@Param("vo") CrimeVO vo);

	int setDeleteCrimeData(@Param("year") int year);

	ArrayList<CrimeVO> getListCrimeData(@Param("year") int year);

	CrimeVO getYearPoliceCheck(@Param("year") int year, @Param("police") String police);

}
