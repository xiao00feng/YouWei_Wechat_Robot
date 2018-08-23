package cn.webank.mumble.mpc.giftcard.service.impl;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.common.dto.CardIdInfoDTO;
import cn.webank.mumble.mpc.giftcard.service.mcd.CardIdService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;

/**
 * Created by leaflyhuang on 2017/4/26.
 */
public class CardIdServiceImplTest extends JunitBaseTest {

    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.service.mcd.CardIdService")
    private CardIdService cardIdService;

    @Test
    public void isGiftCard() throws Exception {
        Assert.assertTrue(cardIdService.isGiftCard("poNV6xGBT1pWu_SLetNJJItmNcF8"));
        Assert.assertFalse(cardIdService.isGiftCard("poNV6xGBT1pWu_SLetNJJItmNcF"));
    }

    @Test
    public void addCardId() {
        CardIdInfoDTO cardIdInfoDTO = new CardIdInfoDTO();
        cardIdInfoDTO.setPlatformId(PlatformServiceImpl.PLATFROM_MCD);
        cardIdInfoDTO.setCardId("test");
        cardIdInfoDTO.setStatus("A");
        try {
            cardIdService.addCardId(cardIdInfoDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}