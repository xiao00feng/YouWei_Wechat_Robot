package cn.YouWei.STD.robot.cookies;

import cn.YouWei.STD.robot.util.StringUtil;
import cn.YouWei.STD.robot.util.UrlUtil;

import javax.servlet.http.Cookie;
import java.io.Serializable;

/**
 * cookie信息
 * 
 * @author tgf(Jan 29, 2011)
 * 
 */
public class CookieInfo {
    /** cookie名称 */
    private String cookieName;
    /** cookie值 */
    private Serializable cookieValue;
    /** 最大有效时间(秒), 负数表示浏览器进程(默认), 0表示删除cookie */
    private int cookieMaxAge = -1;
    /** path where cookie only can see */
    private String cookiePath = "/";
    /** domain,only server that set the cookie */
    private String cookieDomain;
    /** 是否是使用P3P特性 */
    private boolean useP3P;

    public CookieInfo() {
    }

    public CookieInfo(String cookieName, Serializable cookieValue) {
        this.cookieName = cookieName;
        this.cookieValue = cookieValue;
        // 控制有效时间
        if(this.cookieValue == null){
            // 表示删除
            this.cookieMaxAge = 0;
        }
    }

    public CookieInfo(String key, Serializable value, int expires) {
        this.cookieName = key;
        this.cookieValue = value;
        // 控制有效时间
        if(this.cookieValue == null){
            // 表示删除
            this.cookieMaxAge = 0;
        }else if(expires > 0){
            // 设置有效期
            this.cookieMaxAge = expires;
        }
    }

    public void setCookieValue(Serializable cookieValue) {
        this.cookieValue = cookieValue;
        if(this.cookieValue == null){
            // 表示删除
            this.cookieMaxAge = 0;
        }
    }

    /**
     * 转换为标准Cookie对象
     * 
     * @return
     */
    public Cookie toCookie() {
        String value = "";
        if(cookieValue == null){
            // 删除cookie
            this.cookieMaxAge = 0;
        }else{
            value = UrlUtil.encode(cookieValue.toString());
        }
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(cookieMaxAge);
        cookie.setPath(cookiePath);
        if(StringUtil.isNotEmpty(cookieDomain)){
            cookie.setDomain(cookieDomain);
        }
        return cookie;
    }

    public static CookieInfo toCookieInfo(Cookie cookie) {
        if(cookie == null){
            return null;
        }
            
        CookieInfo info = new CookieInfo();
        info.setCookieName(cookie.getName());
        info.setCookieValue(cookie.getValue());
        info.setCookieMaxAge(cookie.getMaxAge());
        info.setCookiePath(cookie.getPath());
        info.setCookieDomain(cookie.getDomain());
        return info;
    }

    /**
     * copy
     * 
     * @return
     */
    public CookieInfo copy() {
        CookieInfo info = new CookieInfo(cookieName, cookieValue);
        info.setCookieMaxAge(cookieMaxAge);
        info.setCookieDomain(cookieDomain);
        info.setCookiePath(cookiePath);
        info.setUseP3P(useP3P);
        return info;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieValue() {
        return cookieValue.toString();
    }

    public int getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public boolean isUseP3P() {
        return useP3P;
    }

    public void setUseP3P(boolean useP3P) {
        this.useP3P = useP3P;
    }
}
