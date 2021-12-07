package net.dengzixu.annotation;

import net.dengzixu.constant.BodyCommandEnum;
import org.reflections.Reflections;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BodyResolverAnnotationProcessor {
    private static final String PACKAGE_NAME = "net.dengzixu.body";

    public static final Map<BodyCommandEnum, Class<?>> BODY_RESOLVERS = new LinkedHashMap<>();

    static {
        Reflections reflections = new Reflections(PACKAGE_NAME);
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(BodyResolver.class);

        annotatedClass.forEach(clazz -> BODY_RESOLVERS.put(clazz.getAnnotation(BodyResolver.class).bodyCommand(), clazz));
    }
}
