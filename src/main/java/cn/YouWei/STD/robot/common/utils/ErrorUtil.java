package cn.webank.mumble.mpc.giftcard.common.utils;

import cn.webank.mumble.mpc.giftcard.common.dto.BaseResponseDTO;

/**
 * Created by qianbw on 2017/3/24.
 */
public class ErrorUtil {
    /**
     * @param baseResponseDTO
     * @param errorStatus
     */
    public static <T extends BaseResponseDTO> void setStateInfo(T baseResponseDTO, ErrorStatus errorStatus) {
        baseResponseDTO.setCode(errorStatus.getCode());
        baseResponseDTO.setMsg(errorStatus.getMessage());
    }

    public static <T extends BaseResponseDTO> void setStateInfo(T baseResponseDTO, String code, String message) {
        baseResponseDTO.setCode(code);
        baseResponseDTO.setMsg(message);
    }
}
