package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * Created by leaflyhuang on 2017/4/19.
 */
public class GiftCardKeys {

    public final static String WEMQ_RETRY_QUEUE = "mpc-giftcard:1WEMQ_RETRY_QUEUE";
    public final static String WEMQ_RETRY_QUEUE2 = "mpc-giftcard:2WEMQ_RETRY_QUEUE";

    // 激活code重试时的锁
    public final static String LOCK_ACTIVE_CODE = "lock:activecode";
    public final static String LOCK_PLATFORM_ACTIVE_CODE = "lock:platactivecode";
    public final static String TOPIC_CONSUME = "GIFTCARD_CONSUME";
    public final static String LOCK_SYN_ORDER = "lock:synorder";
}
