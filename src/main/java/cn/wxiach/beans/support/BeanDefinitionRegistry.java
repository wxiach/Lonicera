package cn.wxiach.beans.support;

import cn.wxiach.beans.config.BeanDefinition;

/**
 * @author wxiach 2025/1/8
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);

    String[] getBeanDefinitionNames();
}
