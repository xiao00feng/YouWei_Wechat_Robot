package cn.YouWei.STD.robot.util;

import cn.YouWei.STD.robot.cookies.CookieInfo;
import cn.YouWei.STD.robot.cookies.CookieUtil;
import cn.YouWei.STD.robot.security.EncryptUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 网络权限验证相关的util
 * 
 * @author tgf(Jul 1, 2011)
 * 
 */
public class AuthUtil {
    /** session cookie key pofix */
    private static final String SESSION_KEY_POFIX = "s";

    /**
     * 设置需要验证的cookie
     * 
     * @param response
     * @param key
     * @param value
     */
    public static void setAuthCookie(HttpServletResponse response, String key,
                                     Serializable value) {
        CookieInfo info = new CookieInfo(key, value.toString());
        setAuthCookie(response, info);
    }

    /**
     * 设置需要验证的cookie
     * 
     * @param response
     * @param info
     */
    public static void setAuthCookie(HttpServletResponse response,
                                     CookieInfo info) {
        if(info == null){
            return;
        }
        String key = info.getCookieName();
        if(StringUtil.isEmpty(key)){
            return;
        }
        // 1.保存cookie
        CookieUtil.addCookie(response, info);

        // 2.保存session cookie
        // 构造
        CookieInfo sau = info.copy();
        String sKey = getAuthSessionKey(key);
        String sValue = getAuthSessionValue(info.getCookieValue());
        sau.setCookieName(sKey);
        sau.setCookieValue(sValue);
        CookieUtil.addCookie(response, sau);
    }

    /**
     * 从cookie中返回验证合法的cookie值 不存在或不合法返回null
     * 
     * @param request
     * @param key
     * @return
     */
    public static String getAuthCookie(HttpServletRequest request, String key) {
        // 1.get cookie value
        String value = CookieUtil.getString(request, key);
        if(StringUtil.isEmpty(value)){
            return null;
        }

        // 2.get session cookie value
        String sValue = CookieUtil.getString(request, getAuthSessionKey(key));
        return getAuthSessionValue(value).equals(sValue) ? value : null;
    }

    /**
     * 删除需要验证的cookie,包括session
     * 
     * @param response
     * @param key
     */
    public static void deleteAuthCookie(HttpServletResponse response,
                                        CookieInfo info) {
        // 1.delete cookie
        CookieUtil.deleteCookie(response, info);

        // 2.delete session cookie
        String skey = getAuthSessionKey(info.getCookieName());
        CookieInfo sInfo = info.copy();
        sInfo.setCookieName(skey);
        CookieUtil.deleteCookie(response, sInfo);
    }

    /**
     * 删除需要验证的cookie,包括session
     * 
     * @param response
     * @param key
     */
    public static void deleteAuthCookie(HttpServletResponse response, String key) {
        // 1.delete cookie
        CookieUtil.deleteCookie(response, key);

        // 2.delete session cookie
        CookieUtil.deleteCookie(response, getAuthSessionKey(key));
    }

    /**
     * 得到session cookie key
     * 
     * @param key
     * @return
     */
    private static String getAuthSessionKey(String key) {
        return key + SESSION_KEY_POFIX;
    }

    /**
     * 生成需要验证的session值
     * 
     * @param cookieValue
     * @return
     */
    public static String getAuthSessionValue(String value) {
        return EncryptUtil.md5(value);
    }

}
