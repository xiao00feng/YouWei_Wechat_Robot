package cn.webank.mumble.mpc.giftcard.common.utils;

/**
 * Created by leaflyhuang on 2017/6/22.
 */
public enum RefundSource {
    TIMER_HBASE("DB", "定时数据库"),
    WEB("WEB", "页面"),;

    private String source;

    private String sourceDesc;

    RefundSource(String source, String sourceDesc) {
        this.source = source;
        this.sourceDesc = sourceDesc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }
}
