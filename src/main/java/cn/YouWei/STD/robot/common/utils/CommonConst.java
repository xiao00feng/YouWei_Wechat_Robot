package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * Created by qianbw on 2017/4/20.
 */
public class CommonConst {
    public static String STATUS_INIT = "0";
    //银基-会员注册接口 	是否已开通会员：0：未开户，1：已开户
    public static String USER_REGISTER_STATE = "1";

    public static  String FROM_WECHAT="01"; //请求来源（01：微信）

    public static  String STATUS_ACTIVE="1";//礼品卡状态（1.激活，0.作废）

    public static  String STATUS_INVALID="0";//礼品卡状态（1.激活，0.作废）
    //发送客服消息 messageType
    public static int  MESSAGE_TYPE_CARD=4;//卡劵

	public static String ORDER_TAKE = "0";// 订单待揽收
	public static String ORDER_ON = "1";// 订单已经配送
	public static String ORDER_OVER = "2";// 物流订单完成
	public static String ORDER_CANCEL = "3";// 订单已取消（SF回调返回）
	public static String ORDER_INIT = "4";// 顺丰店配系统收单成功
	public static String ORDER_FD_SUCCESS = "6";// 非码核销成功
	public static String ORDER_FD_FAIL = "7";// 非码核销失败
	public static String ACTION_SHOP_PICKUP = "order_shop_pickup";// (门店推送)
	public static String ACTION_SEND_AWAIT = "order_send_await";// (订单待揽收)
	public static String ACTION_SEND = "order_send";// (订单配送)
	public static String ACTION_COMPLETE = "order_complete";// （订单完成）
	public static String ACTION_CANCEL = "order_cancel";// (订单取消)
}
