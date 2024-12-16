package com.spring.javaGroupS.service;

import java.util.List;

import com.spring.javaGroupS.vo.BoardVO;

public interface BoardService {

	int getTotRecCnt();

	List<BoardVO> getBoardList(int startIndexNo, int pageSize);

	int setBoardInput(BoardVO vo);

	BoardVO getBoardContent(int idx);

	void imgCheck(String content);

	void setReadNumPlus(int idx);

	List<BoardVO> getBoardListSearch(int startIndexNo, int pageSize, String search, String searchString);

	void imgDelete(String content);

	int setBoardDelete(int idx);

	void imgBackup(String content);

	int setBoardUpdateOk(BoardVO vo);

	int setBoardGood(int idx);

	int setBoardGood2(int idx, int goodCnt);

}
