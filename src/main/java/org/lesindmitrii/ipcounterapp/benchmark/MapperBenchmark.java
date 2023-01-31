package org.lesindmitrii.ipcounterapp.benchmark;

import org.lesindmitrii.ipcounterapp.mapper.IPv4AddressToIntMapper;
import org.lesindmitrii.ipcounterapp.mapper.impl.ByteBufferIPv4AddressToIntMapper;
import org.lesindmitrii.ipcounterapp.mapper.impl.SimpleIPv4AddressToIntMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, warmups = 3)
@BenchmarkMode(Mode.AverageTime)
public class MapperBenchmark {

    public static void main(String[] args) throws Exception {

        Options opt = new OptionsBuilder()
                .include(MapperBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

    private final IPv4AddressToIntMapper byteBufferImpl = new ByteBufferIPv4AddressToIntMapper();
    private final IPv4AddressToIntMapper simpleImpl = new SimpleIPv4AddressToIntMapper();
    @Param({"1.1.1.1", "255.255.255.255"})
    public String ip;

    @Benchmark
    public int byteBufferIPv4AddressToIntMapper() {
        return byteBufferImpl.applyAsInt(ip);
    }

    @Benchmark
    public int simpleIPv4AddressToIntMapper() {
        return simpleImpl.applyAsInt(ip);
    }
}
