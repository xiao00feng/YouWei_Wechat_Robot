package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * @author biweiqian on 2017/03/24.
 */
public enum CardCodeStatus {

    CODE_STATUS_INIT("0", "初始化"),
    CODE_STATUS_STORAGE("1", "已入库"),
    CODE_STATUS_ACTIVATED("2", "已激活"),
    CODE_STATUS_RECEIVED("3", "已领取"),
    CODE_STATUS_STOP_PAID("4", "已止付"),
    CODE_STATUS_FROZEN("5", "已冻结"),
    CODE_STATUS_CONSUMED("-1", "已核销"),
    CODE_STATUS_EXPIRED("-2", "已过期");




    private String code;

    private String message;

    CardCodeStatus(String code, String message) {
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
