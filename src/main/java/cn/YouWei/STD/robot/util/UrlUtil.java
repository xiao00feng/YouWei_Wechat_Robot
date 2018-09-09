package cn.YouWei.STD.robot.util;


import cn.YouWei.STD.robot.constant.EncodingConstants;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * url相关的工具类
 * 
 * @author tgf(Mar 5, 2011)
 * 
 */
public class UrlUtil {
    /**
     * 将url不合法的字符转换成utf8编码格式 Translates a string into
     * application/x-www-form-urlencoded format
     * 
     * @param value
     * @return
     */
    public static String encode(String value) {
        try{
            return URLEncoder.encode(value, EncodingConstants.UTF8);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 将url的字符串解码
     * 
     * @param value
     * @return
     */
    public static String decode(String value) {
        try{
            return URLDecoder.decode(value, EncodingConstants.UTF8);
        }catch (Exception e){
            return null;
        }
    }
}
