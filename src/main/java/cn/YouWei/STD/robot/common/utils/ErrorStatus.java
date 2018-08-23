package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * @author biweiqian on 2017/03/24.
 */
public enum ErrorStatus {

    SUCCESS("0", "成功"),
    FAIL("1", "失败"),
    ERR_SYSTEM_BUSY("939001", "系统繁忙"),
    ERR_PARAMETER("939002", "参数错误"),
    ERR_CODE_NOT_EXIST("939003", "code不存在"),
    ERR_SIGN("929005", "签名验证不通过"),
    ERR_GET_ORDER_FAILED("939004", "获取订单信息失败"),
    ERR_SYS_TIMEOUT("929006", "系统超时"),
    ERR_ACCEPTEROPENID_IS_NOT_NULL("939005", "您的订单不符合退款政策，退款失败！"),
    ERR_FREEMUD_FAIL("939006", "退款失败，请稍后重试！(939006)"),
    ERR_WECHAT_FAIL("939007", "退款失败，请稍后重试！(939007)"),
    ERR_INVOICE_REFUND("939008", "已退款成功，但无法给您开红票，如需继续请稍后重试！"),
    ERR_TRANSID_WRONG("939009", "订单号不正确"),
    ERR_REFUND_TIMEOUT("939010", "处理中，请稍候查询退款状态！"),
    ERR_CONSUME_REFUND("939011", "卡券已使用，无法退款！"),
    ERR_ACCEPTER_CANNOT_REFUND("939012", "退款失败，订单退款必须由购买人发起！"),
    ERR_ORDER_NOT_FULL("939013", "您的订单不完整，无法退款！"),
    ERR_NO_AUTH_TODO("939014", "您无权限操作此卡券"),
    ERR_ALREADY_INVOICED("939015", "已申请开发票，不允许退款"),
    ERR_SYS_EXCEPTION("11810002", "系统异常,请稍后重试"),
    ERR_HANGXIN_REQUEST_CONTENT_INVALID("11810003", "请求参数异常"),
    ERR_REFUND("11810004", "已退款不允许开发票"),
    ERR_WRONG_SIGN("11810005", "签名不正确"),
    ERR_WRONG_CODE("11810006", "二维码错误"),
    ERR_OEDER_NO_NOT_FOUND("11810007", "订单号，不存在"),
    ERR_NO_CASH_CARD("11810008", "不是现金卡"),
    ERR_CODE_STATUS("11810009", "已核销,不能撤销订单"),
    ERR_SERVICE_EXC("11810010", "系统繁忙,请稍后重试"),
    ERR_CARD_ID_NOT_CONFIG("11810011", "cardid未配置"),
    YINJIN_STATUS_200("200", "请求成功"),
    YINJIN_STATUS_500("500", "系统繁忙,请稍后重试"),
    YINJIN_DATA_SUCCESS("1", "成功"),
    YINJIN_DATA_FAIL("0", "失败"),
    CODE_LOCKING("11810012", "CODE处理中，请稍后重试"),
    CODE_CHANGEED("11810013", "CODE已兑换"),
    ACTIVITY_FINISHED("11810014", "活动已结束"),
    ERR_CODE_ID_NOT_EXIST("11810015", "cardId不存在"),
    PARTNER_DATA_FAIL("11810016", "系统异常(partner),订单退款失败"),
    CARDCODE_DATA_FAIL("11810017", "CardCode系统订单退款失败"),
    ERR_CARD_ID_IS_NULL("11810018", "卡id为空"),
    ERR_GET_CARD_INFO_FAIL("11810019", "获取卡信息失败"),
    ERR_GET_CODE_INFO_FAIL("11810020", "获取code信息失败"),
    ERR_CARD_DIFFER_PRODUCT("11810022", "购买商品与商品卡不匹配"),
    ERR_PARTNER_ACTIVE_FAIL("11810021", "合作方激活失败"),
    ERR_BALANCE_NOT_ENOUGH("11810023", "余额不足"),
    CTF_DATA_FAIL("11810024", "周大福方订单退款失败"),
    ERR_CASH_CONSUME_TIMEOUT("11810025", "现金卡内部核销超时"),
    ERR_CASH_CANCEL_TIMEOUT("11810026", "现金卡内部退货超时"),
    ERR_GET_ROUTE_FAILED("939060", "您好，快递暂未揽收，请您稍后查询。"),
    ERR_ADRESS_FAILED("939061", "您好，你的地址数量超过限制"),
    CHECK_ADRESS_FAILED("939062", "对不起，您的地址不在配送范围内，请到门店使用"),
    ERR_NOT_GOODS_CARD("939063", "对不起，你的礼品卷不是提货卷，暂时不能进行配送"),
    ERR_GET_ADRESS_FAILED("939064", "您好，已通知餐厅和圣诞老人-稍后可以看看他到那里了"),
    ORDER_INIT("939065", "您的订单等待快递揽收"),
    ERR_NOT_STATUS_CARD("939063", "对不起，你的礼品卷已核销"),
    ERR_CASH_CANCEL_ORDER_TIMEOUT("11810027", "现金卡订单撤销超时"),
    CONSUME_FAIL("11810028", "核销失败"),
    ERR_INVALID_CODE("11810029", "该卡券已失效"),
    ERR_CODE_ALREADY_ACTIVED("11810030", "该卡券已激活"),
    ERR_CODE_NOT_MATCH("11810031", "上送的code与所属合作方不匹配"),
    WECHAT_CODE_CONSUMED("40127", "该code已核销"),
    WECHAT_CODE_NOT_NORMAL("11810032", "卡状态不正确，不允许退款"),
    ;



    private String code;

    private String message;

    ErrorStatus(String code, String message) {
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
