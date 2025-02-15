package com.spring.javaGroupS.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.spring.javaGroupS.common.JavaGroupProvide;
import com.spring.javaGroupS.dao.StudyDAO;
import com.spring.javaGroupS.vo.ChartVO;
import com.spring.javaGroupS.vo.CrimeVO;
import com.spring.javaGroupS.vo.KakaoAddressVO;
import com.spring.javaGroupS.vo.QrcodeVO;
import com.spring.javaGroupS.vo.TransactionVO;
import com.spring.javaGroupS.vo.User2VO;
import com.spring.javaGroupS.vo.UserVO;

import net.coobird.thumbnailator.Thumbnailator;

@Service
public class StudyServiceImpl implements StudyService {

	@Autowired
	StudyDAO studyDAO;
	
	@Autowired
	JavaGroupProvide javaGroupProvide;

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

	@Override
	public int fileUpload(MultipartFile fName, String mid) {
		int res = 0;
		
		// 파일이름 중복처리
		String imsi = RandomStringUtils.randomAlphanumeric(4);
		
		// 서버로 전송되어온 파일의 정보를 읽어온다.
		String oFileName = fName.getOriginalFilename();
		String sFileName = mid + "_" + imsi + "_" + oFileName;
		
		// 서버에 파일 올리기
		try {
			writeFile(fName, sFileName);
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	private void writeFile(MultipartFile fName, String sFileName) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		
		if(fName.getBytes().length != -1) {
			fos.write(fName.getBytes());
		}
		fos.flush();
		fos.close();
		
	}

	@Override
	public int multiFileUpload(MultipartHttpServletRequest mFile, HttpServletRequest request) {
		int res = 0;
		
		try {
			List<MultipartFile> fileList = mFile.getFiles("fName");
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = RandomStringUtils.randomAlphanumeric(4) + "_" + oFileName;
				
				writeFile(file, sFileName);
				
				oFileNames += oFileName;
				sFileNames += sFileName;
				fileSizes += file.getSize();
			}
			System.out.println("원본파일 : " + oFileNames);
			System.out.println("저장파일 : " + sFileNames);
			System.out.println("총사이즈 : " + fileSizes);
			
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void setTransactionUserInput(UserVO vo) {
		studyDAO.setTransactionUserInput(vo);
	}

	@Override
	public void setTransactionUser2Input(UserVO vo) {
		studyDAO.setTransactionUser2Input(vo);
	}

	@Transactional
	@Override
	public void setTransactionUserInput2(UserVO vo) {
		studyDAO.setTransactionUserInput(vo);
		studyDAO.setTransactionUser2Input(vo);
	}

	@Transactional
	@Override
	public void setTransactionUserInput3(UserVO vo) {
		studyDAO.setTransactionUserInput3(vo);
	}

	@Override
	public int setValidatorInput(TransactionVO vo) {
		return studyDAO.setValidatorInput(vo);
	}

	@Override
	public void setTransactionUserInput4(User2VO vo) {
		studyDAO.setTransactionUserInput4(vo);
	}

	@Override
	public String setThumbnailCreate(MultipartFile file, HttpServletRequest request) {
		String res = "";
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
			File oFile = new File(realPath + file.getOriginalFilename());
			file.transferTo(oFile);
			
			String thumbnailFlie = realPath + "s_" + file.getOriginalFilename();
			File tFile = new File(thumbnailFlie);
			
			int width = 160;
			int height = width / 4 * 3;		// 4 : 3
			Thumbnailator.createThumbnail(oFile, tFile, width, height);
			
			res = file.getOriginalFilename();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public KakaoAddressVO getKakaoAddressSearch(String address) {
		return studyDAO.getKakaoAddressSearch(address);
	}

	@Override
	public int setKakaoAddressInput(KakaoAddressVO vo) {
		return studyDAO.setKakaoAddressInput(vo);
	}

	@Override
	public List<KakaoAddressVO> getKakaoAddressList() {
		return studyDAO.getKakaoAddressList();
	}

	@Override
	public int setKakaoAddressDelete(String address) {
		return studyDAO.setKakaoAddressDelete(address);
	}

	@Override
	public List<ChartVO> getMemberVisitCount() {
		return studyDAO.getMemberVisitCount();
	}

	@Override
	public void setSaveCrimeData(CrimeVO vo) {
		studyDAO.setSaveCrimeData(vo);
	}

	@Override
	public int setDeleteCrimeData(int year) {
		return studyDAO.setDeleteCrimeData(year);
	}

	@Override
	public ArrayList<CrimeVO> getListCrimeData(int year) {
		return studyDAO.getListCrimeData(year);
	}

	@Override
	public CrimeVO getYearPoliceCheck(int year, String police) {
		return studyDAO.getYearPoliceCheck(year, police);
	}

	@Override
	public String setQrcodeCreate(String realPath) {
		String qrcodeName = javaGroupProvide.newNameCreate(2);
		String qrcodeImage = "";
		
		try {
			qrcodeImage = "생성된 QR코드명 : " + qrcodeName;
			qrcodeImage = new String(qrcodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			int qrcodeColor = 0xFF000000;
			int qrcodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, qrcodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			ImageIO.write(bufferedImage, "png", new File(realPath + qrcodeName + ".png"));
		} catch (IOException e) {
			System.out.println("qr코드 생성실패~" + e.getMessage());
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrcodeName;
	}

	@Override
	public String setqrcodeCreate1(String realPath, QrcodeVO vo) {
		String qrcodeName = javaGroupProvide.newNameCreate(2);
		String qrcodeImage = "";
		
		try {
			qrcodeName += vo.getMid() + "_" + vo.getName() + "_" + vo.getEmail();
			qrcodeImage = "생성날짜 : " + qrcodeName.substring(0, 4) + "년, " + qrcodeName.substring(4, 6) + "월, " + qrcodeName.substring(6, 8) + "일\n";
			qrcodeImage += "아이디 : " + vo.getMid() + "\n";
			qrcodeImage += "성명 : " + vo.getName() + "\n";
			qrcodeImage += "이메일 : " + vo.getEmail();
			qrcodeImage = new String(qrcodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// QR코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			int qrcodeColor = 0xFF000000;
			int qrcodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, qrcodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			ImageIO.write(bufferedImage, "png", new File(realPath + qrcodeName + ".png"));
		} catch (IOException e) {
			System.out.println("qr코드 생성실패~" + e.getMessage());
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrcodeName;
	}

	@Override
	public String setqrcodeCreate2(String realPath, QrcodeVO vo) {
		String qrcodeName = javaGroupProvide.newNameCreate(2);
		String qrcodeImage = "";
		
		try {
			//qrcodeName += vo.getSubject() + "_" + vo.getMoveUrl();
			qrcodeName += vo.getSubject();
			qrcodeImage = vo.getMoveUrl();
			qrcodeImage = new String(qrcodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// QR코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			int qrcodeColor = 0xFF000000;
			int qrcodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, qrcodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			ImageIO.write(bufferedImage, "png", new File(realPath + qrcodeName + ".png"));
		} catch (IOException e) {
			System.out.println("qr코드 생성실패~" + e.getMessage());
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrcodeName;
	}

	@Override
	public String setqrcodeCreate3(String realPath, QrcodeVO vo) {
		String qrcodeName = javaGroupProvide.newNameCreate(2);
		String qrcodeImage = "";
		
		try {
			qrcodeName += vo.getMovieCinema() + "_" + vo.getMovieName() + "_" + vo.getMovieDate() + "_" + vo.getMovieTime() + "_" + vo.getMovieAdult() + "_" + vo.getMovieChild();
			qrcodeImage = "구매자 ID : " + vo.getMid() + "\n";
			qrcodeImage += "상영관 : " + vo.getMovieCinema() + "\n";
			qrcodeImage += "영화제목 : " + vo.getMovieName() + "\n";
			qrcodeImage += "상영일자 : " + vo.getMovieDate() + "\n";
			qrcodeImage += "상영시간 : " + vo.getMovieTime() + "\n";
			qrcodeImage += "성인구매인원수 : " + vo.getMovieAdult() + "\n";
			qrcodeImage += "소인구매인원수 : " + vo.getMovieChild();
			qrcodeImage = new String(qrcodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// QR코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			int qrcodeColor = 0xFF000000;
			int qrcodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, qrcodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			ImageIO.write(bufferedImage, "png", new File(realPath + qrcodeName + ".png"));
		} catch (IOException e) {
			System.out.println("qr코드 생성실패~" + e.getMessage());
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrcodeName;
	}

	
	
}
