package cn.webank.mumble.mpc.giftcard;

import cn.webank.mumble.framework.common.utils.MumbleServerDefaultPropertiesUtil;
import cn.webank.mumble.mpc.giftcard.server.StartServer;
import org.apache.commons.lang3.StringUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Created by leaflyhuang on 2017/3/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StartServer.class})
public class JunitBaseTest {

    protected final static Logger LOGGER = LoggerFactory.getLogger(JunitBaseTest.class);

    static {
        String property = System.getProperty("spring.config.location");
        // 如果没有设置，给缺省值
        if (StringUtils.isBlank(property)) {
            System.setProperty("spring.config.location", "classpath:application.properties");
        }

        String logHome = System.getProperty("mumble.log.home");
        if (StringUtils.isBlank(logHome)) {
            System.setProperty("mumble.log.home", "./logs");
        }


        Map<String, Object> defaultConfigMap = MumbleServerDefaultPropertiesUtil.getinitialDefaultConfig();
        //workaround
        defaultConfigMap.put("spring.jackson.time-zone", "Asia/Shanghai");
        defaultConfigMap.entrySet().stream().forEach(entry -> {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.setProperty(key, value.toString());

        });


    }

}
