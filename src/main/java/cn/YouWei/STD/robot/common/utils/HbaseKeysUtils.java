package cn.webank.mumble.mpc.giftcard.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by leaflyhuang on 2017/5/5.
 */
public class HbaseKeysUtils {
    /**
     * 根据hash值生成前3位数字的随机key
     * @param key
     * @return
     */
    public static String hashPrefixKey(String key){
        return hashPrefixKey(key,3);
    }

    public static String hashPrefixKey(String key,int size){
        if(StringUtils.isEmpty(key)){
            return key;
        }
        if(size<1){
            size=3;
        }
        long prefix = Math.abs(key.hashCode()) % Math.round(Math.pow(10, size));
        return String.format("%0"+size+"d_%s", prefix,key);
    }

    public static String originHashPrefixKey(String key){
        if(StringUtils.isEmpty(key)){
            return key;
        }
        int index = key.indexOf('_');
        if(index==-1){
            return key;
        }else
        {
            return key.substring(index+1);
        }
    }


    public static String reverseKey(String key){
        if(StringUtils.isEmpty(key)){
            return key;
        }

        return StringUtils.reverse(key);
    }
    public static void main(String[] args) {
        System.out.println(hashPrefixKey("Z6DVesSuVxVZ5Mw6GQ"));

    }
}
