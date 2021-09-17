package net.dengzixu;

import net.dengzixu.constant.PacketOperationEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PacketOperationEnumTest {
    @Parameterized.Parameter(0)
    public int packetOperation;

    @Parameterized.Parameter(1)
    public PacketOperationEnum packetOperationEnum;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {2, PacketOperationEnum.OPERATION_2},
                {3, PacketOperationEnum.OPERATION_3},
                {5, PacketOperationEnum.OPERATION_5},
                {7, PacketOperationEnum.OPERATION_7},
                {8, PacketOperationEnum.OPERATION_8},
                {11, PacketOperationEnum.OPERATION_UNKNOWN}
        });
    }

    @Test
    public void testOperation7() {
        var result = PacketOperationEnum.getEnum(7);
        Assert.assertEquals(PacketOperationEnum.OPERATION_7, result);
    }

}
