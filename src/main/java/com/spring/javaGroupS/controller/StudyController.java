package com.spring.javaGroupS.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaGroupS.service.BoardService;
import com.spring.javaGroupS.service.DbtestService;
import com.spring.javaGroupS.service.MemberService;
import com.spring.javaGroupS.service.StudyService;
import com.spring.javaGroupS.service.UserService;
import com.spring.javaGroupS.vo.BoardVO;
import com.spring.javaGroupS.vo.ChartVO;
import com.spring.javaGroupS.vo.CrawLingVO;
import com.spring.javaGroupS.vo.CrimeVO;
import com.spring.javaGroupS.vo.DbPayMentVO;
import com.spring.javaGroupS.vo.KakaoAddressVO;
import com.spring.javaGroupS.vo.MailVO;
import com.spring.javaGroupS.vo.MemberVO;
import com.spring.javaGroupS.vo.QrcodeVO;
import com.spring.javaGroupS.vo.TransactionVO;
import com.spring.javaGroupS.vo.User2VO;
import com.spring.javaGroupS.vo.UserVO;

import io.github.bonigarcia.wdm.WebDriverManager;

//@RestController
@Controller
@RequestMapping("/study")
public class StudyController {

	@Autowired
	StudyService studyService;
	
	@Autowired
	DbtestService dbtestService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value="/ajax/ajaxForm", method = RequestMethod.GET)
	public String ajaxFormGet() {
		return "study/ajax/ajaxForm";
	}
	
	@RequestMapping(value="/ajax/ajaxTest1", method = RequestMethod.GET)
	public String ajaxTest1Get(Model model, int idx) {
		model.addAttribute("idx", idx);
		return "study/ajax/ajaxForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest2", method = RequestMethod.GET)
	public String ajaxTest2Get(int idx) {
		System.out.println("idx : " + idx);
		//return "study/ajax/ajaxForm";
		return idx + "";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest3", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String ajaxTest3Post(String str) {
		System.out.println("str : " + str);
		return str;
	}
	
	@RequestMapping(value="/ajax/ajaxTest4_1", method = RequestMethod.GET)
	public String ajaxTest4_1Get() {
		return "study/ajax/ajaxTest4_1";
	}
	
	// 배열형식(문자열)의 자료처리
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_1", method = RequestMethod.POST)
	public String[] ajaxTest4_1Post(String dodo) {
//		String[] strArray = new String[100];
//		strArray = studyService.getCityStringArray(dodo);
//		return strArray;
		return studyService.getCityStringArray(dodo);
	}
	
	@RequestMapping(value="/ajax/ajaxTest4_2", method = RequestMethod.GET)
	public String ajaxTest4_2Get() {
		return "study/ajax/ajaxTest4_2";
	}
	
	// ArrayList의 자료처리
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest4_2Post(String dodo) {
		return studyService.getCityStringArrayList(dodo);
	}
	
	@RequestMapping(value="/ajax/ajaxTest4_3", method = RequestMethod.GET)
	public String ajaxTest4_3Get() {
		return "study/ajax/ajaxTest4_3";
	}
	
	// Map형식(Key,value)의 자료처리
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_3", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest4_3Post(String dodo) {
		ArrayList<String> vos = studyService.getCityStringArrayList(dodo);
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		return map;
	}
	
	@RequestMapping(value="/ajax/ajaxTest4_4", method = RequestMethod.GET)
	public String ajaxTest4_4Get(Model model) {
		ArrayList<String> midVos = dbtestService.getDbtestMidList();
		model.addAttribute("midVos", midVos);
		return "study/ajax/ajaxTest4_4";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_4", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String ajaxTest4_4Post(String mid) {
		UserVO vo = dbtestService.getDbtestIdCheckForm(mid);
		String str = "<h3>회원정보</h3>";
		str += "아이디 : " + vo.getMid() + "<br>";
		str += "비밀번호 : " + vo.getPwd() + "<br>";
		str += "성명 : " + vo.getName() + "<br>";
		str += "나이 : " + vo.getAge() + "<br>";
		str += "성별 : " + vo.getGender() + "<br>";
		str += "주소 : " + vo.getAddress() + "<br>";
		return str;
	}
	
	// 트랜잭션 폼 보기
	@RequestMapping(value = "/transaction/transactionForm", method = RequestMethod.GET)
	public String transactionFormGet(Model model) {
		List<UserVO> vos = userService.getUserList();
		List<UserVO> vos2 = userService.getUser2List();
		model.addAttribute("vos", vos);
		model.addAttribute("vos2", vos2);
		return "study/transaction/transactionForm";
	}
	
	// 트랜잭션 user,user2테이블 자료 등록
	@Transactional
	@RequestMapping(value = "/transaction/transactionInput", method = RequestMethod.POST)
	public String transactionInputPost(UserVO vo) {
		studyService.setTransactionUserInput(vo);
		studyService.setTransactionUser2Input(vo);
		
		return "redirect:/study/transaction/transactionForm";
	}
	
	@RequestMapping(value = "/transaction/transactionInput2", method = RequestMethod.POST)
	public String transactionInput2Post(UserVO vo) {
		studyService.setTransactionUserInput2(vo);
		return "redirect:/study/transaction/transactionForm";
	}
	
	@RequestMapping(value = "/transaction/transactionInput3", method = RequestMethod.POST)
	public String transactionInput3Post(UserVO vo) {
		studyService.setTransactionUserInput3(vo);
		return "redirect:/study/transaction/transactionForm";
	}
	
	// validate처리하면서 트랜잭션도 처리하기(트랜잭션은 서비스에서 처리했다.)
	@RequestMapping(value = "/transaction/transactionInput4", method = RequestMethod.POST)
	public String transactionInput4Post(Model model, @Validated User2VO vo, BindingResult bindingResult) {
		if(bindingResult.hasFieldErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			
			String temp = "";
			for(ObjectError e : list) {
				System.out.println("메세지1 : " + e);
				System.out.println("메세지2 : " + e.getDefaultMessage());
				temp = e.getDefaultMessage() + "";
				break;
			}
			model.addAttribute("mid", temp);
			return "redirect:/message/transactionError";
		}
		
		studyService.setTransactionUserInput4(vo);
		return "redirect:/message/transactionOk";
	}
	
	// Backend체크(validator 폼 보기)
	@RequestMapping(value = "/validator/validatorForm", method = RequestMethod.GET)
	public String validatorFormGet(Model model) {
		List<UserVO> vos = userService.getUserList();
		model.addAttribute("vos", vos);
		return "study/validator/validatorForm";
	}
	
	// Backend체크(validator 처리 - vo에서 관련 자료에 대한 어노테이션 체크가 선행되어 있어야 한다.)
	@ResponseBody
	@RequestMapping(value = "/validator/validatorInput", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String validatorFormPost(@Validated TransactionVO vo, BindingResult bindingResult) {
//		if(bindingResult.hasFieldErrors()) {
//			System.out.println("에러 발생...");
//			System.out.println("bindingResult :" + bindingResult);
//			return "0";
//		}
		
		if(bindingResult.hasFieldErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			
			String[] temp = null;
			for(ObjectError e : list) {
				temp = e.getDefaultMessage().split("/");
				System.out.println("메세지 : " + temp[0] + " , 코드 : " + temp[1]);
				break;
			}
			return temp[0];
		}
		
		return studyService.setValidatorInput(vo) + "";
	}
	
	
	// 메일 폼 보기(주소록을 함께 넘겨주고 있다)
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.GET)
	public String mailFormGet(Model model) {
//		ArrayList<MemberVO> vos = memberService.getMemberTotalList();
		List<MemberVO> vos = memberService.getMemberList(0, 0, "mail");
		model.addAttribute("vos", vos);
		return "study/mail/mail";
	}
	
	// 메일 전송하기
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.POST)
	public String mailFormPost(MailVO vo, HttpServletRequest request) throws MessagingException {
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		messageHelper.setTo(toMail);			// 받는 사람의 메일주소
		messageHelper.setSubject(title);	// 메일 제목
		messageHelper.setText(content);		// 메일 내용
		
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>JavaGroup 에서 길동이가 보냅니다.</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>JavaGroup</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		// 본문에 기재될 그림파일의 경로를 별도로 지정처리한다. 그런후 다시 보관함에 저장한다.
		//FileSystemResource file = new FileSystemResource("D:\\javaGroup\\springframework\\works\\javaGroupS\\src\\main\\webapp\\resources\\images\\main.jpg");
		//request.getSession().getServletContext().getRealPath("/resources/images/main.jpg");
		
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		// 첨부파일 보내기
		//file = new FileSystemResource("D:\\javaGroup\\springframework\\works\\javaGroupS\\src\\main\\webapp\\resources\\images\\3.zip");
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/3.zip"));
		messageHelper.addAttachment("3.zip", file);
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/11.jpg"));
		messageHelper.addAttachment("11.jpg", file);
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}
	
	// 랜덤한 영문자와 숫자 조합연습
	@RequestMapping(value = "/random/randomAlphaNumeric", method = RequestMethod.GET)
	public String randomAlphaNumericGet() {
		return "study/random/randomForm";
	}
	
	// 랜덤한 숫자 조합연습
	@ResponseBody
	@RequestMapping(value = "/random/randomNumeric", method = RequestMethod.POST)
	public String randomNumericPost() {
		return ((int) (Math.random()*(99999999-10000000+1)) + 10000000) + "";
	}
	
	// 랜덤한 UUID 조합연습
	@ResponseBody
	@RequestMapping(value = "/random/randomUUID", method = RequestMethod.POST)
	public String randomUUIDPost() {
		return UUID.randomUUID() + "";
	}
	
	// 랜덤한 영문자와 숫자 조합연습
	@ResponseBody
	@RequestMapping(value = "/random/randomAlphaNumeric", method = RequestMethod.POST)
	public String randomAlphaNumericPost() {
		//String res = RandomStringUtils.randomAlphanumeric(32);
		return RandomStringUtils.randomAlphanumeric(64);
	}
	
	// 파일 업로드 폼 불러오기
	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.GET)
	public String fileUploadGet(HttpServletRequest request, Model model) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload");
		
		String[] files = new File(realPath).list();
		
		model.addAttribute("files", files);
		model.addAttribute("fileCount", files.length);
		
		return "study/fileUpload/fileUpload";
	}
	
	// 싱글파일 업로드 처리
	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.POST)
	public String fileUploadPost(MultipartFile fName, String mid) {
		int res = studyService.fileUpload(fName, mid);
		
		if(res != 0) return "redirect:/message/fileUploadOk";
		else return "redirect:/message/fileUploadNo";
	}
	
	// 파일 삭제 처리
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDelete", method = RequestMethod.POST)
	public String fileDeletePost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		String res = "0";
		File fName = new File(realPath + file);
		if(fName.exists()) {
			fName.delete();
			res = "1";
		}
		return res;
	}
	
	// 파일 전체삭제 처리
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDeleteAll", method = RequestMethod.POST)
	public String fileDeleteAllPost(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		String res = "0";
		File folder = new File(realPath);
		if(!folder.exists()) return res;
		
		File[] files = folder.listFiles();
		
		if(files.length != 0) {
			for(File f : files) {
				if(f.exists()) {
					f.delete();
					res = "1";
				}
			}
		}
		return res;
	}
	
	// 멀티파일 업로드 폼 불러오기
	@RequestMapping(value = "/fileUpload/multiFile", method = RequestMethod.GET)
	public String multiFileGet() {
		return "study/fileUpload/multiFile";
	}
	
	// 멀티파일 업로드 처리
	@RequestMapping(value = "/fileUpload/multiFile", method = RequestMethod.POST)
	public String multiFilePost(MultipartHttpServletRequest mFile, HttpServletRequest request) {
		int res = studyService.multiFileUpload(mFile, request);
		
		if(res != 0) return "redirect:/message/multiFileUploadOk";
		else return "redirect:/message/multiFileUploadNo";
	}
	
	// 썸네일 이미지 폼보기
	@RequestMapping(value = "/thumbnail/thumbnailForm", method = RequestMethod.GET)
	public String thumbnailGet() {
		return "study/thumbnail/thumbnailForm";
	}
	
	// 썸네일 이미지 처리하기
	@ResponseBody
	@RequestMapping(value = "/thumbnail/thumbnailForm", method = RequestMethod.POST)
	public String thumbnailPost(MultipartFile file, HttpServletRequest request) {
		return studyService.setThumbnailCreate(file, request);
	}
	
	// 썸네일 이미지 리스트 보여주기
	@RequestMapping(value = "/thumbnail/thumbnailList", method = RequestMethod.GET)
	public String thumbnailListGet(Model model, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		String[] files = new File(realPath).list();
		
		model.addAttribute("files", files);
		model.addAttribute("fCount", (files.length / 2));
		return "study/thumbnail/thumbnailList";
	}
	
	// 썸네일 이미지 한건 삭제처리
	@ResponseBody
	@RequestMapping(value = "/thumbnail/thumbnailDelete", method = RequestMethod.POST)
	public String thumbnailDeletePost(HttpServletRequest request, String file) {
		String res = "0";
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		File oFile = new File(realPath + file);
		File tFile = new File(realPath + file.substring(2));
		
		if(oFile.exists()) {
			oFile.delete();
			tFile.delete();
			res = "1";
		}
		
		return res;
	}
	
	// 썸네일 이미지 전체 삭제처리
	@ResponseBody
	@RequestMapping(value = "/thumbnail/thumbnailDeleteAll", method = RequestMethod.POST)
	public String thumbnailDeleteAllPost(HttpServletRequest request) {
		String res = "0";
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		
		File folder = new File(realPath);
		if(!folder.exists()) return "0";
		
		File[] files = folder.listFiles();
		
		if(files.length != 0) {
			for(File f : files) {
				if(!f.isDirectory()) f.delete();
			}
			res = "1";
		}
		return res;
	}
	
	@RequestMapping(value = "/kakao/kakaomap", method = RequestMethod.GET)
	public String kakaomapGet() {
		return "study/kakao/kakaomap";
	}
	
	@RequestMapping(value = "/kakao/kakaoEx1", method = RequestMethod.GET)
	public String kakaoEx1Get() {
		return "study/kakao/kakaoEx1";
	}

	@ResponseBody
	@RequestMapping(value = "/kakao/kakaoEx1", method = RequestMethod.POST)
	public String kakaoEx1Post(KakaoAddressVO vo) {
		int res = 0;
		KakaoAddressVO searchVO = studyService.getKakaoAddressSearch(vo.getAddress());
		if(searchVO == null) res = studyService.setKakaoAddressInput(vo);
		return res + "";
	}
	
	// 카카오맵 : MyDB에 저장된 지명 검색
	@RequestMapping(value = "/kakao/kakaoEx2", method = RequestMethod.GET)
	public String kakaoEx2Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address
		) {
		KakaoAddressVO vo = new KakaoAddressVO();		
		
		List<KakaoAddressVO> addressVos = studyService.getKakaoAddressList();
		
		if(address.equals("")) {
			vo.setAddress("청주 그린컴퓨터학원");
			vo.setLatitude(36.63516928441032);
			vo.setLongitude(127.45954113569472);
		}
		else {
			vo = studyService.getKakaoAddressSearch(address);
		}
		
		model.addAttribute("addressVos", addressVos);
		model.addAttribute("vo", vo);
		
		return "study/kakao/kakaoEx2";
	}
	
	@ResponseBody
	@RequestMapping(value = "/kakao/kakaoAddressDelete", method = RequestMethod.POST)
	public String kakaoAddressDeletePost(String address) {
		return studyService.setKakaoAddressDelete(address) + "";

	}
	
	// 카카오DB에 저장된 키워드 검색 결과를..... MyDB에 저장하기....
	@RequestMapping(value = "/kakao/kakaoEx3", method = RequestMethod.GET)
	public String kakaoEx3Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address
		) {
		model.addAttribute("address", address);
		return "study/kakao/kakaoEx3";
	}
	
	// 차트 폼 보기
	@RequestMapping(value = "/chart/chartForm", method = RequestMethod.GET)
	public String chartFormGet(Model model,
			@RequestParam(name="part", defaultValue = "barVChart", required = false) String part
		) {
		model.addAttribute("part", part);
		return "study/chart/chartForm";
	}
	
	// 차트2 폼 보기
	@RequestMapping(value = "/chart2/chart2Form", method = RequestMethod.GET)
	public String chart2FormGet(Model model,
			@RequestParam(name="part", defaultValue = "barVChart", required = false) String part
			) {
		model.addAttribute("part", part);
		return "study/chart2/chart2Form";
	}
	
	// 차트2 폼 처리
	@RequestMapping(value = "/chart2/chart2Form", method = RequestMethod.POST)
	public String chart2FormPost(Model model, ChartVO vo,
			@RequestParam(name="part", defaultValue = "barV", required = false) String part
		) {
		System.out.println("vo : " + vo);
		model.addAttribute("part", part);
		model.addAttribute("vo", vo);
		return "study/chart2/chart2Form";
	}
	
	
	// 차트2 member 차트 처리(최근7일간 방문자수 / 가장 많이 방문한 방문자 5회원 보기)
	@RequestMapping(value = "/chart2/memberViewChart", method = RequestMethod.GET)
	public String memberViewChartGet(Model model,
			@RequestParam(name="part", defaultValue = "barV", required = false) String part
		) {
		List<ChartVO> vos = null;
		
		if(part.equals("visitCount")) {
			vos = studyService.getMemberVisitCount();
			
			String[] visitDates = new String[7];
			int[] visitCounts = new int[7];
			
			for(int i=0; i<7; i++) {
				visitDates[i] = vos.get(i).getVisitDate();
				visitCounts[i] = vos.get(i).getVisitCount();
			}
			System.out.println("전체개수: " + visitCounts.length);
			model.addAttribute("xTitle", "방문날짜");
			model.addAttribute("regend", "하루 총 방문자수");
			model.addAttribute("visitDates", visitDates);
			model.addAttribute("visitCounts", visitCounts);
			model.addAttribute("title", "최근 7일간 방문횟수");
			model.addAttribute("subTitle", "(최근 7일간 방문한 방문자 총수를 표시합니다.");
			model.addAttribute("part", part);
		}
		
		return "study/chart2/chart2Form";
	}
	
	@RequestMapping(value = "/restapi/restapiForm", method = RequestMethod.GET)
	public String restapiFormGet() {
		return "study/restapi/restapiForm";
	}
	
	@RequestMapping(value = "/restapi/restapiTest1/{message}", method = RequestMethod.GET)
	public String restapiTest1Get(@PathVariable String message) {
		System.out.println("message : " + message);
		return "message : " + message;
	}
	
	@RequestMapping(value = "/restapi/restapiTest4", method = RequestMethod.GET)
	public String restapiTest4Get() {
		return "study/restapi/restapiTest4";
	}
	
	@ResponseBody
	@RequestMapping(value = "/restapi/saveCrimeData", method = RequestMethod.POST)
	public void saveCrimeDataPost(CrimeVO vo) {
		studyService.setSaveCrimeData(vo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/restapi/deleteCrimeData", method = RequestMethod.POST)
	public String deleteCrimeDataPost(int year) {
		return studyService.setDeleteCrimeData(year) + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/restapi/listCrimeData", method = RequestMethod.POST)
	public ArrayList<CrimeVO> listCrimeDatePost(int year) {
		return studyService.getListCrimeData(year);
	}
	
	@RequestMapping(value = "/restapi/yearPoliceCheck", method = RequestMethod.POST)
	public String policeCheckPost(Model model, int year, String police) {
		CrimeVO analyzeVO = studyService.getYearPoliceCheck(year, police);
		model.addAttribute("year", year);
		model.addAttribute("police", police);
		model.addAttribute("analyzeVO", analyzeVO);
		return "study/restapi/restapiTest4";
	}
	
	// 웹크롤링 (JSoup)
	@RequestMapping(value = "/crawling/jsoupForm", method = RequestMethod.GET)
	public String jsoupFormGet() {
		return "study/crawling/jsoupForm";
	}
	
	/*
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String jsoupPost(String url, String selector) throws IOException {
		String str = "";
		Connection conn = Jsoup.connect(url);
		
		Document document = conn.get();
		//System.out.println("document : " + document);
		
		System.out.println("selector : " + selector);
		Elements selectors = document.select(selector);
		//System.out.println("selectors : " + selectors);
		
		int i=0;
		for(Element select : selectors) {
			i++;
			//System.out.println("select : " + select);
			System.out.println("select : " + select.text());
			str += i + " : " + select + "<br/>";
		}
		//str = selectors.toString();
		return str;
	}
	*/
	
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST)
	public ArrayList<String> jsoupPost(String url, String selector) throws IOException {
		Connection conn = Jsoup.connect(url);
		
		Document document = conn.get();
		//System.out.println("document : " + document);
		
		System.out.println("selector : " + selector);
		Elements selectors = document.select(selector);
		//System.out.println("selectors : " + selectors);
		
		ArrayList<String> vos = new ArrayList<String>();
		int i=0;
		for(Element select : selectors) {
			i++;
			//System.out.println("select : " + select);
			System.out.println("select : " + select.text());
			//vos.add(i + " : " + select.html());
			vos.add(i + " : " + select.html().replace("data-", ""));
		}
		return vos;
	}
	
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup2", method = RequestMethod.POST)
	public ArrayList<CrawLingVO> jsoup2Post() throws IOException {
		Connection conn = Jsoup.connect("https://news.naver.com/");
		
		Document document = conn.get();
		
		Elements selectors = null;
				
		ArrayList<String> titleVos = new ArrayList<String>();
		selectors = document.select("strong.cnf_news_title");
		for(Element select : selectors) {
			titleVos.add(select.html());
		}
		
		ArrayList<String> imageVos = new ArrayList<String>();
		selectors = document.select("div.cnf_news_thumb");
		for(Element select : selectors) {
			imageVos.add(select.html().replace("data-", ""));
		}
		
		ArrayList<String> broadcastVos = new ArrayList<String>();
		selectors = document.select("em.cnf_journal_name");
		for(Element select : selectors) {
			broadcastVos.add(select.html());
		}
		
		ArrayList<String> checkVos = new ArrayList<String>();
		selectors = document.select("div.comp_news_feed.comp_news_none");
		for(Element select : selectors) {
			checkVos.add(select.html().replace("data-", ""));
		}
		
		ArrayList<CrawLingVO> vos = new ArrayList<CrawLingVO>();
		CrawLingVO vo = null;
		for(int i=0; i<imageVos.size(); i++) {
			vo = new CrawLingVO();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(broadcastVos.get(i));
			vo.setItem4(checkVos.get(i));
			vos.add(vo);
		}
		return vos;
	}
	
	// 다음 엔터테인먼트 기사/사진/언론사 검색
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup3", method = RequestMethod.POST)
	public ArrayList<CrawLingVO> jsoup3Post() throws IOException {
		Connection conn = Jsoup.connect("https://entertain.daum.net/");
		
		Document document = conn.get();
		
		Elements selectors = null;
		
		ArrayList<String> titleVos = new ArrayList<String>();
		selectors = document.select("a.link_txt.valid_link");
		for(Element select : selectors) {
			titleVos.add(select.html());
		}
		
		ArrayList<String> imageVos = new ArrayList<String>();
		selectors = document.select("a.link_thumb");
		for(Element select : selectors) {
			imageVos.add(select.html());
		}
		
		ArrayList<String> broadcastVos = new ArrayList<String>();
		selectors = document.select("span.info_thumb");
		for(Element select : selectors) {
			broadcastVos.add(select.html());
		}
		
//		ArrayList<String> checkVos = new ArrayList<String>();
//		selectors = document.select("div.comp_news_feed.comp_news_none");
//		for(Element select : selectors) {
//			checkVos.add(select.html().replace("data-", ""));
//		}
		
		ArrayList<CrawLingVO> vos = new ArrayList<CrawLingVO>();
		CrawLingVO vo = null;
		for(int i=0; i<titleVos.size(); i++) {
			vo = new CrawLingVO();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(broadcastVos.get(i));
//			vo.setItem4(checkVos.get(i));
			vos.add(vo);
		}
		System.out.println("vos : " + vos);
		return vos;
	}
	
	// 네이버 검색어로 검색처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup4", method = RequestMethod.POST)
	public ArrayList<String> jsoup4Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		
		Document document = conn.get();
		
		Elements selectors = document.select(searchSelector);
		ArrayList<String> vos = new ArrayList<String>();
		
		int i = 0;
		for(Element select : selectors) {
			i++;
			vos.add(i+ " : " + select.html().replace("data-", "").replace("lazy", ""));
		}
		return vos;
	}
	
	// 멜론 차트 검색처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup5", method = RequestMethod.POST)
	public ArrayList<CrawLingVO> jsoup5Post() throws IOException {
		Connection conn = Jsoup.connect("https://www.melon.com/chart/index.htm");
		Document document = conn.get();
		
		Elements selectors = null;
		
		ArrayList<String> rankVos = new ArrayList<String>();
		selectors = document.select("td > div.wrap.t_center > span.rank");
		for(Element select : selectors) {
			rankVos.add(select.html());
		}
		
		ArrayList<String> imageVos = new ArrayList<String>();
		selectors = document.select("div.wrap > a.image_typeAll");
		for(Element select : selectors) {
			imageVos.add(select.html());
		}
		
		ArrayList<String> songVos = new ArrayList<String>();
		selectors = document.select("div.wrap_song_info > div.ellipsis.rank01");
		for(Element select : selectors) {
			songVos.add(select.html());
		}
		
		ArrayList<String> singerVos = new ArrayList<String>();
		selectors = document.select("div.ellipsis.rank02");
		for(Element select : selectors) {
			singerVos.add(select.html());
		}
		
		ArrayList<CrawLingVO> vos = new ArrayList<CrawLingVO>();
		CrawLingVO vo = null;
		for(int i=0; i<100; i++) {
			vo = new CrawLingVO();
			vo.setItem1(rankVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(songVos.get(i));
			vo.setItem4(singerVos.get(i));
			vos.add(vo);
		}
		return vos;
	}
	
	// 웹크롤링 (Selenium)
	@RequestMapping(value = "/crawling/seleniumForm", method = RequestMethod.GET)
	public String seleniumFormGet() {
		return "study/crawling/seleniumForm";
	}

	// 웹크롤링 (Selenium) 처리
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/crawling/selenium", method = RequestMethod.POST)
	public List<HashMap<String, Object>> seleniumFormPost() {
		List<HashMap<String, Object>> vos = new ArrayList<HashMap<String,Object>>();
		
		try {
			WebDriver driver = new ChromeDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
			WebDriverManager.chromedriver().setup();
			
			driver.get("http://www.cgv.co.kr/movies/");
			
			// 현재 상영작만 보여주기
			WebElement btnMore = driver.findElement(By.id("chk_nowshow"));
			btnMore.click();
			
			btnMore = driver.findElement(By.className("link-more"));
			btnMore.click();
			
			try { Thread.sleep(2000); } catch (InterruptedException e) {}
			
			List<WebElement> elements = driver.findElements(By.cssSelector("div.sect-movie-chart ol li"));
			for(WebElement element : elements) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String link = element.findElement(By.tagName("a")).getAttribute("href");
				String title = "<a href='"+link+"' target='_blank'>" + element.findElement(By.className("title")).getText() + "</a>";
				String image = "<img src='"+element.findElement(By.tagName("img")).getAttribute("src")+"' width='150px' />";
				String percent = element.findElement(By.className("percent")).getText();
				map.put("lint", link);
				map.put("title", title);
				map.put("image", image);
				map.put("percent", percent);
				vos.add(map);
			}
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("vos : " + vos);
		return vos;
	}
	
	// 크롤링연습 처리(selenium) - SRT 열차 조회하기
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/crawling/train", method = RequestMethod.POST)
	public List<HashMap<String, Object>> trainPost(HttpServletRequest request, String stationStart, String stationStop) {
		List<HashMap<String, Object>> array = new ArrayList<HashMap<String,Object>>();
		try {
			WebDriver driver = new ChromeDriver();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
      WebDriverManager.chromedriver().setup();			
			
			driver.get("http://srtplay.com/train/schedule");

			WebElement btnMore = driver.findElement(By.xpath("//*[@id=\"station-start\"]/span"));
			btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-pos-input\"]"));
      btnMore.sendKeys(stationStart);
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-arrive\"]/span"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      btnMore = driver.findElement(By.id("station-pos-input"));
      
      btnMore.sendKeys(stationStop);
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}

      btnMore = driver.findElement(By.xpath("//*[@id=\"sr-train-schedule-btn\"]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      List<WebElement> timeElements = driver.findElements(By.cssSelector(".table-body ul.time-list li"));
 			
      HashMap<String, Object> map = null;
      
			for(WebElement element : timeElements){
				map = new HashMap<String, Object>();
				String train=element.findElement(By.className("train")).getText();
				String start=element.findElement(By.className("start")).getText();
				String arrive=element.findElement(By.className("arrive")).getText();
				String time=element.findElement(By.className("time")).getText();
				String price=element.findElement(By.className("price")).getText();
				map.put("train", train);
				map.put("start", start);
				map.put("arrive", arrive);
				map.put("time", time);
				map.put("price", price);
				array.add(map);
			}
			
      // 요금조회하기 버튼을 클릭한다.(처리 안됨 - 스크린샷으로 대체)
//      btnMore = driver.findElement(By.xpath("//*[@id=\"scheduleDiv\"]/div[2]/div/ul/li[1]/div/div[5]/button"));
      //System.out.println("요금 조회버튼클릭");
//      btnMore.click();
//      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	// QR 코드 연습폼 보기
	@RequestMapping(value = "/qrcode/qrcodeForm", method = RequestMethod.GET)
	public String qrcodeFormGet() {
		return "study/qrcode/qrcodeForm";
	}
	
	// QR 코드 생성
	@ResponseBody
	@RequestMapping(value = "/qrcode/qrcodeCreate", method = RequestMethod.POST)
	public String qrcodeCreatePost(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrcode/");				
		return studyService.setQrcodeCreate(realPath);
	}
	
	// 개인정보 QR 코드 생성폼 보기
	@RequestMapping(value = "/qrcode/qrcodeEx1", method = RequestMethod.GET)
	public String qrcodeEx1Get() {
		return "study/qrcode/qrcodeEx1";
	}
	
	// 개인정보 QR 코드 생성
	@ResponseBody
	@RequestMapping(value = "/qrcode/qrcodeCreate1", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String qrcodeCreate1Post(HttpServletRequest request, QrcodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrcode/");				
		return studyService.setqrcodeCreate1(realPath, vo);
	}
	
	// 소개사이트 QR 코드 생성폼 보기
	@RequestMapping(value = "/qrcode/qrcodeEx2", method = RequestMethod.GET)
	public String qrcodeEx2Get() {
		return "study/qrcode/qrcodeEx2";
	}
	
	// 소개사이트 QR 코드 생성
	@ResponseBody
	@RequestMapping(value = "/qrcode/qrcodeCreate2", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String qrcodeCreate2Post(HttpServletRequest request, QrcodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrcode/");				
		return studyService.setqrcodeCreate2(realPath, vo);
	}
	
	// 소개사이트 QR 코드 생성폼 보기
	@RequestMapping(value = "/qrcode/qrcodeEx3", method = RequestMethod.GET)
	public String qrcodeEx3Get() {
		return "study/qrcode/qrcodeEx3";
	}
	
	// 소개사이트 QR 코드 생성
	@ResponseBody
	@RequestMapping(value = "/qrcode/qrcodeCreate3", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public String qrcodeCreate3Post(HttpServletRequest request, QrcodeVO vo) {
		MemberVO mVo = memberService.getMemberIdCheck(vo.getMid());
	  vo.setEmail(mVo.getEmail());
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrcode/");				
		return studyService.setqrcodeCreate3(realPath, vo);
	}
	
	// 캡차 생성메소드 호출
	@RequestMapping(value = "/captcha/captchaForm", method = RequestMethod.GET)
	public String captchaFormGet() {
		return "redirect:/study/captcha/captchaImage";
	}
	
	// 캡차 생성후 폼으로 전송처리
	@RequestMapping(value = "/captcha/captchaImage", method = RequestMethod.GET)
	public String captchaImageGet(HttpServletRequest request, Model model, HttpSession session) {
		/*
		Font[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		for(Font f : fontList) {
			System.out.println(f.getFontName());
		}
		*/
		
		try {
			String str = RandomStringUtils.randomAlphanumeric(5);
			System.out.println("str : " + str);
			session.setAttribute("sCaptcha", str);
			
			Font font = new Font("Jokerman", Font.ITALIC, 30);
			FontRenderContext frc = new FontRenderContext(null, true, true);
			Rectangle2D bounds = font.getStringBounds(str, frc);
			int w = (int) bounds.getWidth();
			int h = (int) bounds.getHeight();
			
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
			Graphics2D g = image.createGraphics();
			
			g.fillRect(0, 0, w, h);
			g.setColor(new Color(0, 156, 240));
			g.setFont(font);
			
			//g.drawString(str, (float) bounds.getX(), (float) -bounds.getY());
			g.drawString(str, 0, h);
			g.dispose();
			
			String realPath = request.getSession().getServletContext().getRealPath("/resources/data/captcha/");
			int temp = (int)(Math.random()*5) + 1;
			String captchaImage = "captchaImage" + temp + ".png";
			
			ImageIO.write(image, "png", new File(realPath + captchaImage));
			model.addAttribute("captchaImage", captchaImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "study/captcha/captchaForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/captcha/captchaCheck", method = RequestMethod.POST)
	public String captcapCheckPost(HttpSession session, String strCaptcha) {
		if(strCaptcha.equals(session.getAttribute("sCaptcha"))) return "1";
		else return "0";
	}
	
	@RequestMapping(value = "/infiniteScroll/infiniteScroll", method = RequestMethod.GET)
	public String infiniteScrollGet(Model model,
		  @RequestParam(name="pag", defaultValue = "1", required = false) int pag,	
		  @RequestParam(name="pageSize", defaultValue = "3", required = false) int pageSize	
		) {
		int startIndexNo = (pag - 1) * pageSize;
		List<BoardVO> vos = boardService.getBoardList(startIndexNo, pageSize);
		model.addAttribute("vos", vos);
		return "study/infiniteScroll/infiniteScroll";
	}
	
	/*
	@ResponseBody
	@RequestMapping(value = "/infiniteScroll/infiniteScrollPaging", method = RequestMethod.POST)
	public ModelAndView infiniteScrollPagingPost(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,	
			@RequestParam(name="pageSize", defaultValue = "3", required = false) int pageSize	
			) {
		int startIndexNo = (pag - 1) * pageSize;
		List<BoardVO> vos = boardService.getBoardList(startIndexNo, pageSize);
		model.addAttribute("vos", vos);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("study/infiniteScroll/infiniteScrollPaging");
		
		return mv;
	}
	*/

//	AJAX에서 JSP 파일을 요청하고, 그 결과를 직접 클라이언트에 렌더링하려면, @ResponseBody는 필요하지 않습니다.
//	만약 AJAX가 서버로부터 JSON 데이터를 받아서 처리하려고 한다면, 이 경우 @ResponseBody가 필요합니다.
//	JSP 파일을 직접 반환하는 경우: @ResponseBody는 필요 없으며, 그냥 return "example.jsp";와 같이 반환하면 됩니다.
//	JSON 데이터나 다른 형식의 응답을 반환하는 경우: @ResponseBody를 사용하여 데이터를 JSON으로 반환해야 합니다.
	@RequestMapping(value = "/infiniteScroll/infiniteScrollPaging", method = RequestMethod.POST)
	public String infiniteScrollPagingPost(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,	
			@RequestParam(name="pageSize", defaultValue = "3", required = false) int pageSize	
		) {
		int startIndexNo = (pag - 1) * pageSize;
		List<BoardVO> vos = boardService.getBoardList(startIndexNo, pageSize);
		model.addAttribute("vos", vos);
		
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("study/infiniteScroll/infiniteScrollPaging");
		
		return "study/infiniteScroll/infiniteScrollPaging";
	}
	
	// 결제 연습폼
	@RequestMapping(value = "/payment/payment", method = RequestMethod.GET)
	public String paymentGet() {
		return "study/payment/payment";
	}
	
	// 결제 연습 처리하기
	@RequestMapping(value = "/payment/payment", method = RequestMethod.POST)
	public String paymentPost(DbPayMentVO vo, Model model, HttpSession session) {
		session.setAttribute("sPayMentVO", vo);
		model.addAttribute("vo", vo);
		return "study/payment/sample";
	}
	
	// 결제처리완료후 결제내역을 확인하는 화면...
  @RequestMapping(value = "/payment/paymentOk", method = RequestMethod.GET)
  public String paymentOkGet(Model model, HttpSession session) {
  	DbPayMentVO vo = (DbPayMentVO) session.getAttribute("sPayMentVO");
  	model.addAttribute("vo", vo);
  	
  	session.removeAttribute("sPayMentVO");
  	return "study/payment/paymentOk";
  }
}
