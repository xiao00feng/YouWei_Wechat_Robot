package cn.webank.mumble.mpc.giftcard.service.mcd.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import cn.webank.mumble.framework.common.dto.BizErrors;
import cn.webank.mumble.framework.common.mapper.JsonMapper;
import cn.webank.mumble.framework.common.utils.MumbleContextUtil;
import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.common.dto.CodeInfoDTO;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.MaintainAddressRequest;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.MaintainAddressResponse;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.OrderBookReq;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.OrderBookRequest;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.OrderBookResponse;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryAdressListRequest;
import cn.webank.mumble.mpc.giftcard.common.dto.mcd.QueryAdressListResponse;
import cn.webank.mumble.mpc.giftcard.service.mcd.CodeInfoService;
import cn.webank.mumble.mpc.giftcard.service.mcd.GiftCardService;
import cn.webank.mumble.mpc.giftcard.service.mcd.GiftCarddDispatchService;

public class GiftCarddDispatchServiceImplTest extends JunitBaseTest {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GiftCardService.class);
	private final static JsonMapper JSON_MAPPER = JsonMapper.nonEmptyMapper();
	
	@Autowired
	@Qualifier("cn.webank.mumble.mpc.giftcard.service.mcd.GiftCarddDispatchService")
	private GiftCarddDispatchService giftCarddDispatchService;
	
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
	public void testQueryOrderInfo() {
		
	}

	@Test
	public void testQueryAdressList() {
		MumbleContextUtil.setBizSeqNo("123456789");
		BizErrors bizErrors=new BizErrors();
		QueryAdressListRequest request=new QueryAdressListRequest();
		request.setOpenId("W00001");
		QueryAdressListResponse response=giftCarddDispatchService.queryAddressList(request, bizErrors);
		LOGGER.info("testQueryAdressList response: {}",JSON_MAPPER.toJson(response));
	}

	@Test
	public void testMaintainAddress() {
		for(int i=0;i<6;i++){
		String desc="测试"+String.valueOf(i);
		MumbleContextUtil.setBizSeqNo("123456789");
		BizErrors bizErrors = new BizErrors();
		MaintainAddressRequest request = new MaintainAddressRequest();
		request.setHandleFlag("A");
		//request.setRowId("052_20171130160215722249");// 主键
		request.setOpenId("W00001");// 用户微信open_id
		request.setUnionId("W00001");// 用户微信union_id
		request.setReceiverName(desc);// 收货人姓名
		request.setReceiverTelephone(desc);// 收货人电话
		request.setReceiverNationality(desc);// 国籍
		request.setReceiverProvince(desc);// 省份
		request.setReceiverCity("desc");// 市区（城市）
		request.setReceiverArea("desc");// 地区
		request.setReceiverLocation(desc);// 地址坐落
		request.setAddressStatus("N");// 状态（地址是否就可用） Y-有效 N—无效
		//request.setAddressSign("N");// 是否默认地址 Y-默认 N—否（默认值）
		MaintainAddressResponse response = giftCarddDispatchService.maintainAddress(request, bizErrors);
/*		request.setHandleFlag("S");
		request.setRowId("790_20171130163637714979");// 主键
		request.setOpenId("W00001");// 用户微信open_id
		request.setUnionId("W00001");// 用户微信union_id
//		request.setReceiverName("测试03");// 收货人姓名
//		request.setReceiverTelephone("测试03");// 收货人电话
//		request.setReceiverNationality("测试03");// 国籍
//		request.setReceiverProvince("测试03");// 省份
//		request.setReceiverCity("测试03");// 市区（城市）
//		request.setReceiverArea("测试03");// 地区
//		request.setReceiverLocation("测试03");// 地址坐落
//		request.setAddressStatus("Y");// 状态（地址是否就可用） Y-有效 N—无效
		//request.setAddressSign("N");// 是否默认地址 Y-默认 N—否（默认值）
		MaintainAddressResponse response=giftCarddDispatchService.maintainAddress(request, bizErrors);*/
		LOGGER.info("response: {}", JSON_MAPPER.toJson(response));}
	}

	@Test
	public void testOrderBook() throws IOException {
		CodeInfoDTO codeInfoDTO=new CodeInfoDTO();
		codeInfoDTO.setAppId("WBCAD0002");
		codeInfoDTO.setCode("115255698385396");
		codeInfoDTO.setPrice("0");
		codeInfoDTO.setCardId("P120520170901155201");
		codeInfoService.addCodeInfo(codeInfoDTO);
		
		LOGGER.info("codeInfoService response: {}", JSON_MAPPER.toJson(codeInfoService.getCodeInfo("115255698385396", null)));
		
		BizErrors bizErrors = new BizErrors();
		OrderBookRequest request=new OrderBookRequest();
		    request.setCode("115255698385396");
			//request.setCardId();
			request.setOpenId("waldo88168");
			request.setReceiverName("波波");
			request.setReceiverTelephone("15111680080");// 收货人电话
			//request.setReceiverNationality();// 国籍
			request.setReceiverProvince("北京市");// 省份
			request.setReceiverCity("北京市");// 市区（城市）
			request.setReceiverArea("五道口");// 地区
			request.setReceiverLocation("123");// 地址坐落
			//request.setRowId();// 地址主键 add 校验地址无效时删除地址
			request.setAppId("WBCAD0002");
		OrderBookResponse response=giftCarddDispatchService.orderBook(request, bizErrors);
		LOGGER.info("response: {}", JSON_MAPPER.toJson(response));
	}

	@Test
	public void testOrderBookCallBack() {
		
	}

	@Test
	public void testQueryWaybillNumber() {
		
	}

	@Test
	public void testQueryOrderRoute() {
	
	}

}
