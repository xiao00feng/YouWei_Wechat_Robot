package cn.webank.mumble.mpc.giftcard.intergration.sao.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.fasterxml.jackson.databind.JavaType;
import cn.webank.mumble.framework.common.dto.BizErrors;
import cn.webank.mumble.framework.common.mapper.JsonMapper;
import cn.webank.mumble.framework.common.utils.MumbleContextUtil;
import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.common.dto.CodeInfoDTO;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.CheckReceiverAddressReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.CheckReceiverAddressRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.CheckSkuListInfo;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.OrderBookReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.OrderBookRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryOrderDetailReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryOrderDetailRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryOrderRouteReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryOrderRouteRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryWaybillNumberResponse;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherCheckReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherCheckRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherDiscountDetailInfo;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherOrderInfo;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherOrderProductInfo;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherVerificationReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherVerificationRsp;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.VoucherpayDetailInfo;
import cn.webank.mumble.mpc.giftcard.intergration.sao.CallSFSAO;
import cn.webank.mumble.mpc.giftcard.service.mcd.CodeInfoService;
import cn.webank.mumble.mpc.giftcard.service.mcd.GiftCardService;

public class CallSFSAOImplTest extends JunitBaseTest {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GiftCardService.class);
	private final static JsonMapper JSON_MAPPER = JsonMapper.nonEmptyMapper();
	
	@Autowired
	@Qualifier("cn.webank.mumble.mpc.giftcard.intergration.sao.CallSFSAO")
	CallSFSAO callSFSAO;
	
	@Autowired
	@Qualifier("cn.webank.mumble.mpc.giftcard.service.mcd.CodeInfoService")
	private CodeInfoService codeInfoService;
	
	@Before
	public void init(){
		String bizSeqNo=getBizSeqNo();
		LOGGER.info("-----bizSeqNo:{}",bizSeqNo);
		MumbleContextUtil.setBizSeqNo(bizSeqNo);
	}
	
	private String getBizSeqNo(){
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date())+RandomStringUtils.randomNumeric(6);
	}

	@Test
	public void testCheckReceiverAddress() {
		BizErrors bizErrors = new BizErrors();
		CheckReceiverAddressReq req = new CheckReceiverAddressReq();
		req.setThirdBrandId("WBCAD0002");
		req.setReceiverAddress("五道口");
		req.setReceiverCity("北京市");
		List<CheckSkuListInfo> list = new ArrayList<CheckSkuListInfo>();

		CheckSkuListInfo checkSkuListInfo = new CheckSkuListInfo();
		checkSkuListInfo.setSkuId("111"); // 商品编码
		checkSkuListInfo.setSkuCodeId("2017120501"); // code
		checkSkuListInfo.setSkuCount(Integer.valueOf("1"));
		checkSkuListInfo.setSkuName("测试商品");
		checkSkuListInfo.setSkuPrice(Integer.valueOf("100"));
		list.add(checkSkuListInfo);
		req.setSkuList(list);
		CheckReceiverAddressRsp addressRsp = callSFSAO.checkReceiverAddress(req, bizErrors);
	}

	@Test
	public void testOrderBook() {
		BizErrors bizErrors = new BizErrors();
		OrderBookReq orderBookReq=new OrderBookReq();
		orderBookReq.setThirdBrandId("WBCAD0002");
		orderBookReq.setReceiverCity("北京市");
		orderBookReq.setReceiverAddress("五道口");
		orderBookReq.setReceiverName("小明");
		orderBookReq.setReceiverPhone( "15111680080");
		List<CheckSkuListInfo> list = new ArrayList<CheckSkuListInfo>();
		CheckSkuListInfo checkSkuListInfo = new CheckSkuListInfo();
		checkSkuListInfo.setSkuId("111"); // 商品编码
		checkSkuListInfo.setSkuCodeId("201712050102"); // code
		checkSkuListInfo.setSkuCount(Integer.valueOf("1"));
		checkSkuListInfo.setSkuName("测试商品");
		checkSkuListInfo.setSkuPrice(Integer.valueOf("100"));
		list.add(checkSkuListInfo);
		orderBookReq.setSkuList(list);
		orderBookReq.setThirdOrderId(MumbleContextUtil.getBizSeqNo());
		OrderBookRsp orderBookRsp=callSFSAO.orderBook(orderBookReq, bizErrors);
		
	}

	@Test
	public void testQueryOrderDetail() {
		BizErrors bizErrors=new BizErrors();
		QueryWaybillNumberResponse response = new QueryWaybillNumberResponse();
		QueryOrderDetailReq queryOrderDetailReq = new QueryOrderDetailReq();
		queryOrderDetailReq.setThirdOrderId("20171206105626753407");
		QueryOrderDetailRsp rsp = callSFSAO.queryOrderDetail(queryOrderDetailReq, bizErrors);
	}

	@Test
	public void testQueryOrderRoute() {
		BizErrors bizErrors=new BizErrors();
		QueryOrderRouteReq queryOrderRouteReq = new QueryOrderRouteReq();
		queryOrderRouteReq.setThirdOrderId("20171206105626753407");
		QueryOrderRouteRsp rsp = callSFSAO.queryOrderRoute(queryOrderRouteReq, bizErrors);
		LOGGER.info("-->{}",JSON_MAPPER.toJson(rsp));
		
	}

	@Test
	public void testVoucherCheck() {
		BizErrors bizErrors=new BizErrors();
		VoucherCheckReq req=new VoucherCheckReq();	
		req.setVoucherNumber("121718778084452");
		VoucherCheckRsp rsp=callSFSAO.voucherCheck(req, bizErrors);
	}

	@Test
	public void testVoucherVerification() throws IOException {
		
		CodeInfoDTO codeInfoDTO = codeInfoService.getCodeInfo("196544801229369",null);
		BizErrors bizErrors=new BizErrors();
		
		VoucherCheckReq req=new VoucherCheckReq();	
		req.setVoucherNumber("101256917003725");
		VoucherCheckRsp rsp=callSFSAO.voucherCheck(req, bizErrors);
		
		VoucherVerificationReq verificationReq = new VoucherVerificationReq();
		verificationReq.setVoucherNumber("196544801229369");
		verificationReq.setStoreNumber("99998"); // 门店编号
		verificationReq.setTransId("12345698635241"); // 交易流水号（顺风下单）
		verificationReq.setStationId("001"); 
		VoucherOrderInfo voucherOrderInfo = new VoucherOrderInfo();
		voucherOrderInfo.setOrderNumber("12345698635241");
		voucherOrderInfo.setMemberNumber("");
		voucherOrderInfo.setAmountPaid(0); 
		voucherOrderInfo.setAmountRec(0);  
		String createTime = DateFormatUtils.format(new Date(),"yyyyMMddHHmmss");
		voucherOrderInfo.setCreateTime(createTime);
		List<VoucherOrderProductInfo> orderProducts=rsp.getOrderProducts();
		
		List<VoucherDiscountDetailInfo> list=new ArrayList<VoucherDiscountDetailInfo>();
		for(VoucherOrderProductInfo info:orderProducts ){
			info.setProductName("测试商品");
			VoucherDiscountDetailInfo detailInfo=new VoucherDiscountDetailInfo();
			detailInfo.setDiscountMoney(0);
			detailInfo.setLineNumber(info.getLineNumber());
			detailInfo.setDiscountType("115");
			detailInfo.setProductNumber(info.getProductNumber());
			list.add(detailInfo);
		}
		voucherOrderInfo.setOrderProducts(orderProducts);
		voucherOrderInfo.setDiscountDetails(list);
		voucherOrderInfo.setPayDetails(new ArrayList<VoucherpayDetailInfo>());
		
		verificationReq.setOrderInfo(voucherOrderInfo);
		VoucherVerificationRsp verificationRsp = callSFSAO.voucherVerification(verificationReq, bizErrors);
	}

}
