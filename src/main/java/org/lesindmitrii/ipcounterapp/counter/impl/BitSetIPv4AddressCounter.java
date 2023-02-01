package org.lesindmitrii.ipcounterapp.counter.impl;

import org.lesindmitrii.ipcounterapp.counter.IPv4AddressCounter;

import java.util.BitSet;

public class BitSetIPv4AddressCounter implements IPv4AddressCounter {

    private final BitSet positive = new BitSet(Integer.MAX_VALUE);
    private final BitSet negative = new BitSet(Integer.MAX_VALUE);

    @Override
    public void add(int ip) {
        if (ip >= 0) {
            positive.set(ip);
        } else {
            negative.set(~ip);
        }
    }

    @Override
    public void addAll(IPv4AddressCounter counter) {
        BitSetIPv4AddressCounter bitSetIpv4AddressCounter = (BitSetIPv4AddressCounter) counter;
        negative.or(bitSetIpv4AddressCounter.negative);
        positive.or(bitSetIpv4AddressCounter.positive);
    }

    @Override
    public long count() {
        return Integer.toUnsignedLong(positive.cardinality()) + Integer.toUnsignedLong(negative.cardinality());
    }
}
