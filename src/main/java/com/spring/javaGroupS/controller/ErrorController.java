package com.spring.javaGroupS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaGroupS.vo.UserVO;

@Controller
@RequestMapping("/errorPage")
public class ErrorController {

	// 에러연습 폼보기
	@RequestMapping(value = "/errorMain", method = RequestMethod.GET)
	public String errorMainGet() {
		return "errorPage/errorMain";
	}
	
	// JSP페이지에서의 서블릿 에러발생시 에러페이지로 이동처리하기
	@RequestMapping(value = "/error1", method = RequestMethod.GET)
	public String error1Get() {
		return "errorPage/error1";
	}
	
	// 컨트롤러를 통해서 에러페이지로 이동처리하기
	@RequestMapping(value = "/errorMessage1", method = RequestMethod.GET)
	public String errorMessage1Get() {
		return "errorPage/errorMessage1";
	}
	
	// 400에러발생 호출...vo의 나이는 숫자인데 넘어오는 값이 없어서 문자로 간주되어 오류(400)
	@RequestMapping(value = "/errorTest400", method = RequestMethod.GET)
	public String errorTest400Get(UserVO vo) {
	//public String errorTest400Get() {
		return "errorPage/error400";	// 앞의 오류(400)로 아래는 수행하지 않는다.
	}
	
	// web.xml에서의 400에러발생시 이동처리하기
	@RequestMapping(value = "/error400", method = RequestMethod.GET)
	public String error400Get() {
		//public String error400Get() {
		return "errorPage/error400";
	}
	
	// web.xml에서의 404에러발생시 이동처리하기
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String error404Get() {
		return "errorPage/error404";
	}
	
	@RequestMapping(value = "/errorTest405", method = RequestMethod.POST)
	public String errorTest405Post() {
		return "errorPage/error405";
	}
	
	// web.xml에서의 405에러발생시 이동처리하기
	@RequestMapping(value = "/error405", method = RequestMethod.GET)
	public String error405Get() {
		return "errorPage/error405";
	}
	
	@RequestMapping(value = "/errorTest500", method = RequestMethod.GET)
	public String errorTest500Get(UserVO vo) {
//		int su = 10 / 0;		// java.lang.ArithmeticException
//		System.out.println("su : " + su);
		
//		String su = "O1O";
//		int intSu = Integer.parseInt(su);		// java.lang.NumberFormatException
//		System.out.println("su : " + su + " , intSu : " + intSu);
		
		int temp = vo.getMid().length();		// java.lang.NullPointerException
		System.out.println("mid : " + temp);
		
//		String[] temps = "010-1234-5679".split("-");	// java.lang.ArrayIndexOutOfBoundsException
//		System.out.println(temps[0] + "," + temps[1] + "," +temps[2] + "," +temps[3]);
		
		return "errorPage/errorMain";
	}
	
	@RequestMapping(value = "/error500", method = RequestMethod.GET)
	public String error500Get() {
		return "errorPage/error500";
	}
	
	@RequestMapping(value = "/errorArithmetic", method = RequestMethod.GET)
	public String errorArithmeticGet() {
		return "errorPage/errorArithmetic";
	}
	
	@RequestMapping(value = "/errorNumberFormat", method = RequestMethod.GET)
	public String errorNumberFormatGet() {
		return "errorPage/errorNumberFormat";
	}
	
	@RequestMapping(value = "/errorNullPointer", method = RequestMethod.GET)
	public String errorNullPointerGet() {
		return "errorPage/errorNullPointer";
	}
	
	@RequestMapping(value = "/errorArrayIndexOutOfBounds", method = RequestMethod.GET)
	public String errorArrayIndexOutOfBoundsGet() {
		return "errorPage/errorArrayIndexOutOfBounds";
	}
	
}
