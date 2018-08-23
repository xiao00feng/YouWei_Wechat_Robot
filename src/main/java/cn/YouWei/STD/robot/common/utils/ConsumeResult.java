package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * 核销结果
 *
 * @author ctrlzhang on 2017/4/20
 */
public enum ConsumeResult {
    SUCCESS(1, "success"),
    CODE_NOT_FOUND(2, "code_not_exist"),
    FAIL(0, "fail");

    private int code;
    private String desc;

    ConsumeResult(int code, String desc) {
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
