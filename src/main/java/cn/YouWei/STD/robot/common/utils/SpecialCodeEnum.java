package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * Created by longmanli on 2018/1/12.
 */
public enum SpecialCodeEnum {
    BALANCE_NOT_ENOUGH("12150004", "余额不足");
    private String code;
    private String message;

    SpecialCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
