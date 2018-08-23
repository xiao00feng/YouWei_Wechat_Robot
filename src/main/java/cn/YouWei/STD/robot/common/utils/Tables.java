package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * Created by leaflyhuang on 2017/7/20.
 */
public class Tables {
    public static final String TABLE_SPLIT = "_";

    public static final String ORDER_INFO = "mpc_giftcard_order_info"; //订单信息表
    public static final String ORDER_STATUS = "mpc_giftcard_order_status";//订单状态表
    public static final String CODE_INFO = "mpc_giftcard_code_info";//code信息表
    public static final String CODE_STATUS = "";//code状态表
    public static final String CONSUME_INFO = "mpc_giftcard_consume_info";//核销信息表
    public static final String CONSUME_STATUS = "mpc_giftcard_consume_status";//核销状态表
    public static final String INVOICE_INFO = "";//发票信息表
    public static final String CARD_ID_INFO = "mpc_giftcard_card_id";//cardID表
    public static final String CARD_CANCEL = "mpc_giftcard_card_cancel";//退款表
    public static final String RECEIPT_ORDER = "mpc_giftcard_receipt_order";//请求发票订单表
    public static final String MARKETING_CODE = "mpc_giftcard_marketing_code";//退款表
    public static final String MARKETING_TICKET_CODE = "mpc_giftcard_marketing_ticket_code";//退款表
    public static final String TRANSID_ORDERID="mpc_giftcard_transid_orderid_info";
    public static final String CASH_CONSUME_INFO="mpc_giftcard_cash_consume_info";
    public static final String CASH_COUPON_CONSUME_INFO="mpc_giftcard_cash_coupon_consume_info";//代金券表
    public static final String REVOKE_CODE_EXC="mpc_giftcard_revoke_code_exc";
    public static final String CASH_CANCEL_INFO="mpc_giftcard_cash_cancel";
    public static final String ORDER_SYN_STATUS="mpc_giftcard_order_syn_status";
    public static final String RECEIVER_ADDRESS_INFO="receiver_address_info"; //收货地址维护表
    public static final String RECEIVER_ORDER_INFO="receiver_order_info"; //顺风下单本地维护表
    public static final String OUTER_INFO="mpc_giftcard_outer_info"; //客户运营辅助信息
    public static final String MCD_ORDER_PREFIX = "mpc_giftcard_order_prefix";//麦当劳订单前缀表
}
