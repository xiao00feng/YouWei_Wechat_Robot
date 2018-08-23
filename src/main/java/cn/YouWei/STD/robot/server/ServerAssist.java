package cn.webank.mumble.mpc.giftcard.server;

import cn.webank.mpc.sdk.wemq.MpcWeMQProducer;
import cn.webank.mpc.sdk.wemq.MpcWeMQPushConsumer;
import cn.webank.mumble.framework.biz.server.MumbleServerAssist;
import cn.webank.mumble.framework.common.threads.MumbleThreadPoolTaskExecutor;
import cn.webank.mumble.mpc.giftcard.common.utils.HBaseClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * Created by calmanpan on 2016/10/10.
 */
@Component
@Lazy(false)
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ServerAssist extends MumbleServerAssist {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerAssist.class);

    @Autowired(required = false)
    private HBaseClientUtils hBaseClientUtils;


    @Autowired(required = false)
    private MpcWeMQProducer mpcWeMQProducer;


    @Autowired(required = false)
    @Qualifier("taskScheduler")
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    @Qualifier("frontTaskExecutor")
    private ThreadPoolTaskExecutor frontTaskExecutor;


    @Autowired
    @Qualifier("wemqTaskExecutor")
    private MumbleThreadPoolTaskExecutor wemqTaskExecutor;

    @Autowired(required = false)
    private MpcWeMQPushConsumer mpcWeMQPushConsumer;

    @Override
    public void doStart() throws Exception {
       
    }

    @Override
    public void doStop() throws Exception {
        LOGGER.info("server beging to stop...");

        //1.关闭http请求
        if (frontTaskExecutor != null) {
            frontTaskExecutor.shutdown();
        }

        if (threadPoolTaskScheduler != null) {
            threadPoolTaskScheduler.shutdown();
        }


        //3.关闭wemq消息监听
        if (mpcWeMQPushConsumer != null) {
            mpcWeMQPushConsumer.shutdown();
        }



        //4.关闭业务处理线程，因为可能调用publish消息
        if (wemqTaskExecutor != null) {
            wemqTaskExecutor.shutdown();
        }


        //5.关闭wemq publish消息
        if (mpcWeMQProducer != null) {
            mpcWeMQProducer.shutdown();
        }



        //6.关闭Hbase连接
        if (hBaseClientUtils != null) {
            hBaseClientUtils.shutdown();
        }

        LOGGER.info("server stop ok");


    }
}
