package cn.YouWei.STD.robot.util;

import cn.YouWei.STD.robot.module.RespTextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 
 * desc:微信接收信息处理工具类
 *
 * @author gzq(2015年3月18日)
 */
public class MessageUtil
{

    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(点击自定义菜单拉取信息)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";
    /**
     * 点击今日内容-音乐
     */
    public static final String EVENTKEY_TODAY_MUSIC = "V1001_TODAY_MUSIC";
    /**
     * 点击菜单-赞一下我们
     */
    public static final String EVENTKEY_MENU_GOOD = "V1001_GOOD";

    /**
     * 事件类型：VIEW(点击自定义菜单跳转链接)
     */
    public static final String EVENT_TYPE_VIEW = "VIEW";

    public static Map<String, String> parseXml(HttpServletRequest request)
            throws IOException, DocumentException
    {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 获取返回给用户的信息
     * 
     * @param textMessage
     * @return
     * 
     *         public static String getRespMessage(RespTextMessage textMessage)
     *         { StringBuffer sb = new StringBuffer("<xml>" +
     *         "<ToUserName><![CDATA[#toUser#]]></ToUserName>" +
     *         "<FromUserName><![CDATA[#fromUser#]]></FromUserName>" +
     *         "<CreateTime>#time#</CreateTime>" +
     *         "<MsgType><![CDATA[text]]></MsgType>" +
     *         "<Content><![CDATA[#content#]]></Content>" + "</xml>");
     * 
     *         String respMessage = sb.toString();
     * 
     *         respMessage = respMessage.replace("#toUser#",
     *         textMessage.getToUserName()); respMessage =
     *         respMessage.replace("#fromUser#", textMessage.getFromUserName());
     * 
     *         // new Date().getTime()==System.currentTimeMillis() / 1000
     *         respMessage = respMessage.replace("#time#",
     *         textMessage.getCreateTime() + ""); respMessage = respMessage
     *         .replace("#content#", textMessage.getContent()); return
     *         respMessage; }
     */

    /**
     * 文本消息对象转换成xml
     * 
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(RespTextMessage textMessage)
    {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver()
    {
        public HierarchicalStreamWriter createWriter(Writer out)
        {
            return new PrettyPrintWriter(out)
            {
                // 对所有非数字xml节点的转换都增加CDATA标记
                public void startNode(String name,
                        @SuppressWarnings("rawtypes") Class clazz)
                {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text)
                {
                    Pattern pattern = Pattern.compile("[0-9]*");
                    if (!pattern.matcher(text).matches())
                    {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                    else
                    {
                        writer.write(text);
                    }
                }
            };
        }
    });
}
