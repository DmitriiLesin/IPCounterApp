import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lesindmitrii.ipcounterapp.counter.impl.BitSetIPv4AddressCounter;


import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BitSetIPv4AddressCounterTest {
    private BitSetIPv4AddressCounter counter;

    @BeforeEach
    public void setup() {
        counter = new BitSetIPv4AddressCounter();
    }

    @Test
    public void edgeCases() {
        counter.add(Integer.MIN_VALUE);
        counter.add(Integer.MAX_VALUE);
        assertEquals(2, counter.count());
    }

    @Test
    public void allPossibleIPv4() {

        int BYTES_COUNT = 256;
        byte[] bytes = new byte[BYTES_COUNT];

        byte b = 0;
        for (int i = 0; i < BYTES_COUNT; i++) {
            bytes[i] = b;
            b++;
        }

        ByteBuffer bf = ByteBuffer.allocate(Integer.BYTES);
        for (int i = 0; i < 256; i++) {
            bf.put(0, bytes[i]);
            for (int j = 0; j < 256; j++) {
                bf.put(1, bytes[j]);
                for (int k = 0; k < 256; k++) {
                    bf.put(2, bytes[k]);
                    for (int l = 0; l < 256; l++) {
                        bf.put(3, bytes[l]);

                        counter.add(bf.getInt(0));

                    }
                }
            }
        }

        long uniqueIPCount = (long) Math.pow(2, 32);

        assertEquals(uniqueIPCount, counter.count());
    }
}
