package cn.wxiach.beans.support;

import cn.wxiach.beans.BeanFactory;
import cn.wxiach.beans.BeansException;

/**
 * @author wxiach 2025/1/14
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
