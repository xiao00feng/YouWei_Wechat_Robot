package cn.webank.mumble.mpc.giftcard.common.utils;

public enum MarketingActivityType {
    INNER_USER_ACTIVE("W1", "员工内部活动");
    private String type;
    private String typeName;

    MarketingActivityType(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
