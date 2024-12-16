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
	
	
}
