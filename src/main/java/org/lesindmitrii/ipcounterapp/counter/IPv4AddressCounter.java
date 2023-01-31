package org.lesindmitrii.ipcounterapp.counter;

public interface IPv4AddressCounter {
    void add(int ip);
    void addAll(IPv4AddressCounter counter);
    long count();
}
