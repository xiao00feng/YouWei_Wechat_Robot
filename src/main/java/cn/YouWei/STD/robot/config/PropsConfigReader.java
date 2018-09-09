package cn.YouWei.STD.robot.config;

import cn.YouWei.STD.robot.util.NumberUtil;
import cn.YouWei.STD.robot.util.ResourceUtil;
import java.util.Properties;


/**
 * properties config file reader
 * 
 * @author tgf(Nov 22, 2010)
 * 
 */
public class PropsConfigReader {
    private Properties props;

    public PropsConfigReader(String propertiesFile) {
        props = ResourceUtil.loadProperties(propertiesFile);
    }

    /**
     * return the propsperties instance
     * 
     * @param key
     * @return
     */
    public Properties getPropeties() {
        return this.props;
    }

    public String getString(String key) {
        return props.getProperty(key);
    }

    public int getInt(String key) {
        String value = getString(key);
        return NumberUtil.parseInt(value);
    }

    public long getLong(String key) {
        String value = getString(key);
        return NumberUtil.parseLong(value);
    }

    public boolean getBoolean(String key) {
        String value = getString(key);
        return "true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
    }
}
