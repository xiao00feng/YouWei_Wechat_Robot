package cn.webank.mumble.mpc.giftcard.service.impl;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.common.dto.OrderInfoDTO;
import cn.webank.mumble.mpc.giftcard.common.dto.ReceiptStatusRequestDTO;
import cn.webank.mumble.mpc.giftcard.intergration.sao.ReceiptSAO;
import cn.webank.mumble.mpc.giftcard.service.platform.ReceiptInfoService;
import cn.webank.mumble.mpc.giftcard.service.platform.ReceiptOrderService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by nickkli on 2017/9/14.
 */
public class ReceiptInfoServiceImplTest extends JunitBaseTest {

    @Autowired
    ReceiptInfoService receiptInfoService;

    @Autowired
    ReceiptOrderService receiptOrderService;

    @Autowired
    ReceiptSAO receiptSAO;

    @Test
    public void retryConsume() throws Exception {
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setOpenId("open1");
        orderInfoDTO.setOrderId("order1");
        orderInfoDTO.setTotalPrice("100");
        orderInfoDTO.setPayFinishTime("20170904152629");
        receiptOrderService.addReceiptOrder(orderInfoDTO);
//        ReceiptStatusRequestDTO requestDTO = new ReceiptStatusRequestDTO();
//        List<String> orderList = Lists.newArrayList();
//        orderList.add("order1");
//        orderList.add("order2");
//        orderList.add("order3");
//        requestDTO.setOrderList(orderList);
//        receiptSAO.check(requestDTO);
    }
}
