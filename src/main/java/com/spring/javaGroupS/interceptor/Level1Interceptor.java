package com.spring.javaGroupS.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 우수회원에 대한 처리
public class Level1Interceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// 관리자(0), 우수회원(1), 정회원(2), 준회원(3), 탈퇴회원(99), 비회원(999)
		
		HttpSession session = request.getSession();
		int level = session.getAttribute("sLevel")==null ? 999 : (int) session.getAttribute("sLevel");
		
		// 우수회원(1) 이상일때 처리
		if(level > 1) {
			RequestDispatcher dispatcher;
			if(level == 999) {
				dispatcher = request.getRequestDispatcher("/message/loginNo");
			}
			else {	// 정회원, 준회원
				dispatcher = request.getRequestDispatcher("/message/memberLevelNo");
			}
			dispatcher.forward(request, response);
			return false;
		}
		
		return true;
	}
	
}
