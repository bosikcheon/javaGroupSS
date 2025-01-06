package com.spring.javaGroupS.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter
public class EncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//System.out.println("Filter - init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//System.out.println("Filter - doFilter - start");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		//System.out.println("path : " + path);
		
		// 한글처리
		if(!path.startsWith("/css/") && !path.startsWith("/js/")) {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			//System.out.println("path : " + path);
		}
		
		chain.doFilter(request, response);
		
		//System.out.println("Filter - doFilter - end");
		
	}

	@Override
	public void destroy() {

		//System.out.println("Filter - destroy");
		
	}

}
