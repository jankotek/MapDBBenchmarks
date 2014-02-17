package org.mapdb.benchmarks;


import java.util.concurrent.ConcurrentSkipListMap;


public class ConcurrentSkipListMapBenchmark extends MapdbBenchmark {
    public ConcurrentSkipListMapBenchmark() {
        map = new ConcurrentSkipListMap();
    }

    @Override
    public void close() {
    }
}
