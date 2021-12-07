package net.dengzixu.annotation;

import net.dengzixu.constant.BodyCommandEnum;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BodyResolver {
    BodyCommandEnum bodyCommand();
}
