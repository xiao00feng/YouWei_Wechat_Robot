package cn.YouWei.STD.robot.module;

/**
 * 
 * desc:服务器响应文本信息实体类
 * 
 * @author gzq(2015年3月4日)
 */
public class RespTextMessage
{
    // 接收方-用户对应的OpenID
    private String ToUserName;
    // 发送方-开发者微信号
    private String FromUserName;
    // 消息创建时间 （整型）
    private long CreateTime;
    // 消息类型（text/music/news）
    private String MsgType;
    // 回复的消息内容
    private String Content;

    public String getToUserName()
    {
        return ToUserName;
    }

    public void setToUserName(String toUserName)
    {
        this.ToUserName = toUserName;
    }

    public String getFromUserName()
    {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName)
    {
        this.FromUserName = fromUserName;
    }

    public long getCreateTime()
    {
        return CreateTime;
    }

    public void setCreateTime(long createTime)
    {
        this.CreateTime = createTime;
    }

    public String getMsgType()
    {
        return MsgType;
    }

    public void setMsgType(String msgType)
    {
        this.MsgType = msgType;
    }

    public String getContent()
    {
        return Content;
    }

    public void setContent(String content)
    {
        this.Content = content;
    }

}
