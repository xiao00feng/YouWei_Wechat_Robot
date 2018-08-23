package cn.webank.mumble.mpc.giftcard.service.platform.impl;

import static org.testng.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.service.platform.MarketingService;

/**
 * Created by longmanli on 2018/4/18.
 */
public class MarketingServiceImplTest extends JunitBaseTest {
    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.service.platform.MarketingService")
    private MarketingService service;

    @Test
    public void testIsNewCardId() throws Exception {
        System.out.println("xxxxx:" + service.isNewCardId("123"));
    }
}