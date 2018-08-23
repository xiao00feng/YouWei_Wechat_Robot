package cn.webank.mumble.mpc.giftcard.service.impl;

import cn.webank.mpc.sdk.jedis.MpcMultiJedisShardedPool;
import cn.webank.mumble.framework.common.exception.SysException;
import cn.webank.mumble.framework.integration.data.jedis.pool.MumbleJedis;
import cn.webank.mumble.mpc.giftcard.JunitBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import redis.clients.jedis.Transaction;

import java.util.List;

public class RedisTest extends JunitBaseTest {

    @Autowired
    @Qualifier("mpcMultiJedisShardedPool")
    private MpcMultiJedisShardedPool mpcMultiJedisShardedPool;
    private static final int ONE_DAY = 24 * 3600;


    @Test
    public void testSeq() {
        initSeq("name","seq",10000);
    }

    @SuppressWarnings("resource")
    public boolean initSeq(String namespace, String seqName, long timeoutMills) {
        long lastTime = System.currentTimeMillis();
        try (MumbleJedis jedis = mpcMultiJedisShardedPool.getJedisPartition(seqName).getReadWriteResource()) {
            while (System.currentTimeMillis() - lastTime < timeoutMills) {
                String fk = formatKey(namespace, seqName);
                jedis.watch(fk);

                if (jedis.exists(fk)) {
                    jedis.unwatch();
                    return true;
                }

                Transaction tx = jedis.multi();
                // 24小时的生命周期
                tx.setex(fk, ONE_DAY, "1000000000000000");
                List<Object> results = tx.exec();
                if (results != null)
                    return true;
            }
        } catch (Exception e) {
            throw new SysException("initSeq fail", e);
        }

        return false;

    }

    private String formatKey(String namespace, String mapName) {
        return namespace + ":" + mapName;
    }


}
