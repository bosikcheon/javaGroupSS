package com.spring.javaGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaGroupS.vo.DbBaesongVO;
import com.spring.javaGroupS.vo.DbCartVO;
import com.spring.javaGroupS.vo.DbOptionVO;
import com.spring.javaGroupS.vo.DbOrderVO;
import com.spring.javaGroupS.vo.DbProductVO;

public interface DbShopDAO {

	List<DbProductVO> getCategoryMain();

	List<DbProductVO> getCategoryMiddleName(@Param("categoryMainCode") String categoryMainCode);

	List<DbProductVO> getCategorySubName(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMiddleCode") String categoryMiddleCode);

	DbProductVO getProductMaxIdx();

	int setDbProductInput(@Param("vo") DbProductVO vo);

	List<DbProductVO> getSubTitle();

	List<DbProductVO> getDbShopList(@Param("part") String part, @Param("mainPrice") String mainPrice);

	DbProductVO getCategoryProductNameOne(@Param("productName") String productName);

	DbProductVO getCategoryProductNameOneVO(@Param("vo") DbProductVO imsiVO);

	List<DbProductVO> getCategoryProductNameAjax(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMiddleCode") String categoryMiddleCode, @Param("categorySubCode") String categorySubCode);

	DbProductVO getProductInfor(@Param("productName") String productName);

	List<DbOptionVO> getOptionList(@Param("productIdx") int productIdx);

	int getOptionSame(@Param("productIdx") int productIdx, @Param("optionName") String optionName);

	int setDbOptionInput(@Param("vo") DbOptionVO vo);

	int setOptionDelete(@Param("idx") int idx);

	DbProductVO getDbShopProduct(@Param("idx") int idx);

	List<DbOptionVO> getDbShopOption(@Param("idx") int idx);

	DbCartVO getDbCartProductOptionSearch(@Param("productName") String productName, @Param("optionName") String optionName, @Param("mid") String mid);

	int dbShopCartUpdate(@Param("vo") DbCartVO vo);

	int dbShopCartInput(@Param("vo") DbCartVO vo);

	List<DbCartVO> getDbCartList(@Param("mid") String mid);

	int dbCartDelete(@Param("idx") int idx);

	DbOrderVO getOrderMaxIdx();

	DbCartVO getCartIdx(@Param("idx") int idx);

	void setDbOrder(@Param("vo") DbOrderVO vo);

	void setDbCartDeleteAll(@Param("cartIdx") int cartIdx);

	void setDbBaesong(@Param("baesongVO") DbBaesongVO baesongVO);

	void setMemberPointPlus(@Param("point") int point, @Param("mid") String mid);

	int getTotalBaesongOrder(@Param("orderIdx") String orderIdx);

	List<DbBaesongVO> getOrderBaesong(@Param("orderIdx") String orderIdx);

	List<DbBaesongVO> getMyOrderList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("mid") String mid);

}
