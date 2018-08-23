package cn.webank.mumble.mpc.giftcard.web.controller;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author ctrlzhang on 2017/4/20
 */
public class McdGiftCardControllerTest extends JunitBaseTest {
    private static void fill(String key, String val) {
        String property = System.getProperty(key);
        if (StringUtils.isBlank(property)) {
            System.setProperty(key, val);
        }
    }

    static {
        fill("tbl.order.info", "test");
        fill("tbl.order.status", "test");
        fill("tbl.code.info", "test");
        fill("tbl.code.status", "test");
        fill("wechat.url", "test");
    }

    @Test
    public void testCardCodeConsume() throws Exception {
        System.out.println("xxxx");
    }
}