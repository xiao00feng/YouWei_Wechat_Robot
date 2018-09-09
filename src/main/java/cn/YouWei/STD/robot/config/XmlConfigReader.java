package cn.YouWei.STD.robot.config;

import cn.YouWei.STD.robot.util.Dom4jUtil;
import cn.YouWei.STD.robot.util.NumberUtil;
import cn.YouWei.STD.robot.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.Properties;


/**
 * read system config from xml the xml schema is: <config> <!-- the key is
 * language, and the value is zh_CH --> <language>zh_CH</language> ... <!--
 * import other config files, seperated by ',', but cannot use import in other
 * config file --> <import>a.xml,b.xml,c.xml</import> </config>
 * 
 * @author tgf(Oct 16, 2010)
 * 
 */
public class XmlConfigReader {
    /** key-value holder */
    private Properties configs = new Properties();

    /** 控制只初始化一次 */
    private boolean isInited;

    /** 默认的配置文件名 */
    private String configFile = "config.xml";

    public XmlConfigReader() {
        init();
    }

    public XmlConfigReader(String configFile) {
        this.configFile = configFile;
        init();
    }

    /**
     * 重新读取配置文件
     * 
     * @param configFile
     */
    public void reinit() {
        isInited = false;
        init();
    }

    /**
     * initializing
     * 
     * @param configFile
     */
    private void init() {
        if(isInited){
            return;
        }
        if(StringUtil.isEmpty(configFile)){
            return;
        }
        // clear
        configs.clear();

        loadConfigFile(configFile, true);

        isInited = true;
    }

    @SuppressWarnings("unchecked")
    private void loadConfigFile(String configFile, boolean toImport) {
        // 生成Document
        Document document = Dom4jUtil.getDocument(configFile);
        Element root = document.getRootElement();
        for(Iterator<Element> i = root.elementIterator(); i.hasNext();){
            Element el = i.next();
            String key = el.getName();
            String value = el.getTextTrim();
            if(toImport && "import".equals(key)){
                if(!StringUtil.isTrimEmpty(value)){
                    String[] importFiles = value.split("\\s*,\\s*");
                    for(String importFile : importFiles){
                        loadConfigFile(importFile, false);
                    }
                }
            }else{
                // save the key-value pairs
                configs.setProperty(key, value);
            }
        }
    }

    /**
     * 打印测试
     */
    public void printAll() {
        configs.list(System.out);
    }

    /**
     * 返回配置信息
     * 
     * @return
     */
    public Properties getAll() {
        return configs;
    }

    /**
     * get string config value
     * 
     * @param key
     * @return
     */
    public String getString(String key) {
        return configs.getProperty(key);
    }

    /**
     * 由字符串得到字符串数组,默认以","分隔 不存在的话返回空字符串数组
     * 
     * @param key
     * @return
     */
    public String[] getStrings(String key) {
        String value = getString(key);
        if(StringUtil.isEmpty(value)){
            return new String[]{};
        }
        return value.split("\\s*,\\s*");
    }

    /**
     * 返回integer类型数组
     * 
     * @param key
     * @return
     */
    public int[] getInts(String key) {
        String[] strArr = getStrings(key);
        int len = strArr.length;
        int[] values = new int[len];
        for(int i = 0; i < len; i++){
            values[i] = NumberUtil.parseInt(strArr[i], 0);
        }
        return values;
    }

    /**
     * 返回integer类型結果
     * 
     * @param key
     * @return
     */
    public int getInt(String key) {
        String value = getString(key);
        return NumberUtil.parseInt(value);
    }

    public long getLong(String key) {
        return NumberUtil.parseLong(getString(key));
    }

    /**
     * 返回double类型結果
     * 
     * @param key
     * @return
     */
    public double getDouble(String key) {
        String value = getString(key);
        return NumberUtil.parseDouble(value);
    }

    /**
     * 返回boolean类型結果 config值为'true'或'yes'返回true,其他返回false
     * 
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        String value = getString(key);
        return "true".equals(value) || "yes".equals(value);
    }
}
