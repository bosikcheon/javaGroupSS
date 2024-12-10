package com.spring.javaGroupS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.StudyDAO;

@Service
public class StudyServiceImpl implements StudyService {

	@Autowired
	StudyDAO studyDAO;

	@Override
	public String[] getCityStringArray(String dodo) {
		String[] strArray = new String[100];
		
		if(dodo.equals("서울")) {
			strArray[0] = "강남구";
			strArray[1] = "강북구";
			strArray[2] = "강서구";
			strArray[3] = "강동구";
			strArray[4] = "관악구";
			strArray[5] = "서초구";
			strArray[6] = "종로구";
			strArray[7] = "영등포구";
			strArray[8] = "마포구";
			strArray[9] = "구로구";
		}
		else if(dodo.equals("경기")) {
			strArray[0] = "안성시";
			strArray[1] = "수원시";
			strArray[2] = "평택시";
			strArray[3] = "용인시";
			strArray[4] = "성남시";
			strArray[5] = "안산시";
			strArray[6] = "광명시";
			strArray[7] = "김포시";
			strArray[8] = "광주시";
			strArray[9] = "의정부시";
		}
		else if(dodo.equals("충북")) {
			strArray[0] = "청주시";
			strArray[1] = "충주시";
			strArray[2] = "제천시";
			strArray[3] = "진천군";
			strArray[4] = "영동군";
			strArray[5] = "음성군";
			strArray[6] = "증평군";
			strArray[7] = "단양군";
			strArray[8] = "괴산군";
			strArray[9] = "보은군";
		}
		else if(dodo.equals("충남")) {
			strArray[0] = "천안시";
			strArray[1] = "아산시";
			strArray[2] = "서산시";
			strArray[3] = "당진시";
			strArray[4] = "논산시";
			strArray[5] = "예산시";
			strArray[6] = "청양군";
			strArray[7] = "공주시";
			strArray[8] = "부여시";
			strArray[9] = "서천군";
		}
		
		
		return strArray;
	}

	@Override
	public ArrayList<String> getCityStringArrayList(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("강북구");
			vos.add("강서구");
			vos.add("강동구");
			vos.add("관악구");
			vos.add("서초구");
			vos.add("종로구");
			vos.add("영등포구");
			vos.add("마포구");
			vos.add("구로구");
		}
		else if(dodo.equals("경기")) {
			vos.add("안성시");
			vos.add("수원시");
			vos.add("평택시");
			vos.add("용인시");
			vos.add("성남시");
			vos.add("안산시");
			vos.add("광명시");
			vos.add("김포시");
			vos.add("광주시");
			vos.add("의정부시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("제천시");
			vos.add("진천군");
			vos.add("영동군");
			vos.add("음성군");
			vos.add("증평군");
			vos.add("단양군");
			vos.add("괴산군");
			vos.add("보은군");
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("서산시");
			vos.add("당진시");
			vos.add("논산시");
			vos.add("예산시");
			vos.add("청양군");
			vos.add("공주시");
			vos.add("부여시");
			vos.add("서천군");
		}
		
		return vos;
	}
	
	
}
