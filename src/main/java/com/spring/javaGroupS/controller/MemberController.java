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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
				session.setAttribute("sLogin", "일반로그인");
				
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
	
	// 일반 카카오 로그인 인증처리
	@RequestMapping(value = "/kakaoLogin", method = RequestMethod.GET)
	public String kakaoLoginGet(HttpSession session, HttpServletRequest request,
			String nickName, String email, String accessToken
		) throws MessagingException {
		//session.setAttribute("sAccessToken", accessToken);
		session.setAttribute("sLogin", "kakao");
		
		// 카카오회원이 우리 회원인지 조사?
		MemberVO vo = memberService.getMemberNickNameEmailCheck(nickName, email);
		
		// 카카오회원이 우리회원이 아니라면 자동으로 우리회원에 가입처리한다.
		// 필수입력:아이디, 닉네임, 이메일, 성명(닉네임으로 대체), 비밀번호(임시비밀번호 발급처리), 레벨:정회원(2)
		String newMember = "NO";	// 신규회원은 OK, 기존회원은 NO
		if(vo == null) { 
			// 신규회원인지에 대한 체크하기....
			String mid = email.substring(0, email.indexOf("@"));
			MemberVO vo2 = memberService.getMemberIdCheck(mid);
			if(vo2 != null) return "redirect:/message/memberIdSameCheck";
		
			// 임시 비밀번호 발급처리
			String pwd = UUID.randomUUID().toString().substring(0, 8);
			session.setAttribute("sImsiPwd", pwd);
			
			// 새로 발급된 비밀번호를 암호화 시켜서 DB에 저장처리한다. 이때 카카오로그인한 회원은 바로 정회원으로 등업처리한다.
			SecurityUtil security = new SecurityUtil();
			String imsiPwd = security.encryptSHA256("123" + pwd);
			memberService.setKakaoMemberInput(mid, nickName, email, "123"+imsiPwd);
			
			// 새로 발급받은 임시비밀번호를 메일로 전송처리.
			mailSend(email, "임시비밀번호를 발송하였습니다.", "임시비밀번호 : "+pwd, request);
			
			vo = memberService.getMemberIdCheck(mid);
			
			// 신규회원은 비밀번호를 새로발급하였기에, memberMain창에서 '개인정보/비밀번호'를 변경하라는 메세지를 지속적으로 뿌리기 위함.
			session.setAttribute("sLoginNew", "OK");
			newMember = "OK";
		}
		
		// 로그인 인증완료시 세션처리
		String strLevel = "";
		if(vo.getLevel() == 0) strLevel = "관리자";
		else if(vo.getLevel() == 1) strLevel = "우수회원";
		else if(vo.getLevel() == 2) strLevel = "정회원";
		else if(vo.getLevel() == 3) strLevel = "준회원";
		
		session.setAttribute("sMid", vo.getMid());
		session.setAttribute("sNickName", vo.getNickName());
		session.setAttribute("sLevel", vo.getLevel());
		session.setAttribute("strLevel", strLevel);
		
		// DB처리 내용 작업하기(방문포인트(?), 총방문횟수, 오늘방문횟수, 마지막방문일자
		// 오늘 방문횟수 처리
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(nowDate);
		
		int todayCnt = 0;
		if(vo.getLastDate().substring(0, 10).equals(strDate)) todayCnt = vo.getTodayCnt() + 1;
		else todayCnt = 1;
		
		memberService.setMemberInforUpdate(vo.getMid(), todayCnt);
		
		if(newMember.equals("NO")) return "redirect:/message/memberLoginOk?mid="+vo.getMid();
		else return "redirect:/message/memberLoginNewOk?mid="+vo.getMid();
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
	
	// 회원 아이디 로그인체크
	@ResponseBody
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.POST)
	public String memberIdCheckPost(String mid) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		String str = "0";
		if(vo != null) str = "1";
		return str;
	}
	
	// 회원 아이디 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberIdDuplicationCheck", method = RequestMethod.POST)
	public String memberIdDuplicationCheckPost(String mid) {
		MemberVO vo = memberService.getMemberIdDuplicationCheck(mid);
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
	public String memberJoinPost(MultipartFile fName, MemberVO vo) {
		// 아이디/닉네임 중복체크
		if(memberService.getMemberIdCheck(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		if(memberService.getMemberNickNameCheck(vo.getNickName()) != null) return "redirect:/message/nickCheckNo";
		
		// 비밀번호 암호화
		String salt = UUID.randomUUID().toString().substring(0,3);
		SecurityUtil security = new SecurityUtil();
		vo.setPwd(salt + security.encryptSHA256(salt + vo.getPwd()));
		
		// 회원 사진 처리
		//if(vo.getPhoto().equals("")) vo.setPhoto("noimage.jpg");
		if(!fName.getOriginalFilename().equals("")) vo.setPhoto(memberService.getFileUpload(fName, vo.getMid()));
		else vo.setPhoto("noimage.jpg");
		
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
	
	// 회원 전체 리스트(페이징 처리)
	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public String memberListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		
		int totRecCnt = memberService.getTotRecCnt("");
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		List<MemberVO> vos = memberService.getMemberList(startIndexNo, pageSize, "page");
		
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
	
	// 현재 비밀번호 확인처리
	@ResponseBody
	@RequestMapping(value = "/memberPwdCheckOk", method = RequestMethod.GET)
	public String memberPwdCheckOkGet(Model model, String mid, String pwd) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo == null) return "0";
		
		String salt = vo.getPwd().substring(0,3);
		SecurityUtil security = new SecurityUtil();
		String tempPwd = security.encryptSHA256(salt + pwd);
		
		if(!vo.getPwd().substring(3).equals(tempPwd)) return "0";
		else return "1";
	}
	
	// 비밀번호 확인창으로 이동하기(pwdFlag값이 p는 비밀번호 변경, i는 정보수정창으로 이동처리함)
	@RequestMapping(value = "/memberPwdCheck/{pwdFlag}", method = RequestMethod.GET)
	public String memberPwdCheckGet(Model model, @PathVariable String pwdFlag) {
		model.addAttribute("pwdFlag", pwdFlag);
		return "member/memberPwdCheck";
	}
	
	// 비밀번호 변경처리
	@RequestMapping(value = "/memberPwdCheck/{pwdFlag}", method = RequestMethod.POST)
	public String memberPwdCheckPost(Model model, @PathVariable String pwdFlag, String mid, String pwdCheck) {
		String salt = UUID.randomUUID().toString().substring(0,3);
		SecurityUtil security = new SecurityUtil();
		String pwd = salt + security.encryptSHA256(salt + pwdCheck);
		
		int res = memberService.setMemberPwdCheckOk(mid, pwd);
		
		if(res != 0) return "redirect:/message/memberPwdChangeOk";
		else return "redirect:/message/memberPwdChangeNo";
	}
	
	// 회원 정보수정창 폼으로 이동
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.GET)
	public String memberUpdateGet(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo.getTel() != null && vo.getAddress() != null) {
			String[] tels = vo.getTel().split("-");
			String[] addresss = vo.getAddress().split("/");
			
			vo.setTel1(tels[0]);
			vo.setTel2(tels[1]);
			vo.setTel3(tels[2]);
			vo.setPostcode(addresss[0]);
			vo.setAddress(addresss[1]);
			vo.setDetailAddress(addresss[2]);
			vo.setExtraAddress(addresss[3]);		
		}
		String[] emails = vo.getEmail().split("@");
		vo.setEmail1(emails[0]);
		vo.setEmail2(emails[1]);
		
		model.addAttribute("vo", vo);
		return "member/memberUpdate";
	}
	
	// 회원 정보수정 처리
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.POST)
	public String memberUpdatePost(Model model, HttpSession session, MemberVO vo) {
		System.out.println("vo : " + vo);
		// 닉네임 체크
		String nickName = (String) session.getAttribute("sNickName");
		if(memberService.getMemberNickNameCheck(vo.getNickName()) != null && !nickName.equals(vo.getNickName())) return "redirect:/message/nickNameCheckNo";
		
		// 회원 사진 처리
		if(vo.getPhoto() == null || vo.getPhoto() == "") vo.setPhoto("noimage.jpg");
		
		// 회원 정보 수정
		int res = memberService.setMemberUpdateOk(vo);
		if(res != 0) {
			session.setAttribute("sNickName", vo.getNickName());
			return "redirect:/message/memberUpdateOk";
		}
		else return "redirect:/message/memberUpdateNo";
	}
	
	// 회원 탈퇴 신청..
	@ResponseBody
	@RequestMapping(value = "/memberDeleteCheck", method = RequestMethod.POST)
	public String memberDeleteCheckPost(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		int res = memberService.setMemberDeleteCheck(mid);
		
		if(res != 0) {
			session.invalidate();
			return "1";
		}
		else return "0";
	}
	
	// 회원 아이디 찾기
	@RequestMapping(value = "/memberIdSearch", method = RequestMethod.GET)
	public String memberIdSearchGet() {
		return "member/memberIdSearch";
	}
	
	// 아이디 찾기를 위한 인증메일 발송하기
	@ResponseBody
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public String emailCheckGet(HttpServletRequest request, HttpSession session, String email) throws MessagingException {
	  ArrayList<MemberVO> vos = memberService.getMemberEmailCheck(email);
		if(vos.size() == 0) return "0";
		
		String emailKey = UUID.randomUUID().toString().substring(0,8);
		session.setAttribute("sEmailKey", emailKey);
		//System.out.println("emailKey: " + emailKey);
		mailSend(email, "아이디 찾기를 위한 인증번호", "인증번호 : " + emailKey, request);
		return "1";
	}
	
	// 인증번호 확인후 아이디 돌려주기
	@ResponseBody
	@RequestMapping(value = "/emailCheckOk", method = RequestMethod.POST)
	public ArrayList<MemberVO> emailCheckGet(HttpSession session, String email, String checkKey) {
		String emailKey = (String) session.getAttribute("sEmailKey");
		if(!emailKey.equals(checkKey))	return null;
		
		ArrayList<MemberVO> vos = memberService.getMemberEmailCheck(email);
		return vos;
	}
	
	// 비밀번호 찾기 폼
	@RequestMapping(value = "/memberPwdSearch", method = RequestMethod.GET)
	public String memberPwdSearchGet() {
		return "member/memberPwdSearch";
	}
	
	// 임시 비밀번호 전송을 위한 아이디와 메일주소 체크하기
	@ResponseBody
	@RequestMapping(value = "/inforCheck", method = RequestMethod.POST)
	public String inforCheckGet(String mid, String email) {
		return memberService.getInforCheck(mid, email) + "";
	}
	
	// 임시 비밀번호 전송하기
	@ResponseBody
	@RequestMapping(value = "/emailSendOk", method = RequestMethod.POST)
	public void emailSendOkGet(HttpServletRequest request, String mid, String email) throws MessagingException {
		String mailFlag = UUID.randomUUID().toString().substring(0,8);
		mailSend(email, mid+"님 임시비밀번호", mailFlag, request);
	}
	
}
