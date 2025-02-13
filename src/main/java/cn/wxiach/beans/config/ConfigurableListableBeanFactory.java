package cn.wxiach.beans.config;

import cn.wxiach.beans.ListableBeanFactory;
import cn.wxiach.beans.NoSuchBeanDefinitionException;

/**
 * @author wxiach 2025/1/20
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    void preInstantiateSingletons();
}
