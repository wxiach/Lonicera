package cn.wxiach.context.annotation;

import cn.wxiach.beans.support.BeanDefinitionRegistry;

/**
 * @author wxiach 2025/1/20
 */
public interface ImportBeanDefinitionRegistrar {

    void registerBeanDefinitions(BeanDefinitionRegistry registry);
}
