package org.mapdb.benchmarks;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.util.*;

public class MapdbBenchmark implements Bench{
    DB db;
    Map<String, String> map;

    public MapdbBenchmark() {
        db = DBMaker.newDirectMemoryDB()
                .transactionDisable()
                .make();
        map = db.getTreeMap("benchmark");
    }

    @Override
    public void put(String key, String val) {
        map.put(key, val);
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public void close() {
        db.close();
    }

}
