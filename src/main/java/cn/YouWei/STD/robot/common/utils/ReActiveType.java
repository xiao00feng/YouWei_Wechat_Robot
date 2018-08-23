package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * Created by shawphychen on 2017/8/14.
 */
public enum ReActiveType {

    ACTIVE_BY_CODE("ACTIVE_BY_CODE", "根据code激活"),
    ACTIVE_BY_TIME("ACTIVE_BY_TIME", "根据时间范围激活"),
    ACTIVE_BY_ORDER("ACTIVE_BY_ORDER", "根据order激活");

    private String code;

    private String desc;

    ReActiveType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ReActiveType getByCode(String code) {
        for (ReActiveType w : values()) {
            if (w.getCode().equals(code)) {
                return w;
            }
        }
        return null;
    }

}
