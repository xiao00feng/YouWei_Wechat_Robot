package cn.webank.mumble.mpc.giftcard.service.impl;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.service.PlatformTableService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PlatformTableServiceImplTest extends JunitBaseTest {

    @Autowired
    private PlatformTableService tableService;

    @Test
    public void addPlatform() throws Exception {
        String a = "abc:123";
        System.out.println(a.split(":")[0]);

//        tableService.addPlatform("W1806277");
//        tableService.addPlatform("W3875803");
//        tableService.addPlatform("W1505752");
//        tableService.addPlatform("W2756643");
    }

}