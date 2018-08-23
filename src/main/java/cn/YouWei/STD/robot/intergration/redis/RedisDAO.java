package cn.webank.mumble.mpc.giftcard.intergration.redis;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
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
public interface RedisDAO {
    public boolean lockKey(String key, String value, long time);

    public String set(final String key, String value);

    public String set(final String key, final String value, final String nxxx, final String expx, final long time);

    public String get(final String key);

    //    public Long exists(final String... keys);

    public Boolean exists(final String key);

    //    public Long del(final String... keys);

    public Long del(String key);

    public String type(final String key);

    //    public Set<String> keys(final String pattern);

    //    public String randomKey();

    //    public String rename(final String oldkey, final String newkey);
    //
    //    public Long renamenx(final String oldkey, final String newkey);

    public Long expire(final String key, final int seconds);

    public Long expireAt(final String key, final long unixTime);

    public Long ttl(final String key);

    public Long move(final String key, final int dbIndex);

    public String getSet(final String key, final String value);

    //    public List<String> mget(final String... keys);

    public Long setnx(final String key, final String value);

    public String setex(final String key, final int seconds, final String value);

    //    public String mset(final String... keysvalues);

    //    public Long msetnx(final String... keysvalues);

    public Long decrBy(final String key, final long integer);

    public Long decr(final String key);

    public Long incrBy(final String key, final long integer);

    public Double incrByFloat(final String key, final double value);

    public Long incr(final String key);

    public Long append(final String key, final String value);

    public String substr(final String key, final int start, final int end);

    public Long hset(final String key, final String field, final String value);

    public String hget(final String key, final String field);

    public Long hsetnx(final String key, final String field, final String value);

    public String hmset(final String key, final Map<String, String> hash);

    public List<String> hmget(final String key, final String... fields);

    public Long hincrBy(final String key, final String field, final long value);

    public Double hincrByFloat(final String key, final String field, final double value);

    public Boolean hexists(final String key, final String field);

    public Long hdel(final String key, final String... fields);

    public Long hlen(final String key);

    public Set<String> hkeys(final String key);

    public List<String> hvals(final String key);

    public Map<String, String> hgetAll(final String key);

    public Long rpush(final String key, final String... strings);

    public Long lpush(final String key, final String... strings);

    public Long llen(final String key);

    public List<String> lrange(final String key, final long start, final long end);

    public String ltrim(final String key, final long start, final long end);

    public String lindex(final String key, final long index);

    public String lset(final String key, final long index, final String value);

    public Long lrem(final String key, final long count, final String value);

    public String lpop(final String key);

    public String rpop(final String key);

    //    public String rpoplpush(final String srckey, final String dstkey);

    public Long sadd(final String key, final String... members);

    public Set<String> smembers(final String key);

    public Long srem(final String key, final String... members);

    public String spop(final String key);

    public Set<String> spop(final String key, final long count);

    //    public Long smove(final String srckey, final String dstkey, final String giftcard);

    public Long scard(final String key);

    public Boolean sismember(final String key, final String member);

    //    public Set<String> sinter(final String... keys);

    //    public Long sinterstore(final String dstkey, final String... keys);

    //    public Set<String> sunion(final String... keys);

    //    public Long sunionstore(final String dstkey, final String... keys);

    //    public Set<String> sdiff(final String... keys);

    //    public Long sdiffstore(final String dstkey, final String... keys);

    public String srandmember(final String key);

    public List<String> srandmember(final String key, final int count);

    public Long zadd(final String key, final double score, final String member);

    public Long zadd(final String key, final double score, final String member, final ZAddParams params);

    public Long zadd(final String key, final Map<String, Double> scoreMembers);

    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params);

    public Set<String> zrange(final String key, final long start, final long end);

    public Long zrem(final String key, final String... members);

    public Double zincrby(final String key, final double score, final String member);

    public Double zincrby(String key, double score, String member, ZIncrByParams params);

    public Long zrank(final String key, final String member);

    public Long zrevrank(final String key, final String member);

    public Set<String> zrevrange(final String key, final long start, final long end);

    public Set<Tuple> zrangeWithScores(final String key, final long start, final long end);

    public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end);

    public Long zcard(final String key);

    public Double zscore(final String key, final String member);

    //    public String watch(final String... keys);

    public List<String> sort(final String key);

    public List<String> sort(final String key, final SortingParams sortingParameters);

    //    public List<String> blpop(final int timeout, final String... keys);
    //
    //    public List<String> blpop(String... args);
    //
    //    public List<String> brpop(String... args);

    //    public List<String> blpop(String arg);
    //
    //    public List<String> brpop(String arg);

    public Long sort(final String key, final SortingParams sortingParameters, final String dstkey);

    public Long sort(final String key, final String dstkey);

    //    public List<String> brpop(final int timeout, final String... keys);

    public Long zcount(final String key, final double min, final double max);

    public Long zcount(final String key, final String min, final String max);

    public Set<String> zrangeByScore(final String key, final double min, final double max);

    public Set<String> zrangeByScore(final String key, final String min, final String max);

    public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset,
            final int count);

    public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset,
            final int count);

    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max);

    public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max);

    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset,
            final int count);

    public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset,
            final int count);

    public Set<String> zrevrangeByScore(final String key, final double max, final double min);

    public Set<String> zrevrangeByScore(final String key, final String max, final String min);

    public Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset,
            final int count);

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min);

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset,
            final int count);

    public Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset,
            final int count);

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min);

    public Long zremrangeByRank(final String key, final long start, final long end);

    public Long zremrangeByScore(final String key, final double start, final double end);

    public Long zremrangeByScore(final String key, final String start, final String end);

    public Long zunionstore(final String dstkey, final String... sets);

    public Long zunionstore(final String dstkey, final ZParams params, final String... sets);

    public Long zinterstore(final String dstkey, final String... sets);

    public Long zinterstore(final String dstkey, final ZParams params, final String... sets);

    public Long zlexcount(final String key, final String min, final String max);

    public Set<String> zrangeByLex(final String key, final String min, final String max);

    public Set<String> zrangeByLex(final String key, final String min, final String max, final int offset,
            final int count);

    public Set<String> zrevrangeByLex(String key, String max, String min);

    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count);

    public Long zremrangeByLex(final String key, final String min, final String max);

    public Long strlen(final String key);

    public Long lpushx(final String key, final String... string);

    public Long persist(final String key);

    public Long rpushx(final String key, final String... string);

    //    public String echo(final String string);

    public Long linsert(final String key, final LIST_POSITION where, final String pivot, final String value);

    //    public String brpoplpush(String source, String destination, int timeout);

    public Boolean setbit(String key, long offset, boolean value);

    public Boolean setbit(String key, long offset, String value);

    public Boolean getbit(String key, long offset);

    public Long setrange(String key, long offset, String value);

    public String getrange(String key, long startOffset, long endOffset);

    public Long bitpos(final String key, final boolean value);

    public Long bitpos(final String key, final boolean value, final BitPosParams params);

    //    public List<String> configGet(final String pattern);
    //
    //    public String configSet(final String parameter, final String value);
    //
    //    public Object eval(String script, int keyCount, String... params);
    //
    //    public void subscribe(final JedisPubSub jedisPubSub, final String... channels);
    //
    //    public Long publish(final String channel, final String message);
    //
    //    public void psubscribe(final JedisPubSub jedisPubSub, final String... patterns);
    //
    //    public Object eval(String script, List<String> keys, List<String> args);
    //
    //    public Object eval(String script);
    //
    //    public Object evalsha(String script);
    //
    //    public Object evalsha(String sha1, List<String> keys, List<String> args);
    //
    //    public Object evalsha(String sha1, int keyCount, String... params);
    //
    //    public Boolean scriptExists(String sha1);
    //
    //    public List<Boolean> scriptExists(String... sha1);
    //
    //    public String scriptLoad(String script);
    //
    //    public List<Slowlog> slowlogGet();
    //
    //    public List<Slowlog> slowlogGet(long entries);
    //
    //    public Long objectRefcount(String string);
    //
    //    public String objectEncoding(String string);
    //
    //    public Long objectIdletime(String string);

    public Long bitcount(final String key);

    public Long bitcount(final String key, long start, long end);

    //    public Long bitop(BitOP op, final String destKey, String... srcKeys);
    //
    //    public List<Map<String, String>> sentinelMasters();
    //
    //    public List<String> sentinelGetMasterAddrByName(String masterName);
    //
    //    public Long sentinelReset(String pattern);
    //
    //    public List<Map<String, String>> sentinelSlaves(String masterName);
    //
    //    public String sentinelFailover(String masterName);
    //
    //    public String sentinelMonitor(String masterName, String ip, int port, int quorum);
    //
    //    public String sentinelRemove(String masterName);
    //
    //    public String sentinelSet(String masterName, Map<String, String> parameterMap);

    public byte[] dump(final String key);

    public String restore(final String key, final int ttl, final byte[] serializedValue);

//    public Long pexpire(final String key, final int milliseconds);

    public Long pexpire(final String key, final long milliseconds);

    public Long pexpireAt(final String key, final long millisecondsTimestamp);

    public Long pttl(final String key);

    //    public String psetex(final String key, final int milliseconds, final String value);

    public String psetex(final String key, final long milliseconds, final String value);

    public String set(final String key, final String value, final String nxxx);

    public String set(final String key, final String value, final String nxxx, final String expx, final int time);

    //    public String clientKill(final String client);
    //
    //    public String clientSetname(final String name);
    //
    //    public String migrate(final String host, final int port, final String key, final int destinationDb,
    //            final int timeout);
    //
    //    public ScanResult<String> scan(int cursor);
    //
    //    public ScanResult<String> scan(int cursor, final ScanParams params);

    //    public ScanResult<Map.Entry<String, String>> hscan(final String key, int cursor);
    //
    //    public ScanResult<Map.Entry<String, String>> hscan(final String key, int cursor, final ScanParams params);

    //    public ScanResult<String> sscan(final String key, int cursor);
    //
    //    public ScanResult<String> sscan(final String key, int cursor, final ScanParams params);

    //    public ScanResult<Tuple> zscan(final String key, int cursor);
    //
    //    public ScanResult<Tuple> zscan(final String key, int cursor, final ScanParams params);

    //    public ScanResult<String> scan(final String cursor);
    //
    //    public ScanResult<String> scan(final String cursor, final ScanParams params);

    public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor);

    public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor, final ScanParams params);

    public ScanResult<String> sscan(final String key, final String cursor);

    public ScanResult<String> sscan(final String key, final String cursor, final ScanParams params);

    public ScanResult<Tuple> zscan(final String key, final String cursor);

    public ScanResult<Tuple> zscan(final String key, final String cursor, final ScanParams params);

    //    public String clusterNodes();
    //
    //    public String readonly();
    //
    //    public String clusterMeet(final String ip, final int port);
    //
    //    public String clusterReset(final Reset resetType);
    //
    //    public String clusterAddSlots(final int... slots);
    //
    //    public String clusterDelSlots(final int... slots);
    //
    //    public String clusterInfo();
    //
    //    public List<String> clusterGetKeysInSlot(final int slot, final int count);
    //
    //    public String clusterSetSlotNode(final int slot, final String nodeId);
    //
    //    public String clusterSetSlotMigrating(final int slot, final String nodeId);
    //
    //    public String clusterSetSlotImporting(final int slot, final String nodeId);
    //
    //    public String clusterSetSlotStable(final int slot);
    //
    //    public String clusterForget(final String nodeId);
    //
    //    public String clusterFlushSlots();

    //    public Long clusterKeySlot(final String key);
    //
    //    public Long clusterCountKeysInSlot(final int slot);
    //
    //    public String clusterSaveConfig();
    //
    //    public String clusterReplicate(final String nodeId);
    //
    //    public List<String> clusterSlaves(final String nodeId);
    //
    //    public String clusterFailover();
    //
    //    public List<Object> clusterSlots();
    //
    //    public String asking();
    //
    //    public List<String> pubsubChannels(String pattern);
    //
    //    public Long pubsubNumPat();
    //
    //    public Map<String, String> pubsubNumSub(String... channels);
    //
    //    public void close();
    //
    //    public void setDataSource(Pool<Jedis> jedisPool);

    public Long pfadd(final String key, final String... elements);

    public long pfcount(final String key);

    //    public long pfcount(String... keys);
    //
    //    public String pfmerge(final String destkey, final String... sourcekeys);

    public List<String> blpop(int timeout, String key);

    public List<String> brpop(int timeout, String key);

    public Long geoadd(String key, double longitude, double latitude, String member);

    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap);

    public Double geodist(String key, String member1, String member2);

    public Double geodist(String key, String member1, String member2, GeoUnit unit);

    public List<String> geohash(String key, String... members);

    public List<GeoCoordinate> geopos(String key, String... members);

    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit
            unit);

    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit,
            GeoRadiusParam param);

    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit);

    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
            GeoRadiusParam param);

    public List<Long> bitfield(String key, String... arguments);
}
