package cn.wxiach.beans.config;

import cn.wxiach.beans.ListableBeanFactory;

/**
 * @author wxiach 2025/1/9
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);
}
