package com.spring.javaGroupS.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaGroupS.dao.PdsDAO;
import com.spring.javaGroupS.vo.PdsVO;
import com.spring.javaGroupS.vo.ReviewVO;

@Service
public class PdsServiceImpl implements PdsService {

	@Autowired
	PdsDAO pdsDAO;

	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		return pdsDAO.getPdsList(startIndexNo, pageSize, part);
	}

	@Override
	public int setPdsInput(HttpServletRequest request, MultipartHttpServletRequest mFile, PdsVO vo) {
		try {
			// getFiles(변수명) : 변수명을 form태그안의 file속성의 변수명과 일치시켜준다.
			List<MultipartFile> fileList = mFile.getFiles("file");
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			// 멀티파일로 업로드시에는 다시 싱글파일로 하나씩 서버에 저장시켜줘야 한다.(MultipartHttpServletRequest -> MultipartFile)
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = RandomStringUtils.randomAlphanumeric(4) + "_" + oFileName;
				
				writeFile(file, sFileName);
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			
			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
			vo.setFSize(fileSizes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdsDAO.setPdsInput(vo);
	}
	
	private void writeFile(MultipartFile fName, String sFileName) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		
		if(fName.getBytes().length != -1) {
			fos.write(fName.getBytes());
		}
		fos.flush();
		fos.close();
	}

	@Override
	public int setPdsDownNumCheck(int idx) {
		return pdsDAO.setPdsDownNumCheck(idx);
	}

	@Override
	public int setPdsDeleteCheck(HttpServletRequest request, int idx, String fSName) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		String[] fSNames = fSName.split("/");
		
		// 서버에 저장된 실제파일을 삭제처리
		for(int i=0; i<fSNames.length; i++) {
//			File file = new File(realPath + fSNames[i]);
//			file.delete();
			new File(realPath + fSNames[i]).delete();
		}
		
		return pdsDAO.setPdsDeleteCheck(idx);
	}

	@Override
	public PdsVO getPdsContent(int idx) {
		return pdsDAO.getPdsContent(idx);
	}

	@Override
	public int setReviewInputOk(ReviewVO vo) {
		return pdsDAO.setReviewInputOk(vo);
	}

	@Override
	public List<ReviewVO> getReviewList(String part, int idx) {
		return pdsDAO.getReviewList(part, idx);
	}

	@Override
	public double getReviewAvg(String part, int idx) {
		return pdsDAO.getReviewAvg(part, idx);
	}

	@Override
	public int setReviewDelete(int idx) {
		return pdsDAO.setReviewDelete(idx);
	}

	@Override
	public int setReviewReplyInputOk(ReviewVO vo) {
		return pdsDAO.setReviewReplyInputOk(vo);
	}

	@Override
	public int setReviewReplyDelete(int replyIdx) {
		return pdsDAO.setReviewReplyDelete(replyIdx);
	}
}
