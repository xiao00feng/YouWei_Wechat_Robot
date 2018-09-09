package cn.YouWei.STD.robot.config;



/**
 * 相关的配置信息
 * 配置文件classpath:*.xml(import zf/sys-config.xml)
 * @author tgf(Oct 31, 2011)
 *
 */
public class BargainConfigurations {
	/** 默认配置文件*/
	private static String filename = "bargain-config.xml";	
	private static XmlConfigReader configReader = new XmlConfigReader(filename);
	
	/**
	 * 重新读取配置文件的配置信息
	 */
	public static void reInitSystemConfig(){
		//1.重新读取配置文件
		configReader.reinit();
	}
	
	/** cookie domain */
	public static String cookieDomain(){
		return configReader.getString("cookie_domain");
	}
	
	public static int customerCookieExpires(){
		return configReader.getInt("customer_cookie_expires");
	}
	
	public static int customerDefaultCookieExpires(){
		return configReader.getInt("customer_default_cookie_expires");
	}
	
	public static String[] recommendCategories(){
		return configReader.getStrings("recommend_categories");
	}
	
	public static String alipayPartner(){
		return configReader.getString("alipay_partner");
	}
	
	public static String alipayKey(){
		return configReader.getString("alipay_key");
	}
	
	public static String alipayLogPath(){
		return configReader.getString("alipay_log_path");
	}
	
	public static String alipayInputCharset(){
		return configReader.getString("alipay_input_charset");
	}
	
	public static String alipaySignType(){
		return configReader.getString("alipay_sign_type");
	}
	
	public static String sellerAccount(){
		return configReader.getString("seller_account");
	}
	
	public static String orderName(){
		return configReader.getString("order_name");
	}
	
	public static String orderDescription(){
		return configReader.getString("order_description");
	}
	
	public static String returnUrl(){
		return configReader.getString("return_url");
	}
	
	public static String alipayGatewayNew(){
		return configReader.getString("alipay_gateway_new");
	}
	
	public static int startActivityInterval(){
		return configReader.getInt("start_activity_interval");
	}
	
	public static int endActivityInterval(){
		return configReader.getInt("end_activity_interval");
	}
	
	public static int techServiceQuota(){
		return configReader.getInt("tech_service_quota");
	}
	
	public static int DailyInterval(){
		return configReader.getInt("daily_interval");
	}
	
	public static String WebRoot(){
		return configReader.getString("web_root");
	}
	
	public static String aliyunAccessKeyId(){
        return configReader.getString("aliyun_accessKeyId");
    }
	
	public static String aliyunAccessKeySecret(){
        return configReader.getString("aliyun_accessKeySecret");
    }
	
	public static String apiUser(){
        return configReader.getString("api_user");
    }
	
	public static String apiKey(){
        return configReader.getString("api_key");
    }
	
	public static String fromEmail(){
        return configReader.getString("from_email");
    }
}
