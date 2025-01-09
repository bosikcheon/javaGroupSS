package com.spring.javaGroupS.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javaGroupS.vo.DbBaesongVO;
import com.spring.javaGroupS.vo.DbCartVO;
import com.spring.javaGroupS.vo.DbOptionVO;
import com.spring.javaGroupS.vo.DbOrderVO;
import com.spring.javaGroupS.vo.DbProductVO;

public interface DbShopService {

	List<DbProductVO> getCategoryMain();

	List<DbProductVO> getCategoryMiddleName(String categoryMainCode);

	List<DbProductVO> getCategorySubName(String categoryMainCode, String categoryMiddleCode);

	int imgCheckProductInput(MultipartFile file, DbProductVO vo);

	List<DbProductVO> getSubTitle();

	List<DbProductVO> getDbShopList(String part, String mainPrice);

	DbProductVO getCategoryProductNameOne(String productName);

	DbProductVO getCategoryProductNameOneVO(DbProductVO imsiVO);

	List<DbProductVO> getCategoryProductNameAjax(String categoryMainCode, String categoryMiddleCode,
			String categorySubCode);

	DbProductVO getProductInfor(String productName);

	List<DbOptionVO> getOptionList(int productIdx);

	int getOptionSame(int productIdx, String optionName);

	int setDbOptionInput(DbOptionVO vo);

	int setOptionDelete(int idx);

	DbProductVO getDbShopProduct(int idx);

	List<DbOptionVO> getDbShopOption(int idx);

	DbCartVO getDbCartProductOptionSearch(String productName, String optionName, String mid);

	int dbShopCartUpdate(DbCartVO vo);

	int dbShopCartInput(DbCartVO vo);

	List<DbCartVO> getDbCartList(String mid);

	int dbCartDelete(int idx);

	DbOrderVO getOrderMaxIdx();

	DbCartVO getCartIdx(int idx);

	void setDbOrder(DbOrderVO vo);

	void setDbCartDeleteAll(int cartIdx);

	void setDbBaesong(DbBaesongVO baesongVO);

	void setMemberPointPlus(int point, String mid);

	int getTotalBaesongOrder(String orderIdx);

	List<DbBaesongVO> getOrderBaesong(String orderIdx);

	List<DbBaesongVO> getMyOrderList(int startIndexNo, int pageSize, String mid);

}
