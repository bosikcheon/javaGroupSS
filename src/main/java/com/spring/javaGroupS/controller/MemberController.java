package com.spring.javaGroupS.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaGroupS.common.SecurityUtil;
import com.spring.javaGroupS.service.GuestService;
import com.spring.javaGroupS.service.MemberService;
import com.spring.javaGroupS.vo.GuestVO;
import com.spring.javaGroupS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	GuestService guestService;
	
	@Autowired
	JavaMailSender mailSender;
	
	// 일반 로그인 폼
	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) {
		// 로그인창의 아이디 체크 유무에 대한 처리(쿠키)
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		return "member/memberLogin";
	}
	
	// 일반 로그인 인증처리
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public String memberLoginPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@RequestParam(name="mid", defaultValue = "hkd1234", required = false) String mid,
			@RequestParam(name="pwd", defaultValue = "1234", required = false) String pwd,
			@RequestParam(name="idSave", defaultValue = "", required = false) String idSave
			//String idSave
		) {
		// 로그인 인증처리(sha256암호화)
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null) {
			String salt = vo.getPwd().substring(0,3);
			SecurityUtil security = new SecurityUtil();
			String tempPwd = security.encryptSHA256(salt + pwd);
			
			if(vo.getPwd().substring(3).equals(tempPwd)) {
				// 로그인 인증완료시 세션처리
				String strLevel = "";
				if(vo.getLevel() == 0) strLevel = "관리자";
				else if(vo.getLevel() == 1) strLevel = "우수회원";
				else if(vo.getLevel() == 2) strLevel = "정회원";
				else if(vo.getLevel() == 3) strLevel = "준회원";
				
				session.setAttribute("sMid", mid);
				session.setAttribute("sNickName", vo.getNickName());
				session.setAttribute("sLevel", vo.getLevel());
				session.setAttribute("strLevel", strLevel);
				
				// 로그인창의 아이디 체크 유무에 대한 처리(쿠키 저장/삭제)
				if(idSave.equals("on")) {
					Cookie cookieMid = new Cookie("cMid", mid);
					cookieMid.setPath("/");
					cookieMid.setMaxAge(60*60*24*7);
					response.addCookie(cookieMid);
				}
				else {
					Cookie[] cookies = request.getCookies();
					if(cookies != null) {
						for(int i=0; i<cookies.length; i++) {
							if(cookies[i].getName().equals("cMid")) {
								cookies[i].setPath("/");
								cookies[i].setMaxAge(0);
								response.addCookie(cookies[i]);
								break;
							}
						}
					}
				}
				
				// DB처리 내용 작업하기(방문포인트(?), 총방문횟수, 오늘방문횟수, 마지막방문일자
				// 오늘 방문횟수 처리
				Date nowDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = sdf.format(nowDate);
				
				int todayCnt = 0;
				if(vo.getLastDate().substring(0, 10).equals(strDate)) todayCnt = vo.getTodayCnt() + 1;
				else todayCnt = 1;
				
				memberService.setMemberInforUpdate(mid, todayCnt);
				
				return "redirect:/message/memberLoginOk?mid="+mid;
			}
		}
		return "redirect:/message/memberLoginNo";
	}
	
	// 로그인 OK시 회원 메인방으로 이동
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String memberMainGet(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO mVo = memberService.getMemberIdCheck(mid);
		
		// 방명록에 작성한 글 목록 가져오기
		ArrayList<GuestVO> guestVos = guestService.getGuestMidList(mVo.getNickName());
		
		model.addAttribute("mVo", mVo);
		model.addAttribute("guestVos", guestVos);
		return "member/memberMain";
	}
	
	// 로그인 아웃시 처리
	@RequestMapping(value = "/memberLogout", method = RequestMethod.GET)
	public String memberLogoutGet(HttpSession session) {
		session.invalidate();
		return "redirect:/message/memberLogout";
	}
	
	// 회원 아이디 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.POST)
	public String memberIdCheckPost(String mid) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		String str = "0";
		if(vo != null) str = "1";
		return str;
	}
	
	// 회원 닉네임 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberNickNameCheck", method = RequestMethod.GET)
	public String memberNickNameCheckPost(String nickName) {
		MemberVO vo = memberService.getMemberNickNameCheck(nickName);
		String str = "0";
		if(vo != null) str = "1";
		return str;
	}
	
	// 회원가입 폼 보기
	@RequestMapping(value = "/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet() {
		return "member/memberJoin";
	}
	
	// 회원가입 처리하기
	@RequestMapping(value = "/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost(MemberVO vo) {
		// 아이디/닉네임 중복체크
		if(memberService.getMemberIdCheck(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		if(memberService.getMemberNickNameCheck(vo.getNickName()) != null) return "redirect:/message/nickCheckNo";
		
		// 비밀번호 암호화
		String salt = UUID.randomUUID().toString().substring(0,3);
		SecurityUtil security = new SecurityUtil();
		vo.setPwd(salt + security.encryptSHA256(salt + vo.getPwd()));
		
		if(vo.getPhoto().equals("")) vo.setPhoto("noimage.jpg");
		
		int res = memberService.setMemberJoinOk(vo);
		
		if(res != 0) return "redirect:/message/memberJoinOk";
		else return "redirect:/message/memberJoinNo";
	}
	
	// 회원가입시 이메일로 인증번호 발송하기
	@ResponseBody
	@RequestMapping(value = "/memberEmailCheck", method = RequestMethod.POST)
	public String memberEmailCheckPost(HttpSession session, HttpServletRequest request, String email) throws MessagingException {
		String emailKey = UUID.randomUUID().toString().substring(0, 8);
		session.setAttribute("sEmailKey", emailKey);
		System.out.println("sEmailKey : " + emailKey);
		
		mailSend(email, "이메일 인증키입니다.", "인증키 : " + emailKey, request);
		
		return "1";
	}
	
	// 회원가입시 이메일로전송받은 인증번호 확인하기
	@ResponseBody
	@RequestMapping(value = "/memberEmailCheckOk", method = RequestMethod.POST)
	public String memberEmailCheckPost(HttpSession session, String checkKey){
		String emailKey = (String) session.getAttribute("sEmailKey");
		if(emailKey.equals(checkKey))	return "1";
		else return "0";
	}
	
	// 메일 전송하기(이메일주소, 제목, 내용)
	private void mailSend(String toMail, String title, String mailFlag, HttpServletRequest request) throws MessagingException {
		String content = "";
		
		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		messageHelper.setTo(toMail);			// 받는 사람의 메일주소
		messageHelper.setSubject(title);	// 메일 제목
		messageHelper.setText(content);		// 메일 내용
		
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>"+mailFlag+"</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>JavaGroup</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		// 메일 전송하기
		mailSender.send(message);
	}
	
	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public String memberListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		
		int totRecCnt = memberService.getTotRecCnt();
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		List<MemberVO> vos = memberService.getMemberList(startIndexNo, pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totRecCnt", totRecCnt);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "member/memberList";
	}
}
