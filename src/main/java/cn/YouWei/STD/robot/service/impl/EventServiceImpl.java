package cn.webank.mumble.mpc.giftcard.service.impl;

import cn.webank.mpc.sdk.wemq.annotation.MpcWeMQService;
import cn.webank.mpc.sdk.wemq.dto.MpcWeMQServiceConfig;
import cn.webank.mumble.framework.biz.service.MumbleBaseService;
import cn.webank.mumble.framework.common.dto.MonitorKeys;
import cn.webank.mumble.framework.common.mapper.JsonMapper;
import cn.webank.mumble.framework.common.utils.MumbleContextUtil;
import cn.webank.mumble.mpc.giftcard.common.annotation.WechatEventMethod;
import cn.webank.mumble.mpc.giftcard.common.dto.BaseResponseDTO;
import cn.webank.mumble.mpc.giftcard.common.dto.PlatformDTO;
import cn.webank.mumble.mpc.giftcard.service.EventService;
import cn.webank.mumble.mpc.giftcard.service.PlatformService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leaflyhuang on 2017/5/12.
 */
@Service("cn.webank.mumble.mpc.giftcard.service.EventService")
public class EventServiceImpl implements EventService, MumbleBaseService, InitializingBean, ApplicationContextAware {

    private final static Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private final static JsonMapper JSON_MAPPER = JsonMapper.nonEmptyMapper();

    private static final Logger MONITOR_LOGGER = LoggerFactory.getLogger(MonitorKeys.APP_MONITOR_LOGGER);

    private ApplicationContext context;

    @Value("${system.env:}")
    private String env;

    private final static Map<String, MpcWeMQServiceConfig> serviceMapping =
            new ConcurrentHashMap<String, MpcWeMQServiceConfig>();

    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.service.PlatformService")
    private PlatformService platformService;

    @Override
    @MpcWeMQService(topics = {"CARD_EVENT"}, requestMessageClass = Map.class)
    public BaseResponseDTO handleMessage(Map map) {
        BaseResponseDTO responseDTO = new BaseResponseDTO();

        long startTime = System.currentTimeMillis();
        LOGGER.debug("handleMessage begin:{}", map);
        if (map == null) {
            return responseDTO;
        }
        String event = (String) map.get("event");
        String authorizerAppId = (String) map.get("authorizerAppId");

        if (StringUtils.isBlank(event) || StringUtils.isBlank(authorizerAppId)) {
            LOGGER.warn("request event orauthorizerAppId  is null :{}", map);
            return responseDTO;
        }

        String platformId = "";
        PlatformDTO platformDTO = null;
        if ("sit".equals(env) && StringUtils.isNotBlank((String) map.get("pageId"))) {
            platformDTO = platformService.queryPlatformByPageId((String) map.get("pageId"));
            LOGGER.info("env:{}, pageId:{}, platform:{}", env, map.get("pageId"), platformDTO);
        } else {
            platformDTO = platformService.queryPlatformByAuthAppid(authorizerAppId);
        }

        if ("sit".equals(env) && !"giftcard_pay_done;user_consume_card".contains(event)) {
            //麦当劳
            platformDTO = new PlatformDTO();
            platformDTO.setPlatformId(PlatformServiceImpl.PLATFROM_MCD);
            map.put("platformId", PlatformServiceImpl.PLATFROM_MCD);
        }

        if (platformDTO != null && !platformDTO.getPlatformId().equals(PlatformServiceImpl.PLATFROM_MCD)) {
            //不为麦当劳产品
            event = "plat_" + event;
            map.put("platformId", platformDTO.getPlatformId());
            map.put("platformName", platformDTO.getPlatformName());
            map.put("authorizerAppId", platformDTO.getAuthorizerAppId());
            platformId = platformDTO.getPlatformId();
        }


        //dispacher
        MpcWeMQServiceConfig weMQServiceConfig = serviceMapping.get(event);
        if (weMQServiceConfig == null) {
            LOGGER.warn("cannot find event config : " + event);
            return responseDTO;
        }
        LOGGER.info("event request: {}", map);
        Method method = weMQServiceConfig.getMethod();
        if (method == null) {
            LOGGER.error("method is null");
            return responseDTO;
        }

        try {
            Object response = method.invoke(weMQServiceConfig.getInvokeClass(), JSON_MAPPER.fromJson(JSON_MAPPER.toJson(map), weMQServiceConfig.getRequestMessageClass()));
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;

            logMonitor(MonitorKeys.REQUEST_SUCCESS_TEMPLATE, weMQServiceConfig.getInvokeClass().getClass().getName(), weMQServiceConfig.getMethod().getName() + platformId,
                    MumbleContextUtil.getBizSeqNo(), "" + responseTime, MonitorKeys.SUCCESS_FLAG);
            responseDTO = (BaseResponseDTO) response;
        } catch (Exception e) {
            LOGGER.error(event, e);
        }

//        if ("giftcard_pay_done".equalsIgnoreCase(event)) {
//            return giftCardService.recvPayNotify(JSON_MAPPER.fromJson(JSON_MAPPER.toJson(map), OrderInfoDTO.class));
//        } else if ("user_consume_card".equalsIgnoreCase(event)) {
//            return consumeOrderService
//                    .receiveConsume(JSON_MAPPER.fromJson(JSON_MAPPER.toJson(map), WcFrontConsumeCallBackRequest.class));
//        } else if ("user_authorize_invoice".equalsIgnoreCase(event)) {
//            return hangXinService.invoice(JSON_MAPPER.fromJson(JSON_MAPPER.toJson(map), UserAuthorizeInvoiceEvent.class));
//        }

        return responseDTO;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, MumbleBaseService> allServices = this.context.getBeansOfType(MumbleBaseService.class);
        if (allServices == null) {
            return;
        }

        allServices.values().stream().forEach(service -> {
            Class<?> wbServiceClaz = service.getClass();
            while (MumbleBaseService.class.isAssignableFrom(wbServiceClaz)) {
                populateClassServiceConfig(wbServiceClaz, service);
                wbServiceClaz = wbServiceClaz.getSuperclass();
            }
        });

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("#system start load service list: #" + serviceMapping.toString());
        }
    }


    private void populateClassServiceConfig(Class<?> serviceClaz, MumbleBaseService service) {
        Method[] methods = serviceClaz.getMethods();
        if (methods == null) {
            return;
        }
        Arrays.asList(methods).stream().filter((m) -> m.isAnnotationPresent(WechatEventMethod.class)).forEach((m) -> {
            WechatEventMethod annotation = m.getAnnotation(WechatEventMethod.class);
            if (annotation.events() != null && annotation.events().length > 0) {
                for (int i = 0; i < annotation.events().length; i++) {
                    String event = annotation.events()[i];
                    m.setAccessible(true);
                    MpcWeMQServiceConfig weMQServiceConfig =
                            new MpcWeMQServiceConfig(event, service, m, annotation.requestMessageClass());
                    serviceMapping.put(event, weMQServiceConfig);
                }
            }

        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    private static void logMonitor(String format, Object... params) {
        String formatMonitorMsg = String.format(format, params);
        MONITOR_LOGGER.info(formatMonitorMsg);
    }
}