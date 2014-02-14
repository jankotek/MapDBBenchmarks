package org.mapdb.benchmarks;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        int size = 5000;
        int time = 5000;
        String fileName = "data.json";

        for (int i = 0; i < args.length; i++) {

            if (args[i].toLowerCase().startsWith("-t")) {
                time = Integer.parseInt(args[i + 1]);
            } else if (args[i].toLowerCase().startsWith("-s")) {
                size = Integer.parseInt(args[i + 1]);
            } else if (args[i].toLowerCase().startsWith("-f")) {
                fileName = args[i + 1];
            }
        }
        Map<String, Map<String, Integer>> benchmark = new HashMap<String, Map<String, Integer>>();
        benchmark.put("Redis", new HashMap<String, Integer>());
        benchmark.put("Mapdb", new HashMap<String, Integer>());
        benchmark.get("Redis").put("read",
                new RedisBenchmark(size, time).runReadTest()
        );
        benchmark.get("Redis").put("write",
                new RedisBenchmark(size, time).runWriteTest()
        );
        benchmark.get("Mapdb").put("read",
                new MapdbBenchmark(size, time).runReadTest()
        );
        benchmark.get("Mapdb").put("write",
                new MapdbBenchmark(size, time).runWriteTest()
        );

        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.println(new JSONObject(benchmark));
        writer.close();
        System.out.println("Creating JSON data file.");
    }
}
