package cn.wxiach.context;

import cn.wxiach.annotations.Component;

import java.beans.Introspector;

/**
 * @author wxiach 2025/1/10
 */
public final class BeanNameGenerator {

    public static String generate(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = clazz.getAnnotation(Component.class);
            if (!component.value().isEmpty()) return component.value();
        }
        return Introspector.decapitalize(clazz.getSimpleName());
    }
}
