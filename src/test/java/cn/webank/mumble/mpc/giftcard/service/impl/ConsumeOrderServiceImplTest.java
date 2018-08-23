package cn.webank.mumble.mpc.giftcard.service.impl;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.service.mcd.ConsumeOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by leaflyhuang on 2017/5/3.
 */
public class ConsumeOrderServiceImplTest extends JunitBaseTest{

    @Autowired
    ConsumeOrderService consumeOrderService;

    @Test
    public void retryConsume() throws Exception {
        consumeOrderService.retryConsume();
    }

}