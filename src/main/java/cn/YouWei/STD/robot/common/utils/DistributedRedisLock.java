package cn.webank.mumble.mpc.giftcard.common.utils;

import cn.webank.mpc.sdk.jedis.MpcMultiJedisShardedPool;
import cn.webank.mumble.framework.integration.data.jedis.pool.MumbleJedis;
import cn.webank.mumble.framework.module.concurrent.DistributedLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

public class DistributedRedisLock implements DistributedLock {
    private static final Logger LOG = LoggerFactory.getLogger(cn.webank.mumble.framework.module.concurrent.DistributedRedisLock.class);

    private MpcMultiJedisShardedPool multiJedisShardedPool;
    private long expireTimeout;
    private TimeUnit expireTimeUnit;
    private String moduleId;
    //是否需要监控锁，打印锁超时信息等
    private boolean isMonitor = true;

    public boolean isMonitor() {
        return isMonitor;
    }

    public void setMonitor(boolean monitor) {
        isMonitor = monitor;
    }


    // /**
    // * 基于redis实现的分布式锁。
    // *
    // * @param moduleId moduleId 模块id，锁的key
    // * @param multiJedisShardedPool redis pool
    // * @param expireTimeout 超时时间
    // * @param expireTimeUnit 单位
    // */
    // public DistributedRedisLock(String moduleId, MultiJedisShardedPool multiJedisShardedPool,
    // long expireTimeout, TimeUnit expireTimeUnit) {
    // this(moduleId, multiJedisShardedPool, expireTimeout, expireTimeUnit, false);
    // }

    /**
     * 基于redis实现的分布式锁。
     *
     * @param moduleId              moduleId 模块id，锁的key
     * @param multiJedisShardedPool redis pool
     * @param expireTimeout         超时时间
     * @param expireTimeUnit        单位
     */
    public DistributedRedisLock(String moduleId, MpcMultiJedisShardedPool multiJedisShardedPool,
                                long expireTimeout, TimeUnit expireTimeUnit) {
        this.multiJedisShardedPool = multiJedisShardedPool;
        this.expireTimeout = expireTimeout;
        this.expireTimeUnit = expireTimeUnit;
        this.moduleId = moduleId;
        Assert.notNull(moduleId, "moduleId is null");
        // this.isMonitor = isMonitor;
    }

    /**
     * 尝试获取锁。
     *
     * @param instanceId      实例的id，可以用ip+port，或者ip+thread id等等
     * @param tryLockTimeout  在此期间一直尝试获取锁
     * @param tryLockTimeUnit 时间单位
     * @return 获取锁结果，true获取到锁，false没有获取到锁
     */
    public boolean tryLock(String instanceId, long tryLockTimeout, TimeUnit tryLockTimeUnit) {

        Assert.notNull(instanceId, "instanceId is null");
        Assert.isTrue(tryLockTimeout > 0, "tryLockTimeout must lager than zero");
        Assert.notNull(moduleId, "moduleId is null");

        long millis = tryLockTimeUnit.toMillis(tryLockTimeout);
        long sleepMillis = millis / RETRY_TIMES;
        sleepMillis = (sleepMillis == 0) ? 1 : sleepMillis;

        try (MumbleJedis jedis =
                     multiJedisShardedPool.getJedisPartition(moduleId).getReadWriteResource()) {

            // 必须是master节点上的操作
            for (int i = 0; i < RETRY_TIMES; i++) {
                String key = moduleId + ":LOCK";
                String value = jedis.get(key);

                if (StringUtils.isNoneBlank(value) && !(value.equals(instanceId))) {
                    // 有锁,instanceId不同 其他实例抢到锁，继续重试;
                } else if (StringUtils.isNoneBlank(value) && value.equals(instanceId)) {
                    // 有锁，instanceId相同，重入锁
                    return true;
                } else {
                    // 锁超时或者没有锁
                    String result = jedis.set(key, instanceId, "NX", "PX",
                            expireTimeUnit.toMillis(expireTimeout));

                    if ("OK".equalsIgnoreCase(result)) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("" + instanceId + " get lock!");
                        }

                        // 启动monitor thread
                        if (this.isMonitor) {
                            Thread monitorThread = new MonitorLockThread(moduleId, instanceId,
                                    multiJedisShardedPool, expireTimeout, expireTimeUnit);
                            monitorThread.setDaemon(true);
                            monitorThread.start();
                        }

                        return true;
                    }
                }

                if (i != (RETRY_TIMES - 1)) {
                    try {
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e) {
                        LOG.warn("tryLock Interrupted", e);
                    }
                }

            }
        }
        return false;
    }

    /**
     * 释放锁。
     *
     * @param instanceId 实例的id,与tryLock的保持一致。
     */
    public void unlock(String instanceId) {
        try (MumbleJedis jedis =
                     multiJedisShardedPool.getJedisPartition(moduleId).getReadWriteResource()) {
            String key = moduleId + ":LOCK";
            String redisInstanceId = jedis.get(key);
            if (StringUtils.isNotBlank(redisInstanceId) && redisInstanceId.equals(instanceId)) {
                jedis.del(key);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("" + instanceId + " unlock!");
            }
        }
    }

    private static class MonitorLockThread extends Thread {
        private static final Logger MONITOR_LOG = LoggerFactory.getLogger(MonitorLockThread.class);

        private MpcMultiJedisShardedPool multiJedisShardedPool;
        private String moduleId;
        private String instanceId;
        private long expireTimeout;
        private TimeUnit expireTimeUnit;

        public MonitorLockThread(String moduleId, String instanceId,
                                 MpcMultiJedisShardedPool multiJedisShardedPool, long expireTimeout,
                                 TimeUnit expireTimeUnit) {
            this.multiJedisShardedPool = multiJedisShardedPool;
            this.instanceId = instanceId;
            this.moduleId = moduleId;
            this.expireTimeout = expireTimeout;
            this.expireTimeUnit = expireTimeUnit;
        }

        public void run() {
            try {
                if (MONITOR_LOG.isDebugEnabled()) {
                    MONITOR_LOG.debug(Thread.currentThread().getName() + " monitor thread start");
                }

                long expireMillis = expireTimeUnit.toMillis(expireTimeout);
                long sleepMillis = expireMillis / 10;
                sleepMillis = (sleepMillis <= 100) ? 100 : sleepMillis;

                long startTime = System.currentTimeMillis();

                while ((System.currentTimeMillis() - startTime) <= expireMillis) {

                    try (MumbleJedis jedis =
                                 multiJedisShardedPool.getJedisPartition(moduleId).getReadWriteResource()) {
                        // 必须是master节点上的操作
                        String key = moduleId + ":LOCK";
                        String value = jedis.get(key);

                        if (StringUtils.isNoneBlank(value) && !(value.equals(instanceId))) {
                            // 有锁,instanceId不同
                            if (MONITOR_LOG.isInfoEnabled()) {
                                MONITOR_LOG.info("" + Thread.currentThread().getName() + " lock:"
                                        + moduleId + " for instanceId:" + instanceId
                                        + " has unlocked or expired!");
                            }

                            // 退出
                            break;

                        } else if (StringUtils.isNoneBlank(value) && value.equals(instanceId)) {
                            // 有锁，instanceId相同
                            if (MONITOR_LOG.isInfoEnabled()) {
                                MONITOR_LOG.info("" + Thread.currentThread().getName() + " lock:"
                                        + moduleId + " for instanceId:" + instanceId + " is holding!");
                            }

                            // 继续监控
                        } else {
                            // 锁超时或已经解锁
                            if (MONITOR_LOG.isInfoEnabled()) {
                                MONITOR_LOG.info("" + Thread.currentThread().getName() + " lock:"
                                        + moduleId + " for instanceId:" + instanceId + " has unlocked or expired !");
                            }
                            // 退出
                            break;
                        }


                    }

                    try {
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e) {
                        MONITOR_LOG.warn("tryLock Interrupted", e);
                    }

                }

                if (MONITOR_LOG.isDebugEnabled()) {
                    MONITOR_LOG.debug(Thread.currentThread().getName() + " monitor thread stop");
                }

            } catch (Exception e) {
                MONITOR_LOG.warn("MonitorLockThread run fail", e);
            }
        }
    }

}

