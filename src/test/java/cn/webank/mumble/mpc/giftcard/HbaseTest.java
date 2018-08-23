package cn.webank.mumble.mpc.giftcard;

import cn.webank.mpc.sdk.hbase.HBaseClient;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by leaflyhuang on 2017/3/22.
 */
public class HbaseTest {

    public static String zkUrl = "10.107.103.40:2181,10.107.102.86:2181,10.107.101.249:2181";
    public static String zkRoot = "/hbase";
    public static String zkPort = "2181";

    public static String tableName = "mpc_member_test1";

    public static void main(String[] args) throws IOException {
        HBaseClient client = new HBaseClient(zkUrl, zkRoot, zkPort);

        String rowKey = "member_test" + RandomStringUtils.randomAlphabetic(5);


        Admin admin = null;
        Connection con = null;
        try {
            Configuration config = HBaseConfiguration.create();
            // 填写zookeeper地址，多个地址用英文逗号隔开
            config.set("hbase.zookeeper.quorum", zkUrl);
            // 设置重试参数
            config.setInt("hbase.client.retries.number", 1);
            /*
             * 要连接腾讯云的hbase服务必须设置此值为true；不设置该值功能和社区版相同，可以正常连接自建hbase服务
             */
            config.set("chbase.tencent.enable", "true");

            TableName TABLE = TableName.valueOf(tableName);
            con = ConnectionFactory.createConnection(config);
            admin = con.getAdmin();


            if (admin.tableExists(TABLE)) {
                System.out.println("table already exists!");
            } else {
                HTableDescriptor tableDesc = new HTableDescriptor(TABLE);
                tableDesc.addFamily(new HColumnDescriptor("status"));
                admin.createTable(tableDesc);
                System.out.println("create table " + tableName + " ok.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (admin != null) {
                admin.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }


}
