package org.lesindmitrii.ipcounterapp.mapper.impl;

import org.lesindmitrii.ipcounterapp.mapper.IPv4AddressToIntMapper;

import java.nio.ByteBuffer;

public class ByteBufferIPv4AddressToIntMapper implements IPv4AddressToIntMapper {

    private static final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);

    @Override
    public int applyAsInt(String value) {
        buffer.clear();
        readToBuffer(value);
        return buffer.getInt(0);
    }

    //implementation from sun.net.util textToNumericFormatV4 adapted to avoid memory allocation
    private void readToBuffer(String src) {

        long tmpValue = 0;

        boolean newOctet = true;

        int len = src.length();
        if (len == 0 || len > 15) {
            return;
        }

        for (int i = 0; i < len; i++) {
            char c = src.charAt(i);
            if (c == '.') {
                if (newOctet || tmpValue < 0 || tmpValue > 0xff || buffer.position() == 3) {
                    return;
                }

                buffer.put((byte) (tmpValue & 0xff));

                tmpValue = 0;
                newOctet = true;
            } else {
                int digit = Character.digit(c, 10);
                if (digit < 0) {
                    return;
                }
                tmpValue *= 10;
                tmpValue += digit;
                newOctet = false;
            }
        }
        if (newOctet || tmpValue < 0 || tmpValue >= (1L << ((4 - buffer.position()) * 8))) {
            return;
        }
        switch (buffer.position()) {
            case 0:
                buffer.put((byte) ((tmpValue >> 24) & 0xff));
            case 1:
                buffer.put((byte) ((tmpValue >> 16) & 0xff));
            case 2:
                buffer.put((byte) ((tmpValue >> 8) & 0xff));
            case 3:
                buffer.put((byte) ((tmpValue) & 0xff));
        }

    }

}
