package com.spring.javaGroupS.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaGroupS.vo.PdsVO;

public interface PdsService {

	List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part);

	int setPdsInput(HttpServletRequest request, MultipartHttpServletRequest mFile, PdsVO vo);

}
