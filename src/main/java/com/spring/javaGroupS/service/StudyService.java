package com.spring.javaGroupS.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface StudyService {

	String[] getCityStringArray(String dodo);

	ArrayList<String> getCityStringArrayList(String dodo);

	int fileUpload(MultipartFile fName, String mid);

	int multiFileUpload(MultipartHttpServletRequest mFile, HttpServletRequest request);

}
