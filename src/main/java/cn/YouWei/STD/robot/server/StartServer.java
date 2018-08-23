package cn.webank.mumble.mpc.giftcard.server;

import cn.webank.mumble.framework.biz.server.MumbleBaseServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by leaflyhuang on 2017/3/19.
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.webank", lazyInit = false)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class StartServer extends MumbleBaseServer {

    /**
     * main函数。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) throws Exception {
        try {
            new StartServer().service(StartServer.class, args);
        } catch (Exception exp) {
            exp.printStackTrace();
            System.exit(1);
        }

    }
}
