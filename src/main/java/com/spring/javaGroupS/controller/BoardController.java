package com.spring.javaGroupS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaGroupS.pagination.PageProcess;
import com.spring.javaGroupS.pagination.PageVO;
import com.spring.javaGroupS.service.BoardService;
import com.spring.javaGroupS.vo.BoardReply2VO;
import com.spring.javaGroupS.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PageProcess pageProcess;
	
	// 게시판 전체 리스트
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		/*
		int totRecCnt = boardService.getTotRecCnt();
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		List<BoardVO> vos = boardService.getBoardList(startIndexNo, pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totRecCnt", totRecCnt);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		return "board/boardList";
		*/
		
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", "", "");
		List<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(), pageSize);
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("vos", vos);
		
		return "board/boardList";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVO vo) {
		// 1.업로드시킨 그림파일은 '/data/ckeditor'폴더에 존재하기에, 실제 업로드되는 그림파일만 뽑아서 '/data/board'폴더로 옮겨준다.
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
		
		// 2.이미지 복사작업을 모두 마치면, 저장된 작업폴더의 위치인 ckeditor폴더를 board폴더로 변경처리한다.
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		
		// 3. 변경된 내용을 vo에 담은후 DB에 저장한다.
		int res = boardService.setBoardInput(vo);
		
		if(res != 0) return "redirect:/message/boardInputOk";
		return "redirect:/message/boardInputNo";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(Model model, HttpSession session, int idx,
		  @RequestParam(name="pag", defaultValue = "1", required = false) int pag,	
		  @RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize	
		) {
		
		// 조회수 증가시키기
		//boardService.setReadNumPlus(idx);
		// 게시글 조회수 1씩 증가(중복방지) - 세션 + 객체배열 처리
		ArrayList<String> contentNum = (ArrayList<String>) session.getAttribute("sContent");
		if(contentNum == null) contentNum = new ArrayList<String>();
		String imsiContentNum = "board" + idx;
		if(!contentNum.contains(imsiContentNum)) {
			boardService.setReadNumPlus(idx);
			contentNum.add(imsiContentNum);
		}
		session.setAttribute("sContent", contentNum);
		BoardVO vo = boardService.getBoardContent(idx);
		model.addAttribute("vo", vo);
		
		// 이전글 다음글처리
		BoardVO preVO = boardService.getPreNextSearch(idx, "preVO");
		BoardVO nextVO = boardService.getPreNextSearch(idx, "nextVO");
		model.addAttribute("preVO", preVO);
		model.addAttribute("nextVO", nextVO);
		
		// 댓글(대댓글) 추가 입력처리
		List<BoardReply2VO> replyVos = boardService.getBoardReplyList(idx);
		model.addAttribute("replyVos", replyVos);
		
		return "board/boardContent";
	}
	
	// 게시판 검색기 처리 리스트
	@RequestMapping(value = "/boardSearchList", method = RequestMethod.GET)
	public String boardSearchListGet(Model model, String search,
			@RequestParam(name="searchString", defaultValue = "", required = false) String searchString,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", search, searchString);
		List<BoardVO> vos = boardService.getBoardListSearch(pageVO.getStartIndexNo(), pageSize, search, searchString);
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("vos", vos);
		
		return "board/boardSearchList";
	}
	
	// 게시판 내용 삭제하기
	@RequestMapping(value = "/boardDelete", method = RequestMethod.GET)
	public String boardDeleteGet(int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		// 게시글에 사진이 존재한다면 서버에 저장된 사진을 먼저 삭제 처리한다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		// 사진 삭제작업이 끝나면 DB에 저장된 실제 정보레코드를 삭제처리한다.
		int res = boardService.setBoardDelete(idx);
		
		if(res != 0) return "redirect:/message/boardDeleteOk";
		return "redirect:/message/boardDeleteNo?idx="+idx+"&pag="+pag+"&pageSize="+pageSize;		
	}
	
	// 게시판 내용 수정하기 폼 보기
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(Model model, int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
			) {
		// 수정화면으로 이동할시는 기존 원본파일에 그림파일 존재했다면, 현재폴더(board)의 그림파일을 ckeditor폴더에 복사처리한다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgBackup(vo.getContent());
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/boardUpdate";
	}
	
	// 게시판 내용 수정 처리하기(그림파일 + DB자료)
	@RequestMapping(value = "/boardUpdateOk", method = RequestMethod.POST)
	public String boardUpdatePost(Model model, BoardVO vo,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
			) {
		// 수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요가 없다.
		// DB에 저장된 원본자료를 불러와서 현재 vo에 담긴 내용(content)과 비교한다.
		BoardVO origVO = boardService.getBoardContent(vo.getIdx());
		int res = 0;
		if(!origVO.getContent().equals(vo.getContent())) {
			// 1.기존 board폴더에 그림이 존재했다면 원본그림을 먼저 삭제처리한다. 업로드시킨 그림파일은 '/data/ckeditor'폴더에 존재하기에, 실제 업로드되는 그림파일만 뽑아서 '/data/board'폴더로 옮겨준다.
			if(origVO.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(origVO.getContent());
			
			// 2.이미지 복사작업을 모두 마치면, 저장된 작업폴더의 위치인 ckeditor폴더를 board폴더로 변경처리한다.
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/"));
			
			// 3.수정된 파일에 그림이 존재한다면 ckeditor에 존재하는 그림파일을 board폴더로 복사한다.
			if(vo.getContent().indexOf("src=\"/") != -1) {
				boardService.imgCheck(vo.getContent());
				vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
			}
			
		}
		// 4. 변경된 내용을 vo에 담은후 DB에 갱신한다.
		res = boardService.setBoardUpdateOk(vo);
		
		model.addAttribute("idx", vo.getIdx());
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		if(res != 0) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/boardGoodCheck", method = RequestMethod.POST)
	public String boardGoodCheckPost(int idx) {
		return boardService.setBoardGood(idx) + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/boardGoodCheck2", method = RequestMethod.POST)
	public String boardGoodCheck2Post(int idx, int goodCnt) {
		return boardService.setBoardGood2(idx, goodCnt) + "";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/boardGoodCheck3", method = RequestMethod.POST)
	public String boardGoodCheck3Post(HttpSession session, int idx) {
		String res = "0";
		ArrayList<String> goodNum = (ArrayList<String>) session.getAttribute("sGood");
		if(goodNum == null) goodNum = new ArrayList<String>();
		String imsiGoodNum = "boardGood" + idx;
		if(!goodNum.contains(imsiGoodNum)) {
			boardService.setBoardGood(idx);
			goodNum.add(imsiGoodNum);
			res = "1";
		}
		session.setAttribute("sGood", goodNum);
		
		return res;
	}
	
	// 부모댓글 입력처리
	@ResponseBody
	@RequestMapping(value = "/boardReplyInput", method = RequestMethod.POST)
	public String boardReplyInputPost(BoardReply2VO replyVO) {
		// 부모댓글의 경우는 ref=0, re_step=0, 처음 re_order=1로 처리.(단, 2번째 이상부터는 마지막댓글의 re_order+1로 처리)
		BoardReply2VO replyParentVO = boardService.getBoardParentReplyCheck(replyVO.getBoardIdx());
		
		replyVO.setRef(0);
		replyVO.setRe_step(0);
		
		if(replyParentVO == null) replyVO.setRe_order(1);
		else replyVO.setRe_order(replyParentVO.getRe_order()+1);
		
		return boardService.setBoardReplyInput(replyVO) + "";
	}
	
	// 대댓글(부모댓글, 부모댓글 답변글) 입력처리
	@ResponseBody
	@RequestMapping(value = "/boardReplyInputRe", method = RequestMethod.POST)
	public String boardReplyInputRePost(BoardReply2VO replyVO) {
		// 대댓글(부모댓글의 댓글/답변글)의 경우는 ref는 부모댓글의 idx를, re_step=re_step+1, re_order는 부모의 re_order보다 큰 댓글은 모두 re_order+1로 처리, 그리고 자신은 부모의 re_order+1 처리한다.
		
		//replyVO.setRef(replyVO.getRef());
		replyVO.setRe_step(replyVO.getRe_step() + 1);
		
		boardService.setReplyOrderUpdate(replyVO.getBoardIdx(), replyVO.getRe_order());

		replyVO.setRe_order(replyVO.getRe_order() + 1);
		
		return boardService.setBoardReplyInput(replyVO) + "";
	}
	
	// 대댓글 삭제....
	@ResponseBody
	@RequestMapping(value = "/boardReplyDelete", method = RequestMethod.POST)
	public String boardReplyDeletePost(int idx, int ref, String nickName) {
		// 자신의 idx를 다른 댓글들의 ref필드가 가지고 있는지를 검색한다. 없다면 해당글을 삭제하면 된다. 그러나 만약 있다면 자신의 댓글이 존재하기에 자신의 nickName필드에는 '-'을, content필드에는 '삭제된 댓글입니다.'를 넣어준다.
		// 또한 삭제시는 ref필드의 내용을 보고, ref변수 안의 번호가 자신외에 다른댓글의 ref변수안에도 있는지 찾아보고, 없다라면 자신이 참조하고 있는 댓글의 nickName이 '-'일 경우에는 함께 삭제할수 있도록 처리하여야 한다.(상위쪽도 모두 검색해서 삭제되었는지 체크해야함)
		// 앞의 조건에 대한 처리는 모두 서비스단에서 처리하도록 하자.
		return boardService.setBoardReplyDelete(idx, ref, nickName);
	}

	// 대댓글 수정처리
	@ResponseBody
	@RequestMapping(value = "/boardReplyUpdate", method = RequestMethod.POST)
	public String boardReplyUpdatePost(BoardReply2VO replyVO) {
		return boardService.setBoardReplyUpdate(replyVO) + "";
	}
	
}
