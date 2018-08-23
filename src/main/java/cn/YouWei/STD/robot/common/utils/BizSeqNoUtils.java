package cn.webank.mumble.mpc.giftcard.common.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;

public class BizSeqNoUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(BizSeqNoUtils.class);

    public final static String sysId = "1181";

    final static int MAX_SEQ_NO_LENGTH = 32;

    public static String getNextBizSeqNo() {
        //6位地址+ 4位子系统id + 线程id + 时间戳 + 随机数
        StringBuffer stringBuffer = new StringBuffer(address);//6位
        String threadid = String.valueOf(Thread.currentThread().getId());
        stringBuffer.append(sysId).append(threadid);//6位线程ipd
        stringBuffer.append(String.valueOf(System.currentTimeMillis()));
        stringBuffer.append(RandomStringUtils.randomAlphabetic(MAX_SEQ_NO_LENGTH - stringBuffer.length()));
        return stringBuffer.toString();
    }

    static String address = "";

    static {
        try {
            address = NetworkUtil.getHostAddress();
        } catch (SocketException e) {
            LOGGER.error("", e);
        }
        if (StringUtils.isNotBlank(address)) {
            LOGGER.info("system ip:{}", address);
            String[] ips = address.split("\\.");
            address = StringUtils.leftPad(ips[2], 3, '0') + StringUtils.leftPad(ips[3], 3, '0');
        }

    }

    public static void main(String[] args) {
        System.out.println(getNextBizSeqNo());
        System.out.println(getNextBizSeqNo());
        System.out.println(getNextBizSeqNo());
        System.out.println(getNextBizSeqNo());
    }


}
