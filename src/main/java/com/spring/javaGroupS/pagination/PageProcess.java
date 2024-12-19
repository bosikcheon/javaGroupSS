package com.spring.javaGroupS.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.BoardDAO;
import com.spring.javaGroupS.dao.PdsDAO;

@Service
public class PageProcess {
	
	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	PdsDAO pdsDAO;

	public PageVO totRecCnt(int pag, int pageSize, String section, String part, String searchString) {
		PageVO pageVO = new PageVO();
		
		int totRecCnt = 0;
		String search = "";
		
		if(section.equals("board")) {
			if(part.equals("")) totRecCnt = boardDAO.getTotRecCnt();
			else {
				search = part;
				totRecCnt = boardDAO.getTotRecCntSearch(search, searchString);
			}
		}
		else if(section.equals("member")) {
			
		}
		else if(section.equals("admin_board")) {
			totRecCnt = boardDAO.getTotRecCnt();
		}
		else if(section.equals("pds")) {
			totRecCnt = pdsDAO.getTotRecCnt(part);
		}
		
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setTotRecCnt(totRecCnt);
		pageVO.setTotPage(totPage);
		pageVO.setStartIndexNo(startIndexNo);
		pageVO.setCurScrStartNo(curScrStartNo);
		pageVO.setBlockSize(blockSize);
		pageVO.setCurBlock(curBlock);
		pageVO.setLastBlock(lastBlock);
		
		pageVO.setSearch(search);
		pageVO.setSearchString(searchString);

		pageVO.setPart(part);
		
		// 검색기에서 search와 searchString를 처리할때 사용.
		String searchTitle = "";
		if(!searchString.equals("")) {
			switch (part) {
				case "title": searchTitle="제목" ;	break;
				case "nickName": searchTitle="글쓴이" ;	break;
				case "content": searchTitle="글내용" ;	break;
			}
			pageVO.setSearchTitle(searchTitle);
		}
		
		return pageVO;
	}
	
}
