package org.lesindmitrii.ipcounterapp.benchmark;

import org.lesindmitrii.ipcounterapp.counter.IPv4AddressCounter;
import org.lesindmitrii.ipcounterapp.counter.impl.BitSetIPv4AddressCounter;
import org.lesindmitrii.ipcounterapp.counter.impl.HashSetIPv4AddressCounter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@Fork(value = 1, warmups = 3)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CounterBenchmark {
    private final IPv4AddressCounter bitSetImpl = new BitSetIPv4AddressCounter();
    private final IPv4AddressCounter hashSetImpl = new HashSetIPv4AddressCounter();
    private final String ONE_THOUSAND = "1000";
    private final String ONE_MILLION = "1000000";

    private final Map<String, int[]> sampleData = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(CounterBenchmark.class.getSimpleName()).build();

        new Runner(opt).run();
    }

    @Param({ONE_THOUSAND, ONE_MILLION})
    public String ipCount;

    @Benchmark
    public long bitSetIPv4AddressCounter() {
        Arrays.stream(sampleData.get(ipCount)).forEach(bitSetImpl::add);
        return bitSetImpl.count();
    }

    @Benchmark
    public long hashSetIPv4AddressCounter() {
        Arrays.stream(sampleData.get(ipCount)).forEach(hashSetImpl::add);
        return hashSetImpl.count();
    }

    @Setup
    public void setup() {
        sampleData.put(ONE_THOUSAND, createIntArray(Integer.parseInt(ONE_THOUSAND)));
        sampleData.put(ONE_MILLION, createIntArray(Integer.parseInt(ONE_MILLION)));
    }

    private int[] createIntArray(int size) {
        return ThreadLocalRandom.current().ints(size).toArray();
    }


}
