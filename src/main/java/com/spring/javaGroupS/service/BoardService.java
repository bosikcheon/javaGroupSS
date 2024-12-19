package com.spring.javaGroupS.service;

import java.util.List;

import com.spring.javaGroupS.vo.BoardReply2VO;
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

	int getRecentCnt();

	BoardVO getPreNextSearch(int idx, String str);

	BoardReply2VO getBoardParentReplyCheck(int boardIdx);

	int setBoardReplyInput(BoardReply2VO replyVO);

	List<BoardReply2VO> getBoardReplyList(int idx);

	void setReplyOrderUpdate(int boardIdx, int re_order);

	int setBoardReplyInputRe(BoardReply2VO replyVO);

	String setBoardReplyDelete(int idx, int ref, String nickName);

	int setBoardReplyUpdate(BoardReply2VO replyVO);

}
