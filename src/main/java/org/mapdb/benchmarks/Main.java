package org.mapdb.benchmarks;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.*;

interface Bench {
    void put(String key, String val);

    String get(String key);

    void close();
}

public class Main {


    //limit maximal memory with java to 1GB: command line param -Xmx1G

    public static void main(String[] args) throws Exception {
        int size = 5000;
        int time = 5000;
        Random rand = new Random();
        String fileName = "data.json";
        for (int i = 0; i < args.length; i++) {

            if (args[i].toLowerCase().equals("-t")) {
                time = Integer.parseInt(args[i + 1]);
            } else if (args[i].toLowerCase().equals("-s")) {
                size = Integer.parseInt(args[i + 1]);
            } else if (args[i].toLowerCase().equals("-f")) {
                fileName = args[i + 1];
            }
        }
        List<Bench> list = new ArrayList<Bench>();
        List<String> decs = new ArrayList<String>();
        list.add((Bench) new RedisBenchmark());
        decs.add("Redis");
        list.add((Bench) new MapdbBenchmark());
        decs.add("MapDB in memory");
        list.add((Bench) new ConcurrentSkipListMapBenchmark());
        decs.add("MapDB skip list");
        Map<String, Integer> ret = new HashMap<String, Integer>();
        for (int a = list.size() - 1; a >= 0; a--) {
            System.out.println(decs.get(a));
            Bench b = list.get(a);
            for (int i = 0; i < size; i++) {
                String k = "" + i;
                b.put(k, k);
            }
            int r = 0;
            for (long t = System.currentTimeMillis() + time; t > System.currentTimeMillis(); ) {
                r++;
                b.get("" + rand.nextInt(size));
            }
            System.out.println(r);
            int w = 0;
            for (long t = System.currentTimeMillis() + time; t > System.currentTimeMillis(); ) {
                w++;
                String k = "" + rand.nextInt(size);
                b.put(k, k);
            }
            System.out.println(w);
            ret.put(decs.get(a) + " read", r);
            ret.put(decs.get(a) + " write", w);

            list.remove(b);
        }

        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.println(new JSONObject(ret));
        writer.close();
        System.out.println("Creating JSON data file " + fileName);
    }
}