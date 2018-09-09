package cn.YouWei.STD.robot.context;

import javax.servlet.ServletContext;

/**
 * zhuanfa web context
 * @author tgf(Mar 24, 2011)
 *
 */
public class BargainContext {
	/** web servlet context*/
	private static ServletContext context;
	/** web应用根路径*/
	private static String webRootPath;
	
	/**
	 * 初始化
	 * @param context
	 */
	public static synchronized void init(ServletContext context){
		BargainContext.context = context;
	}
	
	/**
	 * 获取当前应用的根目录路径(绝对路径)
	 * @return
	 */
	public static String getWebRootPath(){
		if(webRootPath == null){
			webRootPath = (context == null)? null : context.getRealPath("/");
			if(webRootPath != null){
				//去除最后的"/"或"\"
				String regex = "^(.+)(?:/|\\\\)$";
				webRootPath = webRootPath.replaceAll(regex, "$1");
			}
		}
		return webRootPath;
	}
}
