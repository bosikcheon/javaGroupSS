package com.spring.javaGroupS.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

// cron : "초 분 시 일 월 요일"

//@Component
public class JavaGroupScheduler {
	
	@Autowired
	JavaMailSender mailSender;
	
	//@Scheduled(cron = "10 * * * * *")
	public void schedulerRun1() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("10초에 메세지가 출력됩니다....." + strToday);
	}
	
	//@Scheduled(cron = "0/10 * * * * *")
	public void schedulerRun2() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("10초마다 한번씩 메세지가 출력됩니다." + strToday);
	}
	
	//@Scheduled(cron = "0 0/1 * * * *")
	public void schedulerRun3() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("1분마다 한번씩 메세지가 출력됩니다." + strToday);
	}
	
	//@Scheduled(cron = "0 55 12 * * *")
	public void schedulerRun4() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("12시 55분에 한번만 메세지가 출력됩니다." + strToday);
	}
	
	//@Scheduled(cron = "0 10 23 * * *")		// 23시 10분에 신상품 메세지를 회원들에게 메일로 전송한다.
	public void schedulerRun5() throws MessagingException {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("23시 10분에 한번만 메세지가 출력됩니다." + strToday);
		
		String email = "cjsk1126@naver.com";
		String title = "신제품 안내 메일";
		String content = "겨울 신상품 안내 메일 입니다.";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		messageHelper.setTo(email);
		messageHelper.setSubject(title);
		messageHelper.setText(content);

		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>신상품</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>javaGroupS20</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		FileSystemResource file = new FileSystemResource("D:\\javaGroup\\springframework\\works\\javaGroupS\\src\\main\\webapp\\resources\\images\\main.jpg");
		messageHelper.addInline("main.jpg", file);
		
		mailSender.send(message);
	}
	
}
