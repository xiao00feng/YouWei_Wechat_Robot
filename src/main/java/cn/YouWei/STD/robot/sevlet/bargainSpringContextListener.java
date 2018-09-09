package cn.YouWei.STD.robot.sevlet;

import cn.YouWei.STD.robot.config.BargainInitialization;
import cn.YouWei.STD.robot.context.BargainContext;
import cn.YouWei.STD.robot.web.SpringContextUtil;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class bargainSpringContextListener extends ContextLoaderListener{
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		//已完成spring 的初始化,
		//set ApplicationContext
		SpringContextUtil.setContext(getCurrentWebApplicationContext());
		
		//do some zf config initialized...
		BargainInitialization.init();
		
		//初始化zhuanfa context
		BargainContext.init(event.getServletContext());
	}
}
