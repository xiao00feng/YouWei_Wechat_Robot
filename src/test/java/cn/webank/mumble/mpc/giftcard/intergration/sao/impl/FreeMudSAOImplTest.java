package cn.webank.mumble.mpc.giftcard.intergration.sao.impl;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.intergration.sao.FreeMudSAO;
import cn.webank.mumble.mpc.giftcard.service.impl.PlatformServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by leaflyhuang on 2017/4/25.
 */
public class FreeMudSAOImplTest extends JunitBaseTest {

    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.intergration.sao.FreeMudSAO")
    private FreeMudSAO freeMudSAO;

    @Test
    public void activeCode() throws Exception {
        freeMudSAO.activeCode(PlatformServiceImpl.PLATFROM_MCD,"poNV6xDWU7870hLKp12SLi740-1g","113650662565599", null);
    }

}