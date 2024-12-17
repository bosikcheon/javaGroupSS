package com.spring.javaGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaGroupS.vo.BoardReply2VO;
import com.spring.javaGroupS.vo.BoardVO;

public interface BoardDAO {

	int getTotRecCnt();

	List<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	int setBoardInput(@Param("vo") BoardVO vo);

	BoardVO getBoardContent(@Param("idx") int idx);

	void setReadNumPlus(@Param("idx") int idx);

	int getTotRecCntSearch(@Param("search") String search, @Param("searchString") String searchString);

	List<BoardVO> getBoardListSearch(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("search") String search, @Param("searchString") String searchString);

	int setBoardDelete(@Param("idx") int idx);

	int setBoardUpdateOk(@Param("vo") BoardVO vo);

	int setBoardGood(@Param("idx") int idx);

	int setBoardGood2(@Param("idx") int idx, @Param("goodCnt") int goodCnt);

	int getRecentCnt();

	BoardVO getPreNextSearch(@Param("idx") int idx, @Param("str") String str);

	BoardReply2VO getBoardParentReplyCheck(@Param("boardIdx") int boardIdx);

	int setBoardReplyInput(@Param("replyVO") BoardReply2VO replyVO);

	List<BoardReply2VO> getBoardReplyList(@Param("idx") int idx);

	void setReplyOrderUpdate(@Param("boardIdx") int boardIdx, @Param("re_order") int re_order);

	int setBoardReplyInputRe(@Param("replyVO") BoardReply2VO replyVO);

	int setBoardReplyDelete(@Param("idx") int idx);

}
