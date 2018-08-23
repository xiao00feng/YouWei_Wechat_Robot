package cn.webank.mumble.mpc.giftcard.web.controller;

import cn.webank.mumble.framework.common.dto.BaseDTO;
import cn.webank.mumble.framework.common.dto.BizErrors;
import cn.webank.mumble.framework.web.controller.MumbleAbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class MpcBaseController extends MumbleAbstractBaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MpcBaseController.class);

    @Autowired
    @Qualifier("frontTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 超时时间
     */
    @Value("${web.timeout:7000}")
    protected long webTimeout;


    @Value("${sign.key}")
    private String signKey;

    protected String getSignKey(String appid) {
        if (StringUtils.isEmpty(appid)) {
            return null;
        }
        String[] signs = signKey.split(";");
        for (String sign : signs) {
            String[] signKeyValue = sign.split(":");
            if (appid.equals(signKeyValue[0])) {
                return signKeyValue[1];
            }
        }
        return null;
    }

    @Override
    protected ThreadPoolTaskExecutor getFrontTaskExecutor() {
        return threadPoolTaskExecutor;
    }

    @Override
    protected <E extends BaseDTO, T> void postService(E requestDto, T responseDto,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Method callerMethod,
            String requestUri, BizErrors errors) {

    }

    @Override
    protected String getBizSeqNo() {
        return null;
    }

    @Override
    protected String getTxnSeqNo() {
        return null;
    }

    @Override
    protected String getOrgSysId() {
        return null;
    }

    @Override
    protected String getDcnNo() {
        return null;
    }

}
