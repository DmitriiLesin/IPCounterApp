package org.lesindmitrii.ipcounterapp.counter.impl;

import org.lesindmitrii.ipcounterapp.counter.IPv4AddressCounter;

import java.util.HashSet;
import java.util.Set;

public class HashSetIPv4AddressCounter implements IPv4AddressCounter {
    private final Set<Integer> set = new HashSet<>();

    @Override
    public void add(int ip) {
        set.add(ip);
    }

    @Override
    public void addAll(IPv4AddressCounter counter) {
        HashSetIPv4AddressCounter hashSetIpv4AddressCounter = (HashSetIPv4AddressCounter) counter;
        set.addAll(hashSetIpv4AddressCounter.set);
    }

    @Override
    public long count() {
        return set.size();
    }
}
