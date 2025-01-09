package cn.wxiach.context;

import java.beans.Introspector;

/**
 * @author wxiach 2025/1/10
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(Class<?> beanClass) {
        return Introspector.decapitalize(beanClass.getSimpleName());
    }
}
