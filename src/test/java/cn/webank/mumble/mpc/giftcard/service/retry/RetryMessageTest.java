package cn.webank.mumble.mpc.giftcard.service.retry;

import cn.webank.mumble.framework.common.mapper.JsonMapper;
import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import cn.webank.mumble.mpc.giftcard.common.dto.GiftCardWeMQReSendDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by leaflyhuang on 2017/4/20.
 */
public class RetryMessageTest extends JunitBaseTest {

    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.service.retry.GiftCardWeMQRetryMessage")
    private GiftCardWeMQRetryMessage retryMessage;


    @Test
    public void testPushMessageWithHbase() throws Exception {
        for (int i = 0; i < 50; i++) {
            GiftCardWeMQReSendDTO giftCardWeMQReSendDTO = new GiftCardWeMQReSendDTO("test-x005-topic", "*", "request-test" + i);
            giftCardWeMQReSendDTO.setLastSendTimeStamp(System.currentTimeMillis());
            retryMessage.pushMessage(JsonMapper.nonEmptyMapper().toJson(giftCardWeMQReSendDTO));
        }

    }

    @Test
    public void testBatchbatchPopMessageWithHbase() throws Exception {

    }

}