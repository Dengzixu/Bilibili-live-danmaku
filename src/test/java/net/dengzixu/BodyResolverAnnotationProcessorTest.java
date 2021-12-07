package net.dengzixu;

import net.dengzixu.annotation.BodyResolverAnnotationProcessor;
import org.junit.Test;

public class BodyResolverAnnotationProcessorTest {

    @Test
    public void testBodyResolverUtil() {
        BodyResolverAnnotationProcessor.BODY_RESOLVERS.forEach((key, value) -> {
            System.out.println(key);
        });
    }
}
