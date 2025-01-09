package com.spring.javaGroupS.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Component
public class JavaGroupProvide {
	
	// 파일명에 지정된 자리수만큼 난수를 발생시켜서 새로운 파일명을 만들어 반환하는 메소드
	public String newNameCreate(int len) {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String newName = sdf.format(today);
		newName += RandomStringUtils.randomAlphanumeric(len) + "_";
		return newName;
	}

	public String saveFileName(String originalFilename) {
		Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    String saveFileName = sdf.format(date) + "_" + originalFilename;
		return saveFileName;
	}

	public void writeFile(MultipartFile fName, String sFileName, String urlPath) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+urlPath+"/");
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		
		if(fName.getBytes().length != -1) {
			fos.write(fName.getBytes());
		}
		fos.flush();
		fos.close();
	}

	public void fileCopyCheck(String oriFilePath, String copyFilePath) {
    File oriFile = new File(oriFilePath);
    File copyFile = new File(copyFilePath);

    try {
      FileInputStream  fis = new FileInputStream(oriFile);
      FileOutputStream fos = new FileOutputStream(copyFile);

      byte[] buffer = new byte[2048];
      int count = 0;
      while((count = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, count);
      }
      fos.flush();
      fos.close();
      fis.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
	}
	
}
