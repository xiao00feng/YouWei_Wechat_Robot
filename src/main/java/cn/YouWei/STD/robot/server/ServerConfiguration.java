/**
 * Copyright (C) 2016 Webank Group Holding Limited
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

package cn.webank.mumble.mpc.giftcard.server;

import cn.webank.mpc.sdk.jedis.MpcMultiJedisShardedPool;
import cn.webank.mpc.sdk.wemq.MpcWeMQDispatcherService;
import cn.webank.mpc.sdk.wemq.MpcWeMQProducer;
import cn.webank.mpc.sdk.wemq.MpcWeMQPushConsumer;
import cn.webank.mumble.framework.common.cache.MumbleGuavaCacheManager;
import cn.webank.mumble.framework.common.threads.MumbleThreadPoolTaskExecutor;
import cn.webank.mumble.framework.integration.data.jedis.strategy.GuavaConsistentHashStrategy;
import cn.webank.mumble.framework.module.seq.integration.dao.SeqMemoryDAO;
import cn.webank.mumble.mpc.giftcard.hangxin.client.SajtIssueInvoiceServiceStub;
import cn.webank.mumble.mpc.giftcard.hangxin.seq.intergration.dao.impl.MpcSeqMemoryRedisDAO;
import cn.webank.mumble.mpc.giftcard.hangxin.seq.service.impl.MpcSeqRedisPojoService;
import cn.webank.mumble.mpc.giftcard.service.retry.GiftCardWeMQRetryMessage;
import cn.webank.mumble.mpc.giftcard.service.retry.impl.GiftCardWeMQRetryMessageWithHbase;
import cn.webank.wemq.consumer.WeMQMessageListenerConcurrently;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 公共配置类，所有模块共用。
 *
 * @author leaflyhuang
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@EnableCaching
public class ServerConfiguration extends WebMvcConfigurerAdapter implements SchedulingConfigurer {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerConfiguration.class);

    @Value("${threadpool.core-pool-size:48}")
    private int corePoolSize;

    @Value("${threadpool.keep-alive-seconds:60}")
    private int keepAliveSeconds;

    @Value("${threadpool.max-pool-size:48}")
    private int maxPoolSize;

    @Value("${threadpool.queue-capacity:300}")
    private int queueCapacity;

    @Value("${threadpool.allow-core-thread-timeout:true}")
    private boolean allowCoreThreadTimeOut;

    @Value("${threadpool.await-termination-seconds:60}")
    private int awaitTerminationSeconds;

    @Value("${threadpool.task.max-pool-size:8}")
    private int taskMaxPoolSize;

    @Value("${threadpool.wait-for-task-to-complete-on-shutdown:true}")
    private boolean waitForTasksToCompleteOnShutdown;

    @Value("${http.request.timeout:5000}")
    private int httpRequestTimeout;

    @Value("${http.connect.timeout:5000}")
    private int httpConnectTimeout;

    @Value("${http.read.timeout:5000}")
    private int httpReadTimeout;

    @Value("${proxy.host}")
    private String innerProxyHost;

    @Value("${proxy.port}")
    private int innerProxyPort;

    private final static String TOPIC_SPLIT = ";";


    @Bean(name = "messageSource")
    public MessageSource messageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:messages");
        messageResource.setDefaultEncoding("UTF-8");
        messageResource.setCacheSeconds(3600);
        return messageResource;
    }

    @Bean(name = "frontTaskExecutor")
    public ThreadPoolTaskExecutor setFrontTaskExecutor() {
        return getTask();
    }


    @Bean(name = "wemqTaskExecutor")
    public MumbleThreadPoolTaskExecutor setWemqTaskExecutor() {
        MumbleThreadPoolTaskExecutor executor = new MumbleThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        return executor;
    }

    private ThreadPoolTaskExecutor getTask() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

    @Bean(name = "taskScheduler")
    public ThreadPoolTaskScheduler setTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(taskMaxPoolSize);
        return scheduler;
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(setTaskScheduler());
    }


    @Bean(name = "mpcMultiJedisShardedPool")
    public MpcMultiJedisShardedPool setMpcJedisShardedPool(@Value("${redis.max-active:50}") int redisMaxActive,
                                                        @Value("${redis.max-idle:5}") int redisMaxIdle, @Value("${redis.min-idle:5}") int redisMinIdle,
                                                        @Value("${redis.max-wait-millis:5}") int redisMaxWaitMillis,
                                                        @Value("${redis.test-on-borrow:false}") boolean redisTestOnBorrow,
                                                        @Value("${redis.test-on-idle:true}") boolean redisTestOnIdle,
                                                        @Value("${redis.master-name:}") String redisMasterName, @Value("${redis.sentinels:}") String redisSentinels,
                                                        @Value("${redis.sharded-servers:}") String redisShardedServers,
                                                        @Value("${redis.auth:}") String redisAuth) throws IOException {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisMaxActive);
        config.setMaxIdle(redisMaxIdle);
        config.setMinIdle(redisMinIdle);
        config.setMaxWaitMillis(redisMaxWaitMillis);
        config.setTestOnBorrow(redisTestOnBorrow);
        config.setTestWhileIdle(redisTestOnIdle);

        MpcMultiJedisShardedPool mpcMultiJedisShardedPool = new MpcMultiJedisShardedPool();
        mpcMultiJedisShardedPool.setAuth(redisAuth);
        mpcMultiJedisShardedPool.setConnectionTimeout(2000);
        mpcMultiJedisShardedPool.setJedisPoolConfig(config);
        mpcMultiJedisShardedPool.setJedisShardInfos(redisShardedServers);
        mpcMultiJedisShardedPool.setModStrategy(new GuavaConsistentHashStrategy());
        mpcMultiJedisShardedPool.setSoTimeout(2000);
        try {
            mpcMultiJedisShardedPool.afterPropertiesSet();
        } catch (Exception e) {
            LOGGER.error("mpcMultiJedisShardedPool.afterPropertiesSet", e);
        }
        return mpcMultiJedisShardedPool;
    }

    @Bean(name = "cn.webank.mumble.mpc.giftcard.hangxin.seq.intergration.dao.impl.MpcSeqMemoryRedisDAO")
    public MpcSeqMemoryRedisDAO mpcSeqMemoryRedisDAO(
            @Qualifier("mpcMultiJedisShardedPool") MpcMultiJedisShardedPool mpcMultiJedisShardedPool) {
        MpcSeqMemoryRedisDAO dao = new MpcSeqMemoryRedisDAO();
        dao.setMpcMultiJedisShardedPool(mpcMultiJedisShardedPool);
        return dao;
    }

    @Bean(name = "cn.webank.mumble.mpc.giftcard.hangxin.seq.service.impl.MpcSeqRedisPojoService")
    public MpcSeqRedisPojoService mpcSeqRedisPojoService(
            @Qualifier("cn.webank.mumble.mpc.giftcard.hangxin.seq.intergration.dao.impl.MpcSeqMemoryRedisDAO") SeqMemoryDAO seqRedisDAO,
            @Value("${mumble.seq.retry-millseconds:10000}") long retryTimeoutMills,
            @Value("${wemq.sysId}") String sysId
    ) {
        MpcSeqRedisPojoService service = new MpcSeqRedisPojoService();
        service.setSeqRedisDAO(seqRedisDAO);
        service.setRetryTimeoutMills(retryTimeoutMills);
        service.setSysId(sysId);

        return service;
    }

    @Bean(name = "wcFrontRestTemplate")
    public RestTemplate wcFrontRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(httpRequestTimeout);
        httpRequestFactory.setConnectTimeout(httpConnectTimeout);
        httpRequestFactory.setReadTimeout(httpReadTimeout);

        return new RestTemplate(httpRequestFactory);
    }


    @Bean(name = "cn.webank.mumble.mpc.giftcard.service.retry.GiftCardWeMQRetryMessage")
    public GiftCardWeMQRetryMessage giftCardWeMQRetryMessage(
            GiftCardWeMQRetryMessageWithHbase giftCardWeMQRetryMessageWithHbase) {
        return giftCardWeMQRetryMessageWithHbase;
    }


    @Bean(name = "mpcWeMQProducer")
    public MpcWeMQProducer initMpcWeMQProducer(@Value("${wemq.username}") String weMQUserName,
                                               @Value("${wemq.weMQPassword}") String weMQPassword,
                                               @Value("${wemq.sysId}") String sysId,
                                               @Value("${wemq.nameServerAddress}") String nameServerAddress) {
        return new MpcWeMQProducer(weMQUserName, weMQPassword, sysId, nameServerAddress);
    }

    @Bean(name = "mpcWeMQPushConsumer")
    public MpcWeMQPushConsumer initMpcWeMQPushConsumer(@Value("${wemq.username}") String weMQUserName,
                                                       @Value("${wemq.weMQPassword}") String weMQPassword,
                                                       @Value("${wemq.sysId}") String sysId,
                                                       @Value("${wemq.nameServerAddress}") String nameServerAddress,
                                                       @Value("${wemq.topics}") String topics,
                                                       @Value("${wemq.pull.size}") int weMQPullSize,
                                                       @Value("${wemq.ack.win}") int wemqAckWin,
                                                       @Qualifier("cn.webank.mpc.sdk.wemq.MpcWeMQDispatcherService") MpcWeMQDispatcherService weMqDispatcherService,
                                                       @Qualifier("wemqTaskExecutor") MumbleThreadPoolTaskExecutor wemqTaskExecutor) {
        List<String> topicList = new ArrayList<String>();
        if (StringUtils.isBlank(topics)) {
            return null;
        }

        for (String topic : topics.split(TOPIC_SPLIT)) {
            topicList.add(topic);
        }

        WeMQMessageListenerConcurrently subscribe = new WeMQMessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus handleMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                return weMqDispatcherService.handleMessage(msgs);
            }


        };

        return new MpcWeMQPushConsumer(weMQUserName, weMQPassword, sysId, nameServerAddress, wemqTaskExecutor, topicList,
                weMQPullSize, wemqAckWin, subscribe);
    }

    @Bean(name = "hangxinWebService")
    public SajtIssueInvoiceServiceStub hangxinWebService(@Value("${outer.network.proxy.host}") String host,
                                                         @Value("${outer.network.proxy.port}") int port,
                                                         @Value("${hangxin.invoice.server.auth.name}") String authName,
                                                         @Value("${hangxin.invoice.server.auth.password}") String authPassword,
                                                         @Value("${hangxin.invoice.server.ip}") String serverIp,
                                                         @Value("${hangxin.invoice.server.port}") String serverPort,
                                                         @Value("${proxy.host}") String sitProxyHost,
                                                         @Value("${proxy.port}") int sitProxyPort,
                                                         @Value("${system.env}") String env
                                                         ) {
        try {
            SajtIssueInvoiceServiceStub ws = new SajtIssueInvoiceServiceStub(serverIp, serverPort, "2.0.0");
            ServiceClient client = ws._getServiceClient();
            client.getOptions().setTimeOutInMilliSeconds(30000);
            HttpTransportProperties.ProxyProperties proxyProperties = new HttpTransportProperties.ProxyProperties();
            proxyProperties.setProxyName(host);
            proxyProperties.setProxyPort(port);

            /*
            if("sit".equalsIgnoreCase(env)) {
                proxyProperties.setProxyName(sitProxyHost);
                proxyProperties.setProxyPort(sitProxyPort);
            }
            */

            client.getOptions().setTransportInProtocol(Constants.TRANSPORT_HTTP);
            client.getOptions().setProperty(HTTPConstants.PROXY, proxyProperties);

            HttpTransportProperties.Authenticator authenticator = new HttpTransportProperties.Authenticator();
            authenticator.setUsername(authName);
            authenticator.setPassword(authPassword);
            client.getOptions().setProperty(HTTPConstants.AUTHENTICATE, authenticator);

            return ws;
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }

        return null;
    }

    @Bean(name = "cacheManager")
    public MumbleGuavaCacheManager getCacheManager(@Value("${guava.cache.config}") String guavaCacheConfig) {
        Map<String, String> config = new HashMap<String, String>();
        config.put("baseInfoCache", guavaCacheConfig);
        MumbleGuavaCacheManager cacheManager = new MumbleGuavaCacheManager(config);
        return cacheManager;
    }
}