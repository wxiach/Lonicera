package cn.wxiach.beans.support;

import cn.wxiach.beans.BeansException;
import cn.wxiach.beans.ListableBeanFactory;
import cn.wxiach.beans.config.BeanFactoryPostProcessor;
import cn.wxiach.beans.config.ConfigurableListableBeanFactory;

/**
 * @author wxiach 2025/2/13
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;

    @Override
    default void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory){
    }
}
