package cn.wxiach.context;

/**
 * @author wxiach 2025/1/10
 */
public interface BeanNameGenerator {
    public String generateBeanName(Class<?> beanClass);
}
