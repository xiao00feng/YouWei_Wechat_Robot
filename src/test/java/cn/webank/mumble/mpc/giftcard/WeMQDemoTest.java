package cn.webank.mumble.mpc.giftcard;

import cn.webank.mpc.sdk.wemq.MpcDeferResultCallbak;
import cn.webank.mpc.sdk.wemq.MpcWeMQProducer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by leaflyhuang on 2017/4/18.
 */
public class WeMQDemoTest extends JunitBaseTest {

    @Test
    public void testDemo() {
        MpcWeMQProducer producer = new MpcWeMQProducer("wemq","wemq@123","1015", "10.107.102.121:9876");

        DeferredResult<String> result = new DeferredResult<String>();

        for(int i=0;i<5;i++) {
            Message msg = new Message("GIFTCARD_BUY_BJ",// topic
                    "*",// tag
                    (
"{\"toUserName\":\"gh_93eced8f995e\",\"fromUserName\":\"ooNV6xOEVHB5Qo103y8wmxEi29WQ\",\"createTime\":\"1504330508\",\"messageType\":\"event\",\"event\":\"giftcard_pay_done\",\"pageId\":\"YmFti4GK0vUFnkPMayIFqNVZu82ulXJL0X1XRHrGmD4=\",\"orderId\":\"AQCAbuWKIK-aAFhkBqDVesQcptVi\",\"authorizerAppId\":\"wxbb7d5414d333f641\",\"openId\":\"ooNV6xOEVHB5Qo103y8wmxEi29WQ\",\"unionId\":\"oNawNt4R30GWfCSWPLeUiUJfRg_g\"}"

                    ) .getBytes()// body
            );

            try {
                producer.publish(msg, new MpcDeferResultCallbak<String>(result) {


                    @Override
                    public void onSuccess(SendResult sendResult) {
                        this.getDeferredResult().setResult("success");
                        LOGGER.warn("##########onSuccess");
                    }

                    @Override
                    public void onException(Throwable e) {
                        this.getDeferredResult().setResult("fail");
                        LOGGER.warn("##########failed");
                    }
                });
            } catch (MQClientException | RemotingException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(result.getResult());
    }
}
