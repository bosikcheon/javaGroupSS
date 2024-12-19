package com.spring.javaGroupS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javaGroupS.dao.BoardDAO;
import com.spring.javaGroupS.vo.BoardReply2VO;
import com.spring.javaGroupS.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;

	@Override
	public int getTotRecCnt() {
		return boardDAO.getTotRecCnt();
	}

	@Override
	public List<BoardVO> getBoardList(int startIndexNo, int pageSize) {
		return boardDAO.getBoardList(startIndexNo, pageSize);
	}

	@Override
	public int setBoardInput(BoardVO vo) {
		return boardDAO.setBoardInput(vo);
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		return boardDAO.getBoardContent(idx);
	}

	@Override
	public void imgCheck(String content) {
		//      0         1         2         3
		//      0123456789012345678901234567890123456
		// <img src="/javaGroupS/data/ckeditor/241213144906_throw.jpg" style
		// <img src="/javaGroupS/data/board/241213144906_throw.jpg" style
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 31;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		
		while(true) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "ckeditor/" + imgFile;
			String copyFilePath = realPath + "board/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) break;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	// 파일 복사처리
	private void fileCopyCheck(String origFilePath, String copyFilePath) {
		try {
//			File file = new File(origFilePath);
//			FileInputStream fis = new FileInputStream(file);
			FileInputStream fis = new FileInputStream(new File(origFilePath));
			FileOutputStream fos = new FileOutputStream(new File(copyFilePath));
			
			byte[] bytes = new byte[2048];
			int cnt = 0;
			while((cnt=fis.read(bytes)) != -1) {
				fos.write(bytes, 0, cnt);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setReadNumPlus(int idx) {
		boardDAO.setReadNumPlus(idx);
	}

	@Override
	public List<BoardVO> getBoardListSearch(int startIndexNo, int pageSize, String search, String searchString) {
		return boardDAO.getBoardListSearch(startIndexNo, pageSize, search, searchString);
	}

	@Override
	public void imgDelete(String content) {
		//      0         1         2         3
		//      0123456789012345678901234567890123456
		// <img src="/javaGroupS/data/board/241213144906_throw.jpg" style
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 28;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		
		while(true) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "board/" + imgFile;
			
			fileDelete(origFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) break;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	// 서버에 존재하는 파일을 삭제처리한다.
	private void fileDelete(String origFilePath) {
		File delFile = new File(origFilePath);
		if(delFile.exists()) delFile.delete();
	}

	@Override
	public int setBoardDelete(int idx) {
		return boardDAO.setBoardDelete(idx);
	}

	@Override
	public void imgBackup(String content) {
		//      0         1         2         3
		//      0123456789012345678901234567890123456
		// <img src="/javaGroupS/data/board/241213144906_throw.jpg" style
		// <img src="/javaGroupS/data/ckeditor/241213144906_throw.jpg" style
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 28;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		
		while(true) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "board/" + imgFile;
			String copyFilePath = realPath + "ckeditor/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) break;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	@Override
	public int setBoardUpdateOk(BoardVO vo) {
		return boardDAO.setBoardUpdateOk(vo);
	}

	@Override
	public int setBoardGood(int idx) {
		return boardDAO.setBoardGood(idx);
	}

	@Override
	public int setBoardGood2(int idx, int goodCnt) {
		return boardDAO.setBoardGood2(idx, goodCnt);
	}

	@Override
	public int getRecentCnt() {
		return boardDAO.getRecentCnt();
	}

	@Override
	public BoardVO getPreNextSearch(int idx, String str) {
		return boardDAO.getPreNextSearch(idx, str);
	}

	@Override
	public BoardReply2VO getBoardParentReplyCheck(int boardIdx) {
		return boardDAO.getBoardParentReplyCheck(boardIdx);
	}

	@Override
	public int setBoardReplyInput(BoardReply2VO replyVO) {
		return boardDAO.setBoardReplyInput(replyVO);
	}

	@Override
	public List<BoardReply2VO> getBoardReplyList(int idx) {
		return boardDAO.getBoardReplyList(idx);
	}

	@Override
	public void setReplyOrderUpdate(int boardIdx, int re_order) {
		boardDAO.setReplyOrderUpdate(boardIdx, re_order);
	}

	@Override
	public int setBoardReplyInputRe(BoardReply2VO replyVO) {
		return boardDAO.setBoardReplyInputRe(replyVO);
	}

	// 자식댓글 삭제시에 처리해야할것들...
	@Override
	public String setBoardReplyDelete(int idx, int ref, String nickName) {
		// 자신의 idx를 다른 댓글들의 ref필드가 가지고 있는지를 검색한다. 없다면 해당글을 삭제하면 된다.
		// 그러나 만약 있다면 자신의 댓글이 존재하기에 자신의 nickName필드에는 '-'을, content필드에는 '삭제된 댓글입니다.'를 넣어준다.
		// 또한 삭제시는 ref필드의 내용을 보고, ref변수 안의 번호가 자신외에 다른댓글의 ref변수안에도 있는지 찾아보고, 없다라면 자신이 참조하고 있는 댓글의 nickName이 '-'일 경우에는 함께 삭제할수 있도록 처리하여야 한다.(상위쪽도 모두 검색해서 삭제되었는지 체크해야함)

		String res = "0";
		
		int refCount = boardDAO.getIdxRefSearch(idx);	// 삭제하려는 댓글의 idx를 참조하고 있는 ref가 있는지 검색
		if(refCount != 0) {		// 자신을 참조하는 ref가 1개라도 있다면 자신은 삭제하지않고 '삭제된글'이라고 업데이트한다.
			boardDAO.setBoardReplyDeleteCheck(idx);
			res = "1";
		}
		else {	// 자신을 참조하는 글이 없다라면 자신댓글을 삭제하는데, 이때 자신댓글이 부모댓글이면 바로 삭제하고, 그렇지 않으면 자신의 ref가 가리키는 부모idx가 또 있는지 검색후 있다라면 자신은 바로 삭제한다. 그러나 없을때는 다시 부모idx의 내용이 '-'라면 부모의 댓글도 삭제해야 한다.
			if(ref == 0) boardDAO.setBoardReplyDelete(idx);	// 자신의 ref가 0이면 자신은 부모댓글이기에 바로 삭제하면 된다. 
			else {	// 자신 댓글이 부모댓글이 아니면?
				int sameRefCount = boardDAO.getBoardReplyRefSearch(ref);	// 자신이 참조하는 ref의 부모 idx를 다른 댓글이 참조하는지 검색한다.
				if(sameRefCount == 1) {	// 자신ref가 참조하는 부모idx가 자신밖에는 없다(1)라면은? 자신은 삭제하되, 부모idx의 nickName이 '-'인지 체크해서 삭제할지를 고민한다.
					boardDAO.setBoardReplyDelete(idx);
					while(ref != 0) {	//(자신ref가 0이면 부모댓글이다. 따라사 부모댓글아니면반복처리) 자신이 참조(ref)하는 부모댓글(idx)의 nickName이 '-'이면, 부모댓글도 삭제해야하는데, 이때 부모삭제후 부모ref 참조하는 또 부모댓글이 있는지 검색후 같은 내용을 반복한다.(ref=0 면 최종부모기에 작업끝낸다.)
						BoardReply2VO replyVO = boardDAO.getBoardReplyNickName(ref);
						if(replyVO != null && replyVO.getNickName().equals("-")) {	// 부모대글의 nickName이 '-'이라면 부모댓글지워야한다.
							boardDAO.setBoardReplyDelete(replyVO.getIdx());	// 부모댓글을 지운다.
							ref = replyVO.getRef(); // 다시 부모댓글이 참조하던 ref가 0이될때까지 앞의 작업을 반복한다.
							continue;
						}
						break;	// 부모댓글의 nickName이 '-'가 아니면 삭제할필요없기에 탈출한다.
					}
				}
				else boardDAO.setBoardReplyDelete(idx);	// 자신ref가 참조하는 부모idx가 또 있다고 한다면 자신은 바로 삭제시키면 된다.
			}
			res = "1";
		}
		return res;
	}

	@Override
	public int setBoardReplyUpdate(BoardReply2VO replyVO) {
		return boardDAO.setBoardReplyUpdate(replyVO);
	}
	
	
}
