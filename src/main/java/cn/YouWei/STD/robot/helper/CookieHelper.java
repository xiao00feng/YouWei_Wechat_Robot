package cn.YouWei.STD.robot.helper;

import cn.YouWei.STD.robot.config.BargainConfigurations;
import cn.YouWei.STD.robot.cookies.CookieInfo;
import cn.YouWei.STD.robot.util.AuthUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * author : ouwery (2013, 10, 21)
 */
public class CookieHelper {
	/** 是否第一次访问系统的cookie key*/
	private final static String IS_FIRST_VISIT = "_ifv_";
	private final static String ACCOUNT = "_acct_";
	private final static String SSID = "_scsid_";
	private final static String DOMAIN_NAME = "";
	
	//清除account的cookie
	public static void deleteAccount(HttpServletResponse response){
		CookieInfo info = new CookieInfo(ACCOUNT, null);
		info.setCookieDomain(DOMAIN_NAME);
		AuthUtil.deleteAuthCookie(response, info);
	}
	// 保存account的cookie
	public static void setAccount(HttpServletResponse response,
                                  String account) {
		CookieInfo info = new CookieInfo(ACCOUNT, account, BargainConfigurations.customerCookieExpires());
		info.setCookieDomain(DOMAIN_NAME);
		// 设置需要验证的cookie
		AuthUtil.setAuthCookie(response, info);
	}
	public static String getAccount(HttpServletRequest request) {
		String account = AuthUtil.getAuthCookie(request,ACCOUNT);
		return account;
		
	}
	//清除ssid的cookie
	public static void deleteSsid(HttpServletResponse response){
		CookieInfo info = new CookieInfo(SSID, null);
		info.setCookieDomain(DOMAIN_NAME);
		AuthUtil.deleteAuthCookie(response, info);
	}
	// 保存ssid的cookie
	public static void setSsid(HttpServletResponse response,
                               String ssid) {
		CookieInfo info = new CookieInfo(SSID, ssid,BargainConfigurations.customerCookieExpires());
		info.setCookieDomain(DOMAIN_NAME);
		// 设置需要验证的cookie
		AuthUtil.setAuthCookie(response, info);
	}
	public static String getSsid(HttpServletRequest request) {
		String ssid = AuthUtil.getAuthCookie(request,SSID);
		return ssid;
		
	}
	//清除isFirstVisit是否第一次访问cookie
		public static void deleteIsFirstVisit(HttpServletResponse response){
			CookieInfo info = new CookieInfo(IS_FIRST_VISIT, null);
			info.setCookieDomain(DOMAIN_NAME);
			AuthUtil.deleteAuthCookie(response, info);
		}
	// 保存isFirstVisit是否第一次访问
	public static void setIsFirstVisit(HttpServletResponse response,
                                       String isFirstVisit) {
		CookieInfo info = new CookieInfo(IS_FIRST_VISIT, isFirstVisit,BargainConfigurations.customerCookieExpires());
		info.setCookieDomain(DOMAIN_NAME);
		
		// 设置需要验证的cookie
		AuthUtil.setAuthCookie(response, info);
	}
	public static String getIsFirstVisit(HttpServletRequest request) {
		String isFirstVisit = AuthUtil.getAuthCookie(request,IS_FIRST_VISIT);
		return isFirstVisit;
		
	}

}
