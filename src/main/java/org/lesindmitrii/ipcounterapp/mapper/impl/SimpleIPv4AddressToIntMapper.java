package org.lesindmitrii.ipcounterapp.mapper.impl;

import org.lesindmitrii.ipcounterapp.mapper.IPv4AddressToIntMapper;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class SimpleIPv4AddressToIntMapper implements IPv4AddressToIntMapper {
    @Override
    public int applyAsInt(String value) {
        try {
            return Inet4Address.getByName(value).hashCode();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
