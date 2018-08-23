/**
 * Copyright (C) @2017 Webank Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.webank.mumble.mpc.giftcard.common.utils;

import cn.webank.mpc.sdk.hbase.HBaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("hbaseClientUtils")
public class HBaseClientUtils {
    private final static Logger LOG = LoggerFactory.getLogger(HBaseClientUtils.class);

    @Value("${zk.url}")
    private String zkUrl;

    @Value("${zk.root}")
    private String zkRoot;

    @Value("${zk.port}")
    private String zkPort;


    private HBaseClient hbaseClient;

    public String getZkUrl() {
        return zkUrl;
    }

    public void setZkUrl(String zkUrl) {
        this.zkUrl = zkUrl;
    }

    public String getZkRoot() {
        return zkRoot;
    }

    public void setZkRoot(String zkRoot) {
        this.zkRoot = zkRoot;
    }

    public String getZkPort() {
        return zkPort;
    }

    public void setZkPort(String zkPort) {
        this.zkPort = zkPort;
    }


    public HBaseClient getHbaseClient() {
        return hbaseClient;
    }

    public void setHbaseClient(HBaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }

    @PostConstruct
    public void init() {
        hbaseClient = new HBaseClient(zkUrl, zkRoot, zkPort);
    }


    public void shutdown() {
        this.hbaseClient.shutdown();
    }


}
