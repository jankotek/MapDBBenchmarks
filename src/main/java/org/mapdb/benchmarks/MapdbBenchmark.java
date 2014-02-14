package org.mapdb.benchmarks;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.util.*;

public class MapdbBenchmark {
    private int time = 5000;
    private int size = 100000;
    DB db = DBMaker.newDirectMemoryDB()
            .transactionDisable()
            .make();
    private Map<String, String> map = db.getTreeMap("benchmark");
    List<String> keys = new ArrayList<String>();
    Random rand = new Random();

    public MapdbBenchmark(int size, int time) {
        this.size = size;
        this.time = time;
        for (Integer i = 0; i < size; i++) {
            String k = i.toString();
            keys.add(k);
            map.put(k, k);
        }
    }

    public int runReadTest() {
        int i = 0;
        for (long t = System.currentTimeMillis() + time; t > System.currentTimeMillis(); ) {
            i++;
            map.get(keys.get(rand.nextInt(size)));
        }
        System.out.println("Mapdb: read benchmark " + i);
        return i;
    }

    public int runWriteTest() {
        int i = 0;
        for (long t = System.currentTimeMillis() + time; t > System.currentTimeMillis(); ) {
            i++;
            String k = keys.get(rand.nextInt(size));
            map.put(k, k);
        }
        System.out.println("Mapdb: write benchmark " + i);
        return i;
    }
}
