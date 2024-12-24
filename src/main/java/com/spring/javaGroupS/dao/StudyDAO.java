package com.spring.javaGroupS.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javaGroupS.vo.TransactionVO;
import com.spring.javaGroupS.vo.User2VO;
import com.spring.javaGroupS.vo.UserVO;

public interface StudyDAO {

	void setTransactionUserInput(@Param("vo") UserVO vo);

	void setTransactionUser2Input(@Param("vo") UserVO vo);

	void setTransactionUserInput3(@Param("vo") UserVO vo);

	int setValidatorInput(@Param("vo") TransactionVO vo);

	void setTransactionUserInput4(@Param("vo") User2VO vo);

}
