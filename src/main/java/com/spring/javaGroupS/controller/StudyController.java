package com.spring.javaGroupS.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaGroupS.service.DbtestService;
import com.spring.javaGroupS.service.MemberService;
import com.spring.javaGroupS.service.StudyService;
import com.spring.javaGroupS.vo.MailVO;
import com.spring.javaGroupS.vo.MemberVO;
import com.spring.javaGroupS.vo.UserVO;

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
	
	
}
