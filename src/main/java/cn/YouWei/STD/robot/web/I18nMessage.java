package cn.YouWei.STD.robot.web;

import cn.YouWei.STD.robot.util.ResourceUtil;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * get message from i18n properties file
 * file like:
 * i18n/zh_CN/User.properties
 * @author tgf(Mar 6, 2010)
 *
 */
public class I18nMessage { 
	/** 默认的bundle文件根目录*/
	private final static String DEFAULT_BUNDLE_ROOT = "i18n";
	/** 默认的local*/
	private final static String DEFAULT_LOCAL = "zh_CN";
	
	/** cache*/
	private static List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
	/** 是否已初始化的标志*/
	private static boolean isInited = false;
	
	public static void init(){
		init(DEFAULT_BUNDLE_ROOT, DEFAULT_LOCAL);
	}
	
	public static void init(String bundleRoot){
		init(bundleRoot, DEFAULT_LOCAL);
	}
	
	public static void init(String bundleRoot, String local){
		if(isInited){
			return;
		}
		
		String baseName = bundleRoot + "." + local + ".";
		String path = bundleRoot + File.separator + local;
		File root = ResourceUtil.getFile(path);
		if(root.isDirectory()){
			String[] files = root.list();
			for (String file : files) {
				int index = file.indexOf("_" + local);
				if(index < 0){
					index = file.lastIndexOf(".");
				}
				
				String fileName = file.substring(0, index);
				String bundleName = baseName + fileName;
				ResourceBundle bundle = ResourceBundle.getBundle(bundleName);					
				if(bundle != null){
					bundles.add(bundle);
				}
			}
		}
		
		//set to true
		isInited = true;
		
	}
	
	/**
	 * get message from properties file
	 * @param key
	 * @param subs
	 * @return
	 */
	public static String getMessage(String key, Object...subs){
		if( ! isInited){
			//init with default config
			init();
		}
		
		String value = null;
		for (ResourceBundle bundle : bundles) {
			try {
				 value = bundle.getString(key);
				 break;
			} catch (Exception e) {
				//next bundle
				continue;
			}		
		}
		if(value == null){
			//not exist return empty string
			return "";
		}
		
		//format {0}...
		return MessageFormat.format(value, subs);
	}	
}
