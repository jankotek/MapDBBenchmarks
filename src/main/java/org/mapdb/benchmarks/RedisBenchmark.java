package org.mapdb.benchmarks;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class RedisBenchmark implements Bench{
    Jedis j = (new JedisPool("localhost")).getResource();

    @Override
    public void put(String key, String val) {
        j.set(key, val);
    }

    @Override
    public String get(String key) {
        return j.get(key);
    }

    @Override
    public void close() {
        j.quit();
    }
}
