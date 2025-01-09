package com.spring.javaGroupS.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaGroupS.common.JavaGroupProvide;
import com.spring.javaGroupS.pagination.PageProcess;
import com.spring.javaGroupS.service.DbShopService;
import com.spring.javaGroupS.service.MemberService;
import com.spring.javaGroupS.vo.DbBaesongVO;
import com.spring.javaGroupS.vo.DbCartVO;
import com.spring.javaGroupS.vo.DbOptionVO;
import com.spring.javaGroupS.vo.DbOrderVO;
import com.spring.javaGroupS.vo.DbPayMentVO;
import com.spring.javaGroupS.vo.DbProductVO;
import com.spring.javaGroupS.vo.MemberVO;

@Controller
@RequestMapping("/dbShop")
public class DbShopController {
	
	@Autowired
	DbShopService dbShopService;
	
	@Autowired
	PageProcess pageProcess;
		
	@Autowired
	JavaGroupProvide javaGroupProvide;
	
	@Autowired
	MemberService memberService;
	
	// 대분류 선택하면 중분류항목 가져오기
	@ResponseBody
	@RequestMapping(value = "/categoryMiddleName", method = RequestMethod.POST)
	public List<DbProductVO> categoryMiddleNamePost(String categoryMainCode) {
		return dbShopService.getCategoryMiddleName(categoryMainCode);
	}
	
	// 중분류 선택시에 소분류항목명을 가져오기
	@ResponseBody
	@RequestMapping(value = "/categorySubName", method = RequestMethod.POST)
	public List<DbProductVO> categorySubNamePost(String categoryMainCode, String categoryMiddleCode) {
		return dbShopService.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}
	
  // 상품 등록을 위한 폼 보기..
	@RequestMapping(value = "/dbProduct", method=RequestMethod.GET)
	public String dbProductGet(Model model) {
		List<DbProductVO> mainVos = dbShopService.getCategoryMain();
		model.addAttribute("mainVos", mainVos);
		return "admin/dbShop/dbProduct";
	}
	
	// 상품 등록 처리하기
	@RequestMapping(value = "/dbProduct", method=RequestMethod.POST)
	public String dbProductPost(MultipartFile file, DbProductVO vo) {
		// 이미지파일 업로드시에 ckeditor폴더에서 'dbShop/product'폴더로 복사처리...후~ 처리된 내용을 DB에 저장하기
		int res = dbShopService.imgCheckProductInput(file, vo);
		
		if(res != 0) return "redirect:/message/dbProductInputOk";
		return "redirect:/message/dbProductInputNo";
	}
	
	@RequestMapping(value = "/dbShopList", method = RequestMethod.GET)
	public String dbShopListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="mainPrice", defaultValue = "0", required = false) String mainPrice){
		List<DbProductVO> subTitleVOS = dbShopService.getSubTitle();
		model.addAttribute("subTitleVOS", subTitleVOS);
		model.addAttribute("part", part);

		List<DbProductVO> productVOS = dbShopService.getDbShopList(part, mainPrice);
		model.addAttribute("productVOS", productVOS);
		model.addAttribute("price", mainPrice);
		
		return "admin/dbShop/dbShopList";
	}
	
	// 관리자 왼쪽메뉴에서 '옵션등록관리' 호출시 각 제품에 따른 상세보기를 한후 옵션 등록폼 보여주기
	@RequestMapping(value = "/dbOption", method = RequestMethod.GET)
	public String dbOptionGet(Model model,
			@RequestParam(name="productName", defaultValue = "", required=false) String productName) {
		if(productName.equals("")) {
			List<DbProductVO> mainVos = dbShopService.getCategoryMain();
			model.addAttribute("mainVos", mainVos);
		}
		else {
			DbProductVO imsiVO = dbShopService.getCategoryProductNameOne(productName);
			DbProductVO productVO = dbShopService.getCategoryProductNameOneVO(imsiVO);
			model.addAttribute("productVO", productVO);
		}
		return "admin/dbShop/dbOption";
	}
	
	// 소분류 선택시에 해당 상품명(모델명)을 가져오기
	@ResponseBody
	@RequestMapping(value = "/categoryProductName", method = RequestMethod.POST)
	public List<DbProductVO> categoryProductNameGet(String categoryMainCode, String categoryMiddleCode, String categorySubCode) {
		return dbShopService.getCategoryProductNameAjax(categoryMainCode, categoryMiddleCode, categorySubCode);
	}
	
	// '옵션등록폼'에서 상품선택시에 해당상품의 옵션리스트를 가져와서 출력처리...
	@ResponseBody
	@RequestMapping(value = "/getProductInfor", method = RequestMethod.POST)
	public DbProductVO getProductInforGet(String productName) {
		return dbShopService.getProductInfor(productName);
	}
	
	// '옵션보기'클릭시 해당 상품의 옵션리스트를 보여준다.(이때 옵션항목을 클릭하면 해당 옵션을 삭제처리하도록 설계~)
	@ResponseBody
	@RequestMapping(value = "/getOptionList", method = RequestMethod.POST)
	public List<DbOptionVO> getOptionListPost(int productIdx) {
		return dbShopService.getOptionList(productIdx);
	}
	
	// 옵션에 기록한 내용들을 DB에 등록처리
	@RequestMapping(value = "/dbOption", method = RequestMethod.POST)
	public String dbOptionPost(Model model, DbOptionVO vo, String[] optionName, int[] optionPrice) {
		//System.out.println("DbOptionVO : " + vo);
		int res = 0;
		for(int i=0; i<optionName.length; i++) {
			int optionCnt = dbShopService.getOptionSame(vo.getProductIdx(), optionName[i]);
			if(optionCnt != 0) continue;
			
			// 동일한 옵션이 없다면 vo에 현재 옵션 이름과 가격을 set시킨후 옵션테이블에 등록처리한다.
			vo.setProductIdx(vo.getProductIdx());
			vo.setOptionName(optionName[i]);
			vo.setOptionPrice(optionPrice[i]);
			
			res = dbShopService.setDbOptionInput(vo);
		}
		model.addAttribute("tempFlag", vo.getProductName());
		if(res != 0) return "redirect:/message/dbOptionInputOk";
		else return "redirect:/message/dbOptionInputNo";
	}
	
	// 옵션 내역 삭제하기
	@ResponseBody
	@RequestMapping(value="/optionDelete", method = RequestMethod.POST)
	public String optionDeletePost(int idx) {
		int res = dbShopService.setOptionDelete(idx);
		return res + "";
	}
	
	// 진열된 상품을 클릭하였을때 해당 상품의 상세내역보기...
	@RequestMapping(value = "/dbShopContent", method = RequestMethod.GET)
	public String dbShopContentGet(Model model, int idx) {
		DbProductVO productVO = dbShopService.getDbShopProduct(idx);			// 상품 1건의 정보를 불러온다.
		List<DbOptionVO> optionVOS = dbShopService.getDbShopOption(idx);	// 해당 상품의 모든 옵션 정보를 가져온다.
		
		model.addAttribute("productVO", productVO);
		model.addAttribute("optionVOS", optionVOS);
		 
		return "admin/dbShop/dbShopContent";
	}




	/*  =========== 위쪽은 관리자 처리 화면입니다.......  ====================================================================================== */
	
	
	
	/*  =========== 아래쪽은 사용자(고객) 처리 화면입니다.......  ====================================================================================== */

	// 등록된 상품 진열하기(보여주기) - 고객창에 출력
	@RequestMapping(value = "/dbProductList", method = RequestMethod.GET)
	public String dbProductListGet(HttpServletRequest request, Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="mainPrice", defaultValue = "0", required = false) String mainPrice){
		
		// 쿠키에 최근검색한 상품을 읽어와서 퀵메뉴에 출력시켜준다.
		Cookie[] cookies = request.getCookies();
		String productList = "";
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cProduct")) {
					productList = cookies[i].getValue();
					if(productList.indexOf(":") != -1) productList = productList.substring(0, productList.length()-1);
					break;
				}
			}
		}
		
		String[] cookieArr = productList.split(":");
		List<DbProductVO> cookieVos = new ArrayList<DbProductVO>();
		for(String coo : cookieArr) {
			if(!coo.equals("")) {
				DbProductVO vo = dbShopService.getDbShopProduct(Integer.parseInt(coo));
				cookieVos.add(vo);
			}
		}
		model.addAttribute("cookieVos", cookieVos);
		
		
		List<DbProductVO> subTitleVOS = dbShopService.getSubTitle();	// 소분류명을 가져온다.
		model.addAttribute("subTitleVOS", subTitleVOS);
		model.addAttribute("part", part);

		List<DbProductVO> productVOS = dbShopService.getDbShopList(part, mainPrice);	// 전체 상품리스트 가져오기
		model.addAttribute("productVOS", productVOS);
		model.addAttribute("price", mainPrice);
		
		return "dbShop/dbProductList";
	}
	
	// 진열상품 클릭시 상세보기 화면처리
	@RequestMapping(value="/dbProductContent", method=RequestMethod.GET)
	public String dbProductContentGet(HttpServletRequest request, HttpServletResponse response, int idx, Model model) {
		// 최근본 상품을 Cookie에 저장하기
		Cookie[] cookies = request.getCookies();
		String productList = "";
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cProduct")) {
					productList = cookies[i].getValue();
					break;
				}
			}
		}
		if(productList.equals("") || productList.indexOf(idx+"") == -1) {
			String[] cookieArr = productList.split(":");
			if(cookieArr.length > 2) {
				productList = productList.substring(productList.indexOf(":")+1);
			}
			
			Cookie cookieProduct = new Cookie("cProduct", productList + idx + ":");
			cookieProduct.setPath("/");
			cookieProduct.setMaxAge(60*60*24*7);
			response.addCookie(cookieProduct);
		}
		
		
		DbProductVO productVO = dbShopService.getDbShopProduct(idx);			// 상품의 상세정보 불러오기
		List<DbOptionVO> optionVos = dbShopService.getDbShopOption(idx);	// 옵션의 모든 정보 불러오기
		
		model.addAttribute("productVO", productVO);
		model.addAttribute("optionVos", optionVos);
		
		return "dbShop/dbProductContent";
	}
	
	// 최근에 본 상품 쿠키에서 제거하기
	@ResponseBody
	@RequestMapping(value = "/cookieProductDelete", method = RequestMethod.POST)
	public void cookieProductDeletePost(HttpServletRequest request, HttpServletResponse response, int idx) {
		Cookie[] cookies = request.getCookies();
		String productList = "";
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cProduct")) {
					productList = cookies[i].getValue();
					
					productList= productList.replace(idx+":", "");
					Cookie cookieProduct = new Cookie("cProduct", productList);
					cookieProduct.setPath("/");
					cookieProduct.setMaxAge(60*60*24*7);
					response.addCookie(cookieProduct);
					
					break;
				}
			}
		}
	}

	// 장바구니 담기
	@RequestMapping(value="/dbProductContent", method=RequestMethod.POST)
	public String dbProductContentPost(HttpSession session, DbCartVO vo) {
		String mid = (String) session.getAttribute("sMid");
		
		//  예:  기본품목,정수필터,필터
		DbCartVO resVO = dbShopService.getDbCartProductOptionSearch(vo.getProductName(), vo.getOptionName(), mid);	// 지금 구매한 항목을 기존 구매 카트에서와 비교를 위해검색
		
		int res = 0;
		if(resVO != null) {	// 같은 제품(기본품목+옵션항목)일경우는 수량만 업데이트처리한다.
			String[] voOptionNums = vo.getOptionNum().split(",");
			String[] resOptionNums = resVO.getOptionNum().split(",");
			int[] nums = new int[99];
			String strNums = "";
			for(int i=0; i<voOptionNums.length; i++) {
				nums[i] += (Integer.parseInt(voOptionNums[i]) + Integer.parseInt(resOptionNums[i]));
				strNums += nums[i] + ",";
			}
			strNums = strNums.substring(0,strNums.length()-1);
			
			vo.setOptionNum(strNums);
			res = dbShopService.dbShopCartUpdate(vo);
		}
		else {
			res = dbShopService.dbShopCartInput(vo);
		}
		
		return "redirect:/dbShop/dbCartList";
	}
	
	// 장바구니 보기
	@RequestMapping(value="/dbCartList", method=RequestMethod.GET)
	public String dbCartGet(HttpSession session, DbCartVO vo, Model model) {
		String mid = (String) session.getAttribute("sMid");
		List<DbCartVO> vos = dbShopService.getDbCartList(mid);	// idx오름차순으로 정리해서 가져왔다.
		
		if(vos.size() == 0) return "redirect:/message/cartEmpty";
		
		model.addAttribute("cartListVos", vos);
		return "dbShop/dbCartList";
	}
	
	// 장바구니에서 주문한 품목 취소처리
	@ResponseBody
	@RequestMapping(value="/dbCartDelete", method=RequestMethod.POST)
	public String dbCartDeleteGet(int idx) {
		return dbShopService.dbCartDelete(idx) + "";
	}
	
	@RequestMapping(value="/dbCartList", method=RequestMethod.POST)
	public String dbCartPost(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(name="baesong", defaultValue="0", required=false) int baesong) {
		String mid = (String) session.getAttribute("sMid");
		
		// 주문한 상품에 대한 '주문 고유번호'를 만들어준다.
		DbOrderVO maxIdx = dbShopService.getOrderMaxIdx();
		int idx = 1;
    if(maxIdx != null) idx = maxIdx.getMaxIdx() + 1;

    // 주문번호 만들기
    Date today = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String orderIdx = sdf.format(today) + idx;

    // 장바구니에서 여러항목 구매시 체크된 항목만 넘어온다.
    String[] idxChecked = request.getParameterValues("idxChecked");

    DbCartVO cartVO = new DbCartVO();
    List<DbOrderVO> orderVos = new ArrayList<DbOrderVO>();
		
    for(String strIdx : idxChecked) {
    	cartVO = dbShopService.getCartIdx(Integer.parseInt(strIdx));
      DbOrderVO orderVO = new DbOrderVO();
      orderVO.setProductIdx(cartVO.getProductIdx());
      orderVO.setProductName(cartVO.getProductName());
      orderVO.setMainPrice(cartVO.getMainPrice());
      orderVO.setThumbImg(cartVO.getThumbImg());
      orderVO.setOptionName(cartVO.getOptionName());
      orderVO.setOptionPrice(cartVO.getOptionPrice());
      orderVO.setOptionNum(cartVO.getOptionNum());
      orderVO.setTotalPrice(cartVO.getTotalPrice());
      orderVO.setCartIdx(cartVO.getIdx());
      orderVO.setBaesong(baesong);

      orderVO.setOrderIdx(orderIdx); 
      orderVO.setMid(mid);

      orderVos.add(orderVO);
    }
    
    session.setAttribute("sOrderVos", orderVos);

    MemberVO memberVO = memberService.getMemberIdCheck(mid);
    model.addAttribute("memberVO", memberVO);
    
		return "dbShop/dbOrder";
	}
	
	// 결제시스템(결제 API호출)
	@RequestMapping(value="/payment", method=RequestMethod.POST)
	public String paymentPost(DbOrderVO orderVO, DbPayMentVO payMentVO, DbBaesongVO baesongVO, HttpSession session, Model model) {
		model.addAttribute("payMentVO", payMentVO);
		
		session.setAttribute("sPayMentVO", payMentVO);
		session.setAttribute("sBaesongVO", baesongVO);
		
		return "dbShop/paymentOk";
	}
	
	@RequestMapping(value="/paymentResult", method=RequestMethod.GET)
	public String paymentResultGet(HttpSession session, DbPayMentVO receivePayMentVO, Model model) {
		List<DbOrderVO> orderVos = (List<DbOrderVO>) session.getAttribute("sOrderVos");
		DbPayMentVO payMentVO = (DbPayMentVO) session.getAttribute("sPayMentVO");
		DbBaesongVO baesongVO = (DbBaesongVO) session.getAttribute("sBaesongVO");
		
		session.removeAttribute("sBaesongVO");
		
		for(DbOrderVO vo : orderVos) {
			vo.setIdx(Integer.parseInt(vo.getOrderIdx().substring(8)));
			vo.setOrderIdx(vo.getOrderIdx());
			vo.setMid(vo.getMid());							
			
			dbShopService.setDbOrder(vo);
			dbShopService.setDbCartDeleteAll(vo.getCartIdx());
		}
		
		baesongVO.setOIdx(orderVos.get(0).getIdx());
		baesongVO.setOrderIdx(orderVos.get(0).getOrderIdx());
		baesongVO.setAddress(payMentVO.getBuyer_addr());
		baesongVO.setTel(payMentVO.getBuyer_tel());
		
		int totalBaesongOrder = 0;
		for(int i=0; i<orderVos.size(); i++) {
			totalBaesongOrder += orderVos.get(i).getTotalPrice();
		}
		
		if(totalBaesongOrder < 50000) baesongVO.setOrderTotalPrice(totalBaesongOrder + 3000);
		else baesongVO.setOrderTotalPrice(totalBaesongOrder);
		
		dbShopService.setDbBaesong(baesongVO);
		dbShopService.setMemberPointPlus((int)(baesongVO.getOrderTotalPrice() * 0.01), orderVos.get(0).getMid());
		
		payMentVO.setImp_uid(receivePayMentVO.getImp_uid());
		payMentVO.setMerchant_uid(receivePayMentVO.getMerchant_uid());
		payMentVO.setPaid_amount(receivePayMentVO.getPaid_amount());
		payMentVO.setApply_num(receivePayMentVO.getApply_num());
		
		session.setAttribute("sPayMentVO", payMentVO);
		session.setAttribute("orderTotalPrice", baesongVO.getOrderTotalPrice());
		
		return "redirect:/message/paymentResultOk";
	}
	
	// 결제완료후 주문내역을 화면에 출력시켜준다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/paymentResultOk", method=RequestMethod.GET)
	public String paymentResultOkGet(HttpSession session, Model model) {
		List<DbOrderVO> orderVos = (List<DbOrderVO>) session.getAttribute("sOrderVos");
		model.addAttribute("orderVos", orderVos);
		session.removeAttribute("sOrderVos");
		
		int totalBaesongOrder = dbShopService.getTotalBaesongOrder(orderVos.get(orderVos.size()-1).getOrderIdx());
		model.addAttribute("totalBaesongOrder", totalBaesongOrder);
		
		return "dbShop/paymentResult";
	}
	
	// 배송지 정보를 팝업창으로 보여준다.
	@RequestMapping(value="/dbOrderBaesong", method=RequestMethod.GET)
	public String dbOrderBaesongGet(String orderIdx, Model model) {
		List<DbBaesongVO> vos = dbShopService.getOrderBaesong(orderIdx);
		
		DbBaesongVO vo = vos.get(0);
		String payMethod = "";
		if(vo.getPayment().substring(0,1).equals("C")) payMethod = "카드결제";
		else payMethod = "은행(무통장)결제";
		
		model.addAttribute("payMethod", payMethod);
		model.addAttribute("vo", vo);
		
		return "dbShop/dbOrderBaesong";
	}
	
	@RequestMapping(value="/dbMyOrder", method=RequestMethod.GET)
	public String dbMyOrderGet(HttpServletRequest request, HttpSession session, Model model,
			String startJumun, String endJumun, 
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
  	  @RequestParam(name="conditionOrderStatus", defaultValue="전체", required=false) String conditionOrderStatus) {
		String mid = (String) session.getAttribute("sMid");
		int level = (int) session.getAttribute("sLevel");
		if(level == 0) mid = "전체";
		
		//PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "dbMyOrder", mid, "");
		
		// 오늘 구매한 내역을 초기화면에 보여준다.
		//List<DbBaesongVO> vos = dbShopService.getMyOrderList(pageVO.getStartIndexNo(), pageSize, mid);
		List<DbBaesongVO> vos = dbShopService.getMyOrderList(0, 100, mid);
		
		model.addAttribute("vos", vos);				
		model.addAttribute("startJumun", startJumun);
		model.addAttribute("endJumun", endJumun);
		model.addAttribute("conditionOrderStatus", conditionOrderStatus);
		//model.addAttribute("pageVO", pageVO);
		
		return "dbShop/dbMyOrder";
	}
}