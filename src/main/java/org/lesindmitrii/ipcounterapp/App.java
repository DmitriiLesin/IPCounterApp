package org.lesindmitrii.ipcounterapp;

import org.lesindmitrii.ipcounterapp.counter.impl.BitSetIPv4AddressCounter;
import org.lesindmitrii.ipcounterapp.mapper.impl.ByteBufferIPv4AddressToIntMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;


public class App {
    public static void main(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException("Path to the ip-addresses file is not specified");
        }

        Path filePath = Path.of(args[0]);
        Instant start = Instant.now();
        long count = countIPv4Addresses(filePath);
        long spend = Duration.between(start, Instant.now()).toMillis();

        System.out.format("Number of unique ip addresses %d, counting took %d ms.",count, spend);

    }

    private static long countIPv4Addresses(Path path) {

        try (Stream<String> lines = Files.lines(path)) {
            return lines.mapToInt(new ByteBufferIPv4AddressToIntMapper())
                    .collect(BitSetIPv4AddressCounter::new, BitSetIPv4AddressCounter::add, BitSetIPv4AddressCounter::addAll)
                    .count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
