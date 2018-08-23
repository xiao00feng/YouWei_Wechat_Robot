package cn.webank.mumble.mpc.giftcard.service.mcd.impl;

import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.service.mcd.QrCodeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QrCodeServiceImplTest extends JunitBaseTest {

    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.service.mcd.QrCodeService")
    private QrCodeService qrCodeService;

    @Test
    public void getQrcode() throws Exception {
//        QrCodeResponseDTO qrCodeResponseDTO = qrCodeService.getQrcode("41100117080017020974");
//        LOGGER.info(JsonMapper.nonDefaultMapper().toJson(qrCodeResponseDTO));
//        Thread.sleep(61 * 1000 );
//        String code = qrCodeService.isValidQrcode(qrCodeResponseDTO.getQrCode());
//        LOGGER.info("code:{}", code);
    }

    @Test
    public void isValidQrcode() throws Exception {
    }

}