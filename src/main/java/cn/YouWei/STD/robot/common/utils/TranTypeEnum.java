package cn.webank.mumble.mpc.giftcard.common.utils;

public enum TranTypeEnum {

    INIT("0", "现金卡-初始化建账号"),
    CONSUME("1", "现金卡-消费"),
    CANCEL("2", "现金卡-消费退款"),
    ;
    private String type;
    private String desc;

    TranTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
