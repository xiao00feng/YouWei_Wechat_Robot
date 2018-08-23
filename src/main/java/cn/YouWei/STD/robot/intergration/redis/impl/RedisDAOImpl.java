package cn.webank.mumble.mpc.giftcard.intergration.redis.impl;

import cn.webank.mpc.sdk.jedis.MpcMultiJedisShardedPool;
import cn.webank.mumble.framework.integration.data.jedis.pool.MumbleJedis;
import cn.webank.mumble.mpc.giftcard.intergration.redis.RedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author leaflyhuang on 2015/10/24.
 */
@Repository("cn.webank.mumble.mpc.giftcard.intergration.redis.RedisDAO")
public class RedisDAOImpl implements RedisDAO {

    @Autowired
    @Qualifier("mpcMultiJedisShardedPool")
    private MpcMultiJedisShardedPool multiJedisShardedPool;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDAOImpl.class);


    @Override
    public boolean lockKey(String key, String value, long time) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            String result = jedis.set(key, value, "NX", "EX", time);
            if ("OK".equalsIgnoreCase(result)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String set(String key, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.set(key, value);
        }
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.set(key, value, nxxx, expx, time);
        }
    }

    @Override
    public String get(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.get(key);
        }
    }


    @Override
    public Boolean exists(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.exists(key);
        }
    }


    @Override
    public Long del(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.del(key);
        }
    }

    @Override
    public String type(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.type(key);
        }
    }


    //    @Override
    //    public String randomKey() {
    //        return null;
    //    }

    //    @Override
    //    public String rename(String oldkey, String newkey) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Long renamenx(String oldkey, String newkey) {
    //        return null;
    //    }

    @Override
    public Long expire(String key, int seconds) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.expire(key, seconds);
        }
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.expireAt(key, unixTime);
        }
    }

    @Override
    public Long ttl(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.ttl(key);
        }
    }

    @Override
    public Long move(String key, int dbIndex) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.move(key, dbIndex);
        }
    }

    @Override
    public String getSet(String key, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.getSet(key, value);
        }
    }

    //    @Override
    //    public List<String> mget(String... keys) {
    //        return null;
    //    }

    @Override
    public Long setnx(String key, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.setnx(key, value);
        }
    }

    @Override
    public String setex(String key, int seconds, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.setex(key, seconds, value);
        }
    }

    //    @Override
    //    public String mset(String... keysvalues) {
    //        return null;
    //    }

    //    @Override
    //    public Long msetnx(String... keysvalues) {
    //        return null;
    //    }

    @Override
    public Long decrBy(String key, long integer) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.decrBy(key, integer);
        }
    }

    @Override
    public Long decr(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.decr(key);
        }
    }

    @Override
    public Long incrBy(String key, long integer) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.incrBy(key, integer);
        }
    }

    @Override
    public Double incrByFloat(String key, double value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.incrByFloat(key, value);
        }
    }

    @Override
    public Long incr(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.incr(key);
        }
    }

    @Override
    public Long append(String key, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.append(key, value);
        }
    }

    @Override
    public String substr(String key, int start, int end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.substr(key, start, end);
        }
    }

    @Override
    public Long hset(String key, String field, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hset(key, field, value);
        }
    }

    @Override
    public String hget(String key, String field) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hget(key, field);
        }
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hsetnx(key, field, value);
        }
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hmset(key, hash);
        }
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hmget(key, fields);
        }
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hincrBy(key, field, value);
        }
    }

    @Override
    public Double hincrByFloat(String key, String field, double value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hincrByFloat(key, field, value);
        }
    }

    @Override
    public Boolean hexists(String key, String field) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hexists(key, field);
        }
    }

    @Override
    public Long hdel(String key, String... fields) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hdel(key, fields);
        }
    }

    @Override
    public Long hlen(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hlen(key);
        }
    }

    @Override
    public Set<String> hkeys(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hkeys(key);
        }
    }

    @Override
    public List<String> hvals(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hvals(key);
        }
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hgetAll(key);
        }
    }

    @Override
    public Long rpush(String key, String... strings) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.rpush(key, strings);
        }
    }

    @Override
    public Long lpush(String key, String... strings) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.lpush(key, strings);
        }
    }

    @Override
    public Long llen(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.llen(key);
        }
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.lrange(key, start, end);
        }
    }

    @Override
    public String ltrim(String key, long start, long end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.ltrim(key, start, end);
        }
    }

    @Override
    public String lindex(String key, long index) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.lindex(key, index);
        }
    }

    @Override
    public String lset(String key, long index, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.lset(key, index, value);
        }
    }

    @Override
    public Long lrem(String key, long count, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.lrem(key, count, value);
        }
    }

    @Override
    public String lpop(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.lpop(key);
        }
    }

    @Override
    public String rpop(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.rpop(key);
        }
    }

    //    @Override
    //    public String rpoplpush(String srckey, String dstkey) {
    //        return null;
    //    }

    @Override
    public Long sadd(String key, String... members) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.sadd(key, members);
        }
    }

    @Override
    public Set<String> smembers(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.smembers(key);
        }
    }

    @Override
    public Long srem(String key, String... members) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.srem(key, members);
        }
    }

    @Override
    public String spop(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.spop(key);
        }
    }

    @Override
    public Set<String> spop(String key, long count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.spop(key, count);
        }
    }

    //    @Override
    //    public Long smove(String srckey, String dstkey, String giftcard) {
    //        return null;
    //    }

    @Override
    public Long scard(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.scard(key);
        }
    }

    @Override
    public Boolean sismember(String key, String member) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.sismember(key, member);
        }
    }

    //    @Override
    //    public Set<String> sinter(String... keys) {
    //        return null;
    //    }

    //    @Override
    //    public Long sinterstore(String dstkey, String... keys) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Set<String> sunion(String... keys) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Long sunionstore(String dstkey, String... keys) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Set<String> sdiff(String... keys) {
    //        return null;
    //    }

    //    @Override
    //    public Long sdiffstore(String dstkey, String... keys) {
    //        return null;
    //    }

    @Override
    public String srandmember(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.srandmember(key);
        }
    }

    @Override
    public List<String> srandmember(String key, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.srandmember(key, count);
        }
    }

    @Override
    public Long zadd(String key, double score, String member) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zadd(key, score, member);
        }
    }

    @Override
    public Long zadd(String key, double score, String member, ZAddParams params) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zadd(key, score, member, params);
        }
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zadd(key, scoreMembers);
        }
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zadd(key, scoreMembers, params);
        }
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrange(key, start, end);
        }
    }

    @Override
    public Long zrem(String key, String... members) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrem(key, members);
        }
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zincrby(key, score, member);
        }
    }

    @Override
    public Double zincrby(String key, double score, String member, ZIncrByParams params) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zincrby(key, score, member, params);
        }
    }

    @Override
    public Long zrank(String key, String member) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrank(key, member);
        }
    }

    @Override
    public Long zrevrank(String key, String member) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrank(key, member);
        }
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrange(key, start, end);
        }
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeWithScores(key, start, end);
        }
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeWithScores(key, start, end);
        }
    }

    @Override
    public Long zcard(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zcard(key);
        }
    }

    @Override
    public Double zscore(String key, String member) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zscore(key, member);
        }
    }

    //    @Override
    //    public String watch(String... keys) {
    //        return null;
    //    }

    @Override
    public List<String> sort(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.sort(key);
        }
    }

    @Override
    public List<String> sort(String key, SortingParams sortingParameters) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.sort(key, sortingParameters);
        }
    }

    //    @Override
    //    public List<String> blpop(int timeout, String... keys) {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<String> blpop(String... args) {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<String> brpop(String... args) {
    //        return null;
    //    }

    //    @Override
    //    public List<String> blpop(String arg) {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<String> brpop(String arg) {
    //        return null;
    //    }

    @Override
    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.sort(key, sortingParameters, dstkey);
        }
    }

    @Override
    public Long sort(String key, String dstkey) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.sort(key, dstkey);
        }
    }

    //    @Override
    //    public List<String> brpop(int timeout, String... keys) {
    //        return null;
    //    }

    @Override
    public Long zcount(String key, double min, double max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zcount(key, min, max);
        }
    }

    @Override
    public Long zcount(String key, String min, String max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zcount(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByScore(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByScore(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByScore(key, min, max, offset, count);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByScore(key, min, max, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByScoreWithScores(key, min, max);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByScoreWithScores(key, min, max);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByScore(key, max, min);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByScore(key, max, min);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByScore(key, max, min, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByScoreWithScores(key, max, min);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
        }
    }


    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByScore(key, max, min, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByScoreWithScores(key, max, min);
        }
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zremrangeByRank(key, start, end);
        }
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zremrangeByScore(key, start, end);
        }
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zremrangeByScore(key, start, end);
        }
    }

    @Override
    public Long zunionstore(String dstkey, String... sets) {
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(dstkey).getReadWriteResource()) {
            return jedis.zunionstore(dstkey, sets);
        }
    }

    @Override
    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(dstkey).getReadWriteResource()) {
            return jedis.zunionstore(dstkey, params, sets);
        }
    }

    @Override
    public Long zinterstore(String dstkey, String... sets) {
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(dstkey).getReadWriteResource()) {
            return jedis.zinterstore(dstkey, sets);
        }
    }

    @Override
    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(dstkey).getReadWriteResource()) {
            return jedis.zinterstore(dstkey, params, sets);
        }
    }

    @Override
    public Long zlexcount(String key, String min, String max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zlexcount(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByLex(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrangeByLex(key, min, max, offset, count);
        }
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByLex(key, max, min);
        }
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zrevrangeByLex(key, max, min, offset, count);
        }
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zremrangeByLex(key, min, max);
        }
    }

    @Override
    public Long strlen(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.strlen(key);
        }
    }

    @Override
    public Long lpushx(String key, String... string) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.lpushx(key, string);
        }
    }

    @Override
    public Long persist(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.persist(key);
        }
    }

    @Override
    public Long rpushx(String key, String... string) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.rpushx(key);
        }
    }

    //    @Override
    //    public String echo(String string) {
    //        return null;
    //    }

    @Override
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.linsert(key, where, pivot, value);
        }
    }

    //    @Override
    //    public String brpoplpush(String source, String destination, int timeout) {
    //        return null;
    //    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.setbit(key, offset, value);
        }
    }

    @Override
    public Boolean setbit(String key, long offset, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.setbit(key, offset, value);
        }
    }

    @Override
    public Boolean getbit(String key, long offset) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.getbit(key, offset);
        }
    }

    @Override
    public Long setrange(String key, long offset, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.setrange(key, offset, value);
        }
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.getrange(key, startOffset, endOffset);
        }
    }

    @Override
    public Long bitpos(String key, boolean value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.bitpos(key, value);
        }
    }

    @Override
    public Long bitpos(String key, boolean value, BitPosParams params) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.bitpos(key, value, params);
        }
    }

    //    @Override
    //    public List<String> configGet(String pattern) {
    //        return null;
    //    }

    //    @Override
    //    public String configSet(String parameter, String value) {
    //        return null;
    //    }

    //    @Override
    //    public Object eval(String script, int keyCount, String... params) {
    //        return null;
    //    }
    //
    //    @Override
    //    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
    //
    //    }
    //
    //    @Override
    //    public Long publish(String channel, String message) {
    //        return null;
    //    }
    //
    //    @Override
    //    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
    //
    //    }
    //
    //    @Override
    //    public Object eval(String script, List<String> keys, List<String> args) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Object eval(String script) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Object evalsha(String script) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Object evalsha(String sha1, List<String> keys, List<String> args) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Object evalsha(String sha1, int keyCount, String... params) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Boolean scriptExists(String sha1) {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<Boolean> scriptExists(String... sha1) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String scriptLoad(String script) {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<Slowlog> slowlogGet() {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<Slowlog> slowlogGet(long entries) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Long objectRefcount(String string) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String objectEncoding(String string) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Long objectIdletime(String string) {
    //        return null;
    //    }

    @Override
    public Long bitcount(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.bitcount(key);
        }
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.bitcount(key, start, end);
        }
    }

    //    @Override
    //    public Long bitop(BitOP op, String destKey, String... srcKeys) {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<Map<String, String>> sentinelMasters() {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<String> sentinelGetMasterAddrByName(String masterName) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Long sentinelReset(String pattern) {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<Map<String, String>> sentinelSlaves(String masterName) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String sentinelFailover(String masterName) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String sentinelMonitor(String masterName, String ip, int port, int quorum) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String sentinelRemove(String masterName) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String sentinelSet(String masterName, Map<String, String> parameterMap) {
    //        return null;
    //    }

    @Override
    public byte[] dump(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.dump(key);
        }
    }

    @Override
    public String restore(String key, int ttl, byte[] serializedValue) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.restore(key, ttl, serializedValue);
        }
    }

    //    @Override
    //    public Long pexpire(String key, int milliseconds) {
    //        
    //        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
    //            return jedis.pexpire(key, milliseconds);
    //        }
    //    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.pexpire(key, milliseconds);
        }
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.pexpireAt(key, millisecondsTimestamp);
        }
    }

    @Override
    public Long pttl(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.pttl(key);
        }
    }

    //    @Override
    //    public String psetex(String key, int milliseconds, String value) {
    //        
    //        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
    //            return jedis.psetex( key,milliseconds,value);
    //        }
    //    }

    @Override
    public String psetex(String key, long milliseconds, String value) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.psetex(key, milliseconds, value);
        }
    }

    @Override
    public String set(String key, String value, String nxxx) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.set(key, value, nxxx);
        }
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, int time) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.set(key, value, nxxx, expx, time);
        }
    }

    //    @Override
    //    public String clientKill(String client) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clientSetname(String name) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String migrate(String host, int port, String key, int destinationDb, int timeout) {
    //        return null;
    //    }
    //
    //    @Override
    //    public ScanResult<String> scan(int cursor) {
    //        return null;
    //    }
    //
    //    @Override
    //    public ScanResult<String> scan(int cursor, ScanParams params) {
    //        return null;
    //    }

    //    @Override
    //    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
    //        
    //        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
    //            return jedis.hscan( key,cursor);
    //        }
    //    }
    //
    //    @Override
    //    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor, ScanParams params) {
    //        return null;
    //    }

    //    @Override
    //    public ScanResult<String> sscan(String key, int cursor) {
    //        return null;
    //    }
    //
    //    @Override
    //    public ScanResult<String> sscan(String key, int cursor, ScanParams params) {
    //        return null;
    //    }
    //
    //    @Override
    //    public ScanResult<Tuple> zscan(String key, int cursor) {
    //        return null;
    //    }
    //
    //    @Override
    //    public ScanResult<Tuple> zscan(String key, int cursor, ScanParams params) {
    //        return null;
    //    }

    //    @Override
    //    public ScanResult<String> scan(String cursor) {
    //        return null;
    //    }
    //
    //    @Override
    //    public ScanResult<String> scan(String cursor, ScanParams params) {
    //        return null;
    //    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hscan(key, cursor);
        }
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.hscan(key, cursor, params);
        }
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.sscan(key, cursor);
        }
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.sscan(key, cursor, params);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zscan(key, cursor);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.zscan(key, cursor, params);
        }
    }

    //    @Override
    //    public String clusterNodes() {
    //        return null;
    //    }
    //
    //    @Override
    //    public String readonly() {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterMeet(String ip, int port) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterReset(JedisCluster.Reset resetType) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterAddSlots(int... slots) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterDelSlots(int... slots) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterInfo() {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<String> clusterGetKeysInSlot(int slot, int count) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterSetSlotNode(int slot, String nodeId) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterSetSlotMigrating(int slot, String nodeId) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterSetSlotImporting(int slot, String nodeId) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterSetSlotStable(int slot) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterForget(String nodeId) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterFlushSlots() {
    //        return null;
    //    }

    //    @Override
    //    public Long clusterKeySlot(String key) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Long clusterCountKeysInSlot(int slot) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterSaveConfig() {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterReplicate(String nodeId) {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<String> clusterSlaves(String nodeId) {
    //        return null;
    //    }
    //
    //    @Override
    //    public String clusterFailover() {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<Object> clusterSlots() {
    //        return null;
    //    }
    //
    //    @Override
    //    public String asking() {
    //        return null;
    //    }
    //
    //    @Override
    //    public List<String> pubsubChannels(String pattern) {
    //        return null;
    //    }
    //
    //    @Override
    //    public Long pubsubNumPat() {
    //        return null;
    //    }
    //
    //    @Override
    //    public Map<String, String> pubsubNumSub(String... channels) {
    //        return null;
    //    }
    //
    //    @Override
    //    public void close() {
    //
    //    }
    //
    //    @Override
    //    public void setDataSource(Pool<Jedis> jedisPool) {
    //
    //    }

    @Override
    public Long pfadd(String key, String... elements) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.pfadd(key, elements);
        }
    }

    @Override
    public long pfcount(String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.pfcount(key);
        }
    }

    //    @Override
    //    public long pfcount(String... keys) {
    //        return 0;
    //    }
    //
    //    @Override
    //    public String pfmerge(String destkey, String... sourcekeys) {
    //        return null;
    //    }

    @Override
    public List<String> blpop(int timeout, String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.blpop(timeout, key);
        }
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.brpop(timeout, key);
        }
    }

    @Override
    public Long geoadd(String key, double longitude, double latitude, String member) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.geoadd(key, longitude, latitude, member);
        }
    }

    @Override
    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.geoadd(key, memberCoordinateMap);
        }
    }

    @Override
    public Double geodist(String key, String member1, String member2) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.geodist(key, member1, member2);
        }
    }

    @Override
    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.geodist(key, member1, member2, unit);
        }
    }

    @Override
    public List<String> geohash(String key, String... members) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.geohash(key, members);
        }
    }

    @Override
    public List<GeoCoordinate> geopos(String key, String... members) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.geopos(key, members);
        }
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
            GeoUnit unit) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.georadius(key, longitude, latitude, radius, unit);
        }
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit,
            GeoRadiusParam param) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.georadius(key, longitude, latitude, radius, unit, param);
        }
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.georadiusByMember(key, member, radius, unit);
        }
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
            GeoRadiusParam param) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.georadiusByMember(key, member, radius, unit, param);
        }
    }

    @Override
    public List<Long> bitfield(String key, String... arguments) {
        
        try (MumbleJedis jedis = multiJedisShardedPool.getJedisPartition(key).getReadWriteResource()) {
            return jedis.bitfield(key, arguments);
        }
    }
}
