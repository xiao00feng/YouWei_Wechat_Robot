package cn.webank.mumble.mpc.giftcard.web.controller;

import cn.webank.mpc.sdk.security.MpcSignUtils;
import cn.webank.mumble.framework.common.dto.BizErrors;
import cn.webank.mumble.framework.common.utils.MumbleContextUtil;
import cn.webank.mumble.framework.web.function.ExecServiceTemplate;
import cn.webank.mumble.mpc.giftcard.common.dto.*;
import cn.webank.mumble.mpc.giftcard.common.utils.ErrorStatus;
import cn.webank.mumble.mpc.giftcard.common.utils.ParameterFilter;
import cn.webank.mumble.mpc.giftcard.common.utils.RefundSource;
import cn.webank.mumble.mpc.giftcard.intergration.sao.WeMQSAO;
import cn.webank.mumble.mpc.giftcard.service.PlatformService;
import cn.webank.mumble.mpc.giftcard.service.impl.PlatformServiceImpl;
import cn.webank.mumble.mpc.giftcard.service.platform.*;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 业务需求处理中心
 *
 *
 */

@RestController
@RequestMapping("card/business")
public class BusinessNeedsController extends MpcBaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BusinessNeedsController.class);

    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.service.PlatformService")
    private PlatformService platformService;

    @Autowired
    @Qualifier("cn.webank.mumble.mpc.giftcard.service.platform.OrderInfoService")
    private OrderInfoService orderInfoService;

    /**
     * 新百激活历史数据同步
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(value = "synActice", method = RequestMethod.GET)
    public DeferredResult<BaseResponseDTO> synHistoryActice(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                                                @RequestParam(value = "appid", required = true) String appid) {

            LOGGER.info("synHistoryActice appid={}", appid);
            DeferredResult<BaseResponseDTO> response = new DeferredResult<>();
            if (StringUtils.isBlank(appid)) {
                response.setResult(new BaseResponseDTO(ErrorStatus.ERR_PARAMETER));
                return response;
            }
            //2.投递消息
            BaseResponseDTO timeOutDefaultRsp =
                    new BaseResponseDTO(ErrorStatus.ERR_SYSTEM_BUSY.getCode(), ErrorStatus.ERR_SYSTEM_BUSY.getMessage());
            BaseResponseDTO exceptionDefaultRsp =
                    new BaseResponseDTO(ErrorStatus.ERR_SYS_EXCEPTION.getCode(), ErrorStatus.ERR_SYS_EXCEPTION.getMessage());
            return this.execute(httpServletRequest, httpServletResponse, null, webTimeout, (requestDto, bizErrors) -> {
                PlatformDTO platformDTO = platformService.queryPlatform(appid);
                if (platformDTO == null) {
                    BaseResponseDTO platformCardConsumeRsp = new BaseResponseDTO(ErrorStatus.ERR_PARAMETER);
                    LOGGER.warn("appid:{} cannot find", appid);
                    return platformCardConsumeRsp;
                }
                return orderInfoService.snyOrderCode(appid);
            }, timeOutDefaultRsp, exceptionDefaultRsp);
        }


    }