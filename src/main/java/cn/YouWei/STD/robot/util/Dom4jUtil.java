package cn.YouWei.STD.robot.util;

import cn.YouWei.STD.robot.exception.IORuntimeException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * dom4j工具类
 *
 * @author tgf(May 13, 2010)
 */
public class Dom4jUtil {
    /**
     * 根据配置文件返回Dom4j的配置文件的Document对象
     *
     * @param configFile
     * @return
     */
    public static Document getDocument(String configFile) {
        InputStream is = null;
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {
            is = ResourceUtil.getFileAsStream(configFile);
        } catch (Exception e) {
            String message = "error happen when init config file,cannot get config file "
                    + configFile + " as stream";
            throw new IORuntimeException(message, e);
        }
        try {
            document = saxReader.read(is);
        } catch (Exception e) {
            String message = "error happen when reading config file by Dom4j";
            throw new IORuntimeException(message, e);
        }
        return document;
    }

    /**
     * 从url读取xml文件进行解析
     *
     * @param url
     * @param encoding xml编码格式
     * @return
     */
    public static Document getDocumentFromUrl(String url, String encoding) {
        Document document = null;
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding(encoding);
        try {
            document = saxReader.read(new URL(url));
        } catch (MalformedURLException e) {
            String message = "cannot parse the url to xml document, the url is: "
                    + url;
            throw new IORuntimeException(message, e);
        } catch (DocumentException e) {
            String message = "error happen when parsing xml from url";
            throw new IORuntimeException(message, e);
        }
        return document;
    }

    /**
     * 返回Element元素的node子节点的值
     *
     * @param el
     * @param node
     * @return
     */
    public static String getString(Element el, String node) {
        return el.selectSingleNode(node).getText();
    }

    /**
     * 返回node子节点的int类型的值, 不存在或错误返回0
     *
     * @param el
     * @param node
     * @return
     */
    public static int getInt(Element el, String node) {
        String value = getString(el, node);
        return NumberUtil.parseInt(value, 0);
    }

    /**
     * 返回node子节点的boolean类型的值, 不存在则返回false
     *
     * @param el
     * @param node
     * @return
     */
    public static boolean getBoolean(Element el, String node) {
        String value = getString(el, node);
        return "true".equalsIgnoreCase(value);
    }

    /**
     * 返回node子节点的double类型的值, 不存在或错误返回0
     *
     * @param el
     * @param node
     * @return
     */
    public static double getDouble(Element el, String node) {
        String value = getString(el, node);
        return NumberUtil.parseDouble(value, 0.0d);
    }
}
