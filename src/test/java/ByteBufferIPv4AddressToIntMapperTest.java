import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lesindmitrii.ipcounterapp.mapper.impl.ByteBufferIPv4AddressToIntMapper;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteBufferIPv4AddressToIntMapperTest {
    private ByteBufferIPv4AddressToIntMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new ByteBufferIPv4AddressToIntMapper();
    }

    @Test
    public void edgeCases() throws UnknownHostException {
        String MIN_IPv4 = "0.0.0.0";
        assertEquals(Inet4Address.getByName(MIN_IPv4).hashCode(), mapper.applyAsInt(MIN_IPv4));
        String MAX_IPv4 = "255.255.255.255";
        assertEquals(Inet4Address.getByName(MAX_IPv4).hashCode(), mapper.applyAsInt(MAX_IPv4));
    }

    @Test
    public void allPossibleIPv4() throws UnknownHostException {

        String ip;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 256; k++) {
                    for (int l = 0; l < 256; l++) {
                        ip = "" + i + "." + j + "." + k + "." + l;
                        assertEquals(Inet4Address.getByName(ip).hashCode(), mapper.applyAsInt(ip));
                    }
                }

            }

        }

    }

}
