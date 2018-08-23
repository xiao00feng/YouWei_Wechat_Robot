package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * Created by v_wbzytang on 2017/8/14.
 */
public enum ProductCodeEnum {

    /**
     * CA-现金卡 CC-代金券
     * 01是麦当劳
     * 01是微信渠道
     * 01是序号
     */
    MCD_CASHCARD("CA010101", "现金卡-麦当劳微信"),
    PLATFORM_CASHCARD("CA020101", "现金卡-平台微信"),
    MCD_CASHCOUPON("CC010101", "代金券-麦当劳");

    private String code;

    private String desc;

    ProductCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ProductCodeEnum getByCode(String code) {
        for (ProductCodeEnum w : values()) {
            if (w.getCode().equals(code)) {
                return w;
            }
        }
        return null;
    }

}
