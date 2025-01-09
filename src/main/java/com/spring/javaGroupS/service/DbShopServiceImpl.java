package com.spring.javaGroupS.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaGroupS.common.JavaGroupProvide;
import com.spring.javaGroupS.dao.DbShopDAO;
import com.spring.javaGroupS.vo.DbBaesongVO;
import com.spring.javaGroupS.vo.DbCartVO;
import com.spring.javaGroupS.vo.DbOptionVO;
import com.spring.javaGroupS.vo.DbOrderVO;
import com.spring.javaGroupS.vo.DbProductVO;

@Service
public class DbShopServiceImpl implements DbShopService {

	@Autowired
	DbShopDAO dbShopDAO;
	
	@Autowired
	JavaGroupProvide javaGroupProvide;

	@Override
	public List<DbProductVO> getCategoryMain() {
		return dbShopDAO.getCategoryMain();
	}

	@Override
	public List<DbProductVO> getCategoryMiddleName(String categoryMainCode) {
		return dbShopDAO.getCategoryMiddleName(categoryMainCode);
	}

	@Override
	public List<DbProductVO> getCategorySubName(String categoryMainCode, String categoryMiddleCode) {
		return dbShopDAO.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}

	@Override
	public int imgCheckProductInput(MultipartFile file, DbProductVO vo) {
    int res = 0;
    // 메인 이미지 업로드 작업처리
    try {
      String originalFilename = file.getOriginalFilename();
      if(originalFilename != null && originalFilename != "") {
        // 상품 메인사진 업로드시 파일명 중복을 피하기 위한 처리(파일명 변경하기)
      	String saveFileName = javaGroupProvide.saveFileName(originalFilename);
        
        // 메인 이미지파일을 서버 파일시스템에 업로드 처리하는 메소드 호출
      	javaGroupProvide.writeFile(file, saveFileName, "dbShop/product");
        vo.setFSName(saveFileName);		// 업로드된 메인이미지의 실제로 저장된 파일명을 vo에 저장시켜준다.
      }
      else {
        return res;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // ckeditor에서 올린 이미지파일을 'ckeditor'에서 'dbShop/product'폴더로 복사한다.
    //             0         1         2         3         4         5
    //             012345678901234567890123456789012345678901234567890
    // <img alt="" src="/javaGroupS/data/ckeditor/211229124318_4.jpg"
    // <img alt="" src="/javaGroupS/data/dbShop/product/211229124318_4.jpg"

    // ckeditor을 이용해서 담은 상품의 상세설명내역에 그림이 포함되어 있으면 그림을 'ckeditor'폴더에서 'dbShop/product'폴더로 복사작업처리 시켜준다.
    String content = vo.getContent();
    if(content.indexOf("src=\"/") == -1) return 0;    // content박스의 내용중 그림이 없으면 돌려보낸다.

    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/");

    int position = 31;
    String nextImg = content.substring(content.indexOf("src=\"/") + position);
    boolean sw = true;

    while(sw) {
      String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
      String copyFilePath = "";
      String oriFilePath = uploadPath + "ckeditor/" + imgFile;

      copyFilePath = uploadPath + "dbShop/product/" + imgFile;

      javaGroupProvide.fileCopyCheck(oriFilePath, copyFilePath);

      if(nextImg.indexOf("src=\"/") == -1) sw = false;
      else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
    }
    
    vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/dbShop/product/"));

    int maxIdx = 1;
    DbProductVO maxVo = dbShopDAO.getProductMaxIdx();
    if(maxVo != null) maxIdx = maxVo.getIdx() + 1;
    
    vo.setIdx(maxIdx);
    vo.setProductCode(vo.getCategoryMainCode()+vo.getCategoryMiddleCode()+vo.getCategorySubCode()+maxIdx);
    res = dbShopDAO.setDbProductInput(vo);
    return res;
	}

	@Override
	public List<DbProductVO> getSubTitle() {
		return dbShopDAO.getSubTitle();
	}

	@Override
	public List<DbProductVO> getDbShopList(String part, String mainPrice) {
		return dbShopDAO.getDbShopList(part, mainPrice);
	}

	@Override
	public DbProductVO getCategoryProductNameOne(String productName) {
		return dbShopDAO.getCategoryProductNameOne(productName);
	}

	@Override
	public DbProductVO getCategoryProductNameOneVO(DbProductVO imsiVO) {
		return dbShopDAO.getCategoryProductNameOneVO(imsiVO);
	}

	@Override
	public List<DbProductVO> getCategoryProductNameAjax(String categoryMainCode, String categoryMiddleCode, String categorySubCode) {
		return dbShopDAO.getCategoryProductNameAjax(categoryMainCode, categoryMiddleCode, categorySubCode);
	}

	@Override
	public DbProductVO getProductInfor(String productName) {
		return dbShopDAO.getProductInfor(productName);
	}

	@Override
	public List<DbOptionVO> getOptionList(int productIdx) {
		return dbShopDAO.getOptionList(productIdx);
	}

	@Override
	public int getOptionSame(int productIdx, String optionName) {
		return dbShopDAO.getOptionSame(productIdx, optionName);
	}

	@Override
	public int setDbOptionInput(DbOptionVO vo) {
		return dbShopDAO.setDbOptionInput(vo);
	}

	@Override
	public int setOptionDelete(int idx) {
		return dbShopDAO.setOptionDelete(idx);
	}

	@Override
	public DbProductVO getDbShopProduct(int idx) {
		return dbShopDAO.getDbShopProduct(idx);
	}

	@Override
	public List<DbOptionVO> getDbShopOption(int idx) {
		return dbShopDAO.getDbShopOption(idx);
	}

	@Override
	public DbCartVO getDbCartProductOptionSearch(String productName, String optionName, String mid) {
		return dbShopDAO.getDbCartProductOptionSearch(productName, optionName, mid);
	}

	@Override
	public int dbShopCartUpdate(DbCartVO vo) {
		return dbShopDAO.dbShopCartUpdate(vo);
	}

	@Override
	public int dbShopCartInput(DbCartVO vo) {
		return dbShopDAO.dbShopCartInput(vo);
	}

	@Override
	public List<DbCartVO> getDbCartList(String mid) {
		return dbShopDAO.getDbCartList(mid);
	}

	@Override
	public int dbCartDelete(int idx) {
		return dbShopDAO.dbCartDelete(idx);
	}

	@Override
	public DbOrderVO getOrderMaxIdx() {
		return dbShopDAO.getOrderMaxIdx();
	}

	@Override
	public DbCartVO getCartIdx(int idx) {
		return dbShopDAO.getCartIdx(idx);
	}

	@Override
	public void setDbOrder(DbOrderVO vo) {
		dbShopDAO.setDbOrder(vo);
	}

	@Override
	public void setDbCartDeleteAll(int cartIdx) {
		dbShopDAO.setDbCartDeleteAll(cartIdx);
	}

	@Override
	public void setDbBaesong(DbBaesongVO baesongVO) {
		dbShopDAO.setDbBaesong(baesongVO);
	}

	@Override
	public void setMemberPointPlus(int point, String mid) {
		dbShopDAO.setMemberPointPlus(point, mid);
	}

	@Override
	public int getTotalBaesongOrder(String orderIdx) {
		return dbShopDAO.getTotalBaesongOrder(orderIdx);
	}

	@Override
	public List<DbBaesongVO> getOrderBaesong(String orderIdx) {
		return dbShopDAO.getOrderBaesong(orderIdx);
	}

	@Override
	public List<DbBaesongVO> getMyOrderList(int startIndexNo, int pageSize, String mid) {
		return dbShopDAO.getMyOrderList(startIndexNo, pageSize, mid);
	}
	
}
