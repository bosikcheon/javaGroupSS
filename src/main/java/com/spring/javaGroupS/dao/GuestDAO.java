package com.spring.javaGroupS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaGroupS.vo.GuestVO;

public interface GuestDAO {

	List<GuestVO> getGuestList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	int setGuestInput(GuestVO vo);

	int setGuestDeleteOk(int idx);

	int getTotRecCnt();

	ArrayList<GuestVO> getGuestMidList(@Param("nickName") String nickName);

	int getRecentCnt();

}
