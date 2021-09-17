package net.dengzixu;

import net.dengzixu.constant.PacketProtocolVersionEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PacketProtocolVersionTest {
    @Parameterized.Parameter(0)
    public int packetProtocolVersion;

    @Parameterized.Parameter(1)
    public PacketProtocolVersionEnum packetProtocolVersionEnum;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, PacketProtocolVersionEnum.PROTOCOL_VERSION_0},
                {1, PacketProtocolVersionEnum.PROTOCOL_VERSION_1},
                {2, PacketProtocolVersionEnum.PROTOCOL_VERSION_2},
                {3, PacketProtocolVersionEnum.PROTOCOL_VERSION_3},
                {-1, PacketProtocolVersionEnum.PROTOCOL_VERSION_UNKNOWN}
        });
    }

    @Test
    public void testGetEnum() {
        Assert.assertEquals(packetProtocolVersionEnum, PacketProtocolVersionEnum.getEnum(packetProtocolVersion));
    }

}
