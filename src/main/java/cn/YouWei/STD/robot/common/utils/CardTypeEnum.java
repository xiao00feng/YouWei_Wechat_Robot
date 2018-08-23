package cn.webank.mumble.mpc.giftcard.common.utils;

public enum CardTypeEnum {

    SINGLE_CARD("2", "单品卡"),
    BALANCE_CARD("1", "余额卡"),
    CASH_CARD("3", "现金卡"),
    MARKETING_CARD("4", "营销兑换卡"),
    MARKETING_TICKET("5", "营销兑换券"),
    ;


    private String type;

    private String name;

    CardTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
