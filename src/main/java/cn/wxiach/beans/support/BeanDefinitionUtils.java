package cn.wxiach.beans.support;

import cn.wxiach.context.annotation.Component;
import cn.wxiach.core.util.StringUtils;

import java.beans.Introspector;
import java.lang.annotation.Annotation;

/**
 * @author wxiach 2025/1/10
 */
public final class BeanDefinitionUtils {

    public static String generateBeanName(Class<?> clazz) {
        return generateBeanName(clazz, null);
    }

    public static String generateBeanName(Class<?> clazz, Annotation annotation) {
        String beanName = "";
        if (annotation != null && annotation.annotationType().equals(Component.class)) {
            beanName = ((Component) annotation).value();
        }
        return StringUtils.hasText(beanName) ? beanName : Introspector.decapitalize(clazz.getSimpleName());
    }
}
