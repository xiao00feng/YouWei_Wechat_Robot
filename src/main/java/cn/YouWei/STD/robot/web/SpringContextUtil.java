package cn.YouWei.STD.robot.web;

import freemarker.template.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * get spring application context
 * @author tgf(Mar 7, 2010)
 *
 */

public class SpringContextUtil {
	private static ApplicationContext context = null;	

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		SpringContextUtil.context = context;
	}

	public static Object getBean(String beanId) {
		if(context == null){
			return null;
		}
		return context.getBean(beanId);
	}
	
	/**
	 * 从spring 容器中得到freemarker配置实例对象
	 * @return
	 */
	public static Configuration getFreemarkerConfig(){
		String freemarkerBeanId = "freemarkerConfig";
		FreeMarkerConfigurer configurer = (FreeMarkerConfigurer)getBean(freemarkerBeanId);
		return configurer == null ? null : configurer.getConfiguration();
	}
}
