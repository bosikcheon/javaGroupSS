package com.spring.javaGroupS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaGroupS.service.SurveyService;
import com.spring.javaGroupS.vo.SurveyVO;

@Controller
public class HomeController {
	
	@Autowired
	SurveyService surveyService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/","/h","/index"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		// 첫화면에 설문지 문항 띄우기
		List<SurveyVO> surveyVos = surveyService.getSurveyList(1);
		model.addAttribute("surveyVos", surveyVos);
		
		return "home";
	}
	
	@RequestMapping("/imageUpload")
	public void imageUpload(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		String oFileName = upload.getOriginalFilename();
		
		// 파일명 중복방지를 위한 이름 설정
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date) + "_" + oFileName;
		
		// 파일 서버로 업로드처리
		byte[] bytes = upload.getBytes();
//		File file = new File(realPath + oFileName);
//		FileOutputStream fos = new FileOutputStream(file);
		FileOutputStream fos = new FileOutputStream(new File(realPath + oFileName));
		fos.write(bytes);
		
		// 브라우저에 서버에 전송된 그림을 표시시켜주기
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath() + "/data/ckeditor/" + oFileName;
		out.println("{\"originalFilename\":\""+oFileName+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");
		
		out.flush();
		fos.close();
	}
	
	// data폴더 아래쪽의 '폴더명/파일명'들을 다운받고자 할대 수행하는 메소드
	@RequestMapping(value = "/fileDownAction", method = RequestMethod.GET)
	public void fileDownActionGet(HttpServletRequest request, HttpServletResponse response, String path, String file) throws IOException {
		if(path.equals("pds")) path += "/temp/";
		
		String realPathFile = request.getSession().getServletContext().getRealPath("/resources/data/" + path) + file;
		
		File downFile = new File(realPathFile);
		
		String downFileName = new String(file.getBytes("UTF-8"), "8859_1");
		response.setHeader("Content-Disposition", "attachment;filename=" + downFileName);
		
		FileInputStream fis = new FileInputStream(downFile);
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] bytes = new byte[2048];
		int data;
		while((data = fis.read(bytes, 0, bytes.length)) != -1) {
			sos.write(bytes, 0, data);
		}
		sos.flush();
		sos.close();
		fis.close();
		
		// 다운로드 완료후에 서버에 저장된 zip파일을 삭제처리한다.
		downFile.delete();
	}
	
	// 채팅창 띄우기
	@RequestMapping(value = "/webSocket/webSocket", method = RequestMethod.GET)
	public String webSocketGet() {
		return "webSocket/webSocket";
	}
	
	
}
