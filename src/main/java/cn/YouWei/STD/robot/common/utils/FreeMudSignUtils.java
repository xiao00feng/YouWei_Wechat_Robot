package cn.webank.mumble.mpc.giftcard.common.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by leaflyhuang on 2017/4/20.
 */
public class FreeMudSignUtils {
    private final static String KEY_SPLIT = "=";
    private final static String PARAMETER_SPLIT = "&";

    public static String countSign(Map<String, String> requestMap, String signKey) {
        if (requestMap == null || requestMap.isEmpty()) {
            return null;
        }
        List<String> orderKeys = new ArrayList<>();
        requestMap.keySet().forEach(key -> {
            if (!"sign".equals(key)) {
                orderKeys.add(key);
            }
        });
        java.util.Collections.sort(orderKeys);
        StringBuffer stringBuffer = new StringBuffer();
        orderKeys.forEach(key -> {
            stringBuffer.append(key).append(KEY_SPLIT).append(requestMap.get(key)).append(PARAMETER_SPLIT);
        });
        String toSign = stringBuffer.toString();
        toSign = toSign.substring(0, toSign.length() - 1);
        return MD5(toSign + signKey);
    }


    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}
