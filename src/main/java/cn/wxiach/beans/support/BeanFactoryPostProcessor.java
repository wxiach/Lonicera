package cn.wxiach.beans.support;

import cn.wxiach.beans.DefaultBeanFactory;

/**
 * @author wxiach 2025/1/9
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(DefaultBeanFactory beanFactory);
}
