package com.spring.javaGroupS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaGroupS.pagination.PageProcess;
import com.spring.javaGroupS.pagination.PageVO;
import com.spring.javaGroupS.service.PdsService;
import com.spring.javaGroupS.vo.PdsVO;
import com.spring.javaGroupS.vo.ReviewVO;

@Controller
@RequestMapping("/pds")
public class PdsController {
	
	@Autowired
	PdsService pdsService;
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/pdsList", method = RequestMethod.GET)
	public String pdsListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "pds", part, "");
		
		List<PdsVO> vos = pdsService.getPdsList(pageVO.getStartIndexNo(), pageSize, part);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "pds/pdsList";
	}
	
	@RequestMapping(value = "/pdsInput", method = RequestMethod.GET)
	public String pdsInputGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part
		) {
		//model.addAttribute("part", part);
		return "pds/pdsInput";
	}
	
	// PDS(자료실) 업로드 처리
	// 싱글파일 업로드시는 : MultipartFile객체사용, 멀티파일 업로드시는 : MultipartHttpServletRequest객체 사용
	@RequestMapping(value = "/pdsInput", method = RequestMethod.POST)
	public String pdsInputPost(MultipartHttpServletRequest mFile, HttpServletRequest request, PdsVO vo) {
		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		int res = pdsService.setPdsInput(request, mFile, vo);
		
		if(res != 0) return "redirect:/message/pdsInputOk";
		return "redirect:/message/pdsInputNo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/pdsDownNumCheck", method = RequestMethod.POST)
	public String pdsDownNumCheckPost(int idx) {
		return pdsService.setPdsDownNumCheck(idx)+"";
	}
	
	// 게시글 삭제처리
	@ResponseBody
	@RequestMapping(value = "/pdsDeleteCheck", method = RequestMethod.POST)
	public String pdsDeleteCheckPost(HttpServletRequest request, int idx, String fSName) {
		return pdsService.setPdsDeleteCheck(request, idx, fSName)+"";
	}
	
  // 게시글 삭제처리(비밀번호 확인후 삭제처리)
	@ResponseBody
	@RequestMapping(value = "/pdsDeleteCheck2", method = RequestMethod.POST)
	public String pdsDeleteCheck2Post(HttpServletRequest request, int idx, String pwd, String fSName) {
		String res = "0";
		PdsVO vo = pdsService.getPdsContent(idx);
		if(passwordEncoder.matches(pwd, vo.getPwd())) {
			res = pdsService.setPdsDeleteCheck(request, idx, fSName) + "";
		}
		return res;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/pdsTotalDown", method = RequestMethod.GET)
	public String pdsTotalDownGet(HttpServletRequest request, int idx) throws IOException {
		// 다운로드수 증가하기
		pdsService.setPdsDownNumCheck(idx);
		
		// 여러개의 파일을 하나의 파일(zip)로 압축(통합)하여 다운로드 시켜준다. (제목.zip)
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		PdsVO vo = pdsService.getPdsContent(idx);
		
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");
		
		String zipPath = realPath + "temp/";
		String zipName = vo.getTitle() + ".zip";
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath + zipName));
		
		byte[] bytes = new byte[2048];
		
		for(int i=0; i<fNames.length; i++) {
			fis = new FileInputStream(realPath + fSNames[i]);
			fos = new FileOutputStream(zipPath + fNames[i]);
			
			// pds폴더의 파일을 temp폴더로 복사...
			int data = 0;
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {
				fos.write(bytes, 0, data);
			}
			fos.flush();
			fos.close();
			fis.close();
			
			// temp폴더로 복사된 파일을 zip파일에 담아주는 작업처리
			File copyFile = new File(zipPath + fNames[i]);
			fis = new FileInputStream(copyFile);
			zout.putNextEntry(new ZipEntry(fNames[i]));
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {
				zout.write(bytes, 0, data);
			}
			zout.flush();
			zout.closeEntry();
			fis.close();
		}
		zout.close();
		// 파일 압축작업이 완료되면 압축될 파일을 클라이언트로 전송처리한다.
		return "redirect:/fileDownAction?path=pds&file="+java.net.URLEncoder.encode(zipName);
	}
	
	@RequestMapping(value = "/pdsContent", method = RequestMethod.GET)
	public String pdsContentGet(Model model, int idx,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		PdsVO vo = pdsService.getPdsContent(idx);
		model.addAttribute("vo", vo);
		
		// 리뷰 가져오기
		List<ReviewVO> rVos = pdsService.getReviewList("pds", idx);
		model.addAttribute("rVos", rVos);
		
		// 리뷰 평점 구하기
		double reviewAvg = pdsService.getReviewAvg("pds", idx);
		model.addAttribute("reviewAvg", reviewAvg);	
		
		
		return "pds/pdsContent";
	}
	
	@ResponseBody
	@RequestMapping(value = "/reviewInputOk", method = RequestMethod.POST)
	public String reviewInputOkPost(ReviewVO vo) {
		return pdsService.setReviewInputOk(vo) + "";
	}
	
	// 리뷰삭제....
	@ResponseBody
	@RequestMapping(value = "/reviewDelete", method = RequestMethod.POST)
	public String reviewDeletePost(int idx) {
		return pdsService.setReviewDelete(idx) + "";
	}
	
	// 리뷰에 댓글 달기
	@ResponseBody
	@RequestMapping(value = "/reviewReplyInputOk", method = RequestMethod.POST)
	public String reviewDeletePost(ReviewVO vo) {
		return pdsService.setReviewReplyInputOk(vo) + "";
	}
	
	// 리뷰 댓글 삭제
	@ResponseBody
	@RequestMapping(value = "/reviewReplyDelete", method = RequestMethod.POST)
	public String reviewReplyDeletePost(int replyIdx) {
		return pdsService.setReviewReplyDelete(replyIdx) + "";
	}
	
}
