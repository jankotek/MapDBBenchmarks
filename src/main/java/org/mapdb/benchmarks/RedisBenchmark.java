package org.mapdb.benchmarks;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RedisBenchmark {
    private int time = 5000;
    private int size = 100000;
    Jedis j = (new JedisPool("localhost")).getResource();
    List<String> keys = new ArrayList<String>();
    Random rand = new Random();

    public RedisBenchmark(int size, int time) {
        this.time=time;
        this.size=size;
        for (Integer i = 0; i < size; i++) {
            String k = i.toString();
            keys.add(k);
            j.set(k, k);
        }
    }

    public int runReadTest() {
        int i = 0;
        for (long t = System.currentTimeMillis() + time; t > System.currentTimeMillis(); ) {
            i++;
            j.get(keys.get(rand.nextInt(size)));
        }
        System.out.println("Redis: read benchmark " + i);
        return i;
    }

    public int runWriteTest() {
        int i = 0;
        for (long t = System.currentTimeMillis() + time; t > System.currentTimeMillis(); ) {
            i++;
            String k = keys.get(rand.nextInt(size));
            j.set(k, k);
        }
        System.out.println("Redis: write benchmark " + i);
        return i;
    }

}
