package net.dengzixu;

import net.dengzixu.constant.BodyCommandEnum;
import org.junit.Assert;
import org.junit.Test;

public class BodyCommandEnumTest {

    @Test
    public void BodyCommandTest1() {
        var result = BodyCommandEnum.getEnum("DANMU_MSG");
        Assert.assertEquals(BodyCommandEnum.DANMU_MSG, result);
    }

    @Test
    public void BodyCommandTest2() {
        var result = BodyCommandEnum.getEnum("FUCK");
        Assert.assertEquals(BodyCommandEnum.UNKNOWN, result);
    }
}
