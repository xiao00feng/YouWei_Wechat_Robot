package cn.YouWei.STD.robot.config;

import cn.YouWei.STD.robot.web.I18nMessage;

/**
 * 转发相关的各种初始化工作(系统启动后调用)
 * @author tgf(Nov 25, 2010)
 * 
 */
public class BargainInitialization {
	/**
	 * 系统初始化
	 */
	public static synchronized void init(){
		//1.初始化i18n相关信息
		I18nMessage.init();
	}
}
