package cn.webank.mumble.mpc.giftcard.service.patch.impl;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.service.patch.GetUserPatchService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetUserPatchServiceImplTest  extends JunitBaseTest{

    @Autowired
    private GetUserPatchService getUserPatchService;

    @Test
    public void patchUser() throws Exception {
        getUserPatchService.patchUser();
    }

}