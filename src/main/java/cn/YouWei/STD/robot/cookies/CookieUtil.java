package cn.YouWei.STD.robot.cookies;

import cn.YouWei.STD.robot.util.NumberUtil;
import cn.YouWei.STD.robot.util.StringUtil;
import cn.YouWei.STD.robot.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie util
 * 
 * @author tgf 2008/07/03
 */
public class CookieUtil {
    private static final String P3P_HEADER = "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"";

    private static final Logger logger=LoggerFactory.getLogger(CookieUtil.class);

    /**
     * delete cookie set cookie's maxAge=0 mean delete the cookie
     * 
     * @param name
     */
    public static void deleteCookie(HttpServletResponse response, String key) {
        // cookie 值为null表示删除cookie
        CookieInfo info = new CookieInfo(key, null);
        addCookie(response, info);
    }

    /**
     * delete cookie set cookie's maxAge=0 mean delete the cookie
     * 
     * @param name
     */
    public static void deleteCookie(HttpServletResponse response,
                                    CookieInfo info) {
        // cookie 值为null表示删除cookie
        info.setCookieValue(null);
        addCookie(response, info);
    }

    /**
     * add cookie
     * 
     * @param response
     * @param key
     * @param value
     */
    public static void addCookie(HttpServletResponse response, String key,
                                 String value) {
        CookieInfo info = new CookieInfo(key, value);
        addCookie(response, info);
    }

    /**
     * 设置cookie
     * 
     * @param info
     *            cookie的信息
     */
    public static void addCookie(HttpServletResponse response, CookieInfo info) {
        if(info == null){
            logger.error("cookie info is null");
            return;
        }
        // 是否需要使用P3P,需要早于set cookie
        checkP3P(response, info);
        Cookie cookie = info.toCookie();
        response.addCookie(cookie);
    }

    /**
     * 从httpservletRequest返回Cookie对象
     * 
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String key) {
        if(StringUtil.isTrimEmpty(key)){
            return null;
        }

        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for(Cookie cookie : cookies){
                if(key.equals(cookie.getName())){
                    return cookie;
                }
            }
        }

        return null;
    }

    /**
     * 判断是否需要使用P3P
     * 
     * @param info
     */
    private static void checkP3P(HttpServletResponse response, CookieInfo info) {
        if(info.isUseP3P()){
            response.setHeader("P3P", P3P_HEADER);
        }
    }

    /**
     * 从cookie中返回字符串值
     * 
     * @param name
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        Cookie cookie = getCookie(request, key);
        if(cookie == null){
            return null;
        }

        String value = cookie.getValue();
        if("null".equals(value)){
            return null;
        }

        return UrlUtil.decode(value);
    }

    /**
     * 从cookie中返回int值
     * 
     * @param name
     * @return 不存在返回0
     */
    public static int getInt(HttpServletRequest request, String key) {
        return NumberUtil.parseInt(getString(request, key), 0);
    }

    /**
     * 从cookie中返回double值
     * 
     * @param name
     * @return 不存在返回0
     */
    public static double getDouble(HttpServletRequest request, String key) {
        return NumberUtil.parseDouble(getString(request, key), 0d);
    }
}
