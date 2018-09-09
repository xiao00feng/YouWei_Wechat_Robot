package cn.YouWei.STD.robot.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * transfer java object to json string, or other
 *
 * @author tgf(Mar 7, 2010)
 */
public class JsonUtil {
    /**
     * transfer java object to json text
     *
     * @param obj
     * @return
     */
    public static String toJsonText(Object object) {
        return JSON.toJSONString(object);
    }

    public static Object toOjbect(String jsonText) {
        return JSON.parse(jsonText);
    }

    public static JSONObject parseObject(String jsonText) {
        return JSON.parseObject(jsonText);
    }


}
