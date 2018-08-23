package cn.webank.mumble.mpc.giftcard.service;

import cn.webank.mumble.mpc.giftcard.common.dto.BaseResponseDTO;

import java.util.Map;

/**
 * Created by leaflyhuang on 2017/5/12.
 */
public interface EventService {
    /**
     * 处理消息
     *
     * @param map
     * @return
     */
    public BaseResponseDTO handleMessage(Map map);
}
