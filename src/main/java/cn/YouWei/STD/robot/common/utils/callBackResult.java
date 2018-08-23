package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * 核销结果
 *
 * @author ctrlzhang on 2017/4/20
 */
public enum callBackResult {
    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    CAV_FAIL(3, "cancel_after_verification_fail"),
    ORDER_NOT_FOUND(4, "order_not_exist"),
    CODE_NOT_FOUND(5, "code_not_exist"),
    SYS_ERROR(6, "sys_error")
    ;

    private int code;
    private String desc;

    callBackResult(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
