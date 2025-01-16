package cn.wxiach.context.util;

import cn.wxiach.annotations.Component;
import cn.wxiach.core.util.StringUtils;

import java.beans.Introspector;
import java.lang.annotation.Annotation;

/**
 * @author wxiach 2025/1/10
 */
public final class BeanNameGenerator {

    public static String generate(Class<?> clazz) {
        return generate(clazz, null);
    }

    public static String generate(Class<?> clazz, Annotation annotation) {
        String beanName = "";
        if (annotation != null && annotation.annotationType().equals(Component.class)) {
            beanName = ((Component) annotation).value();
        }
        return StringUtils.hasText(beanName) ? beanName : Introspector.decapitalize(clazz.getSimpleName());
    }
}
