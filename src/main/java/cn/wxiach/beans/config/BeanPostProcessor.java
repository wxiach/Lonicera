package cn.wxiach.beans.config;

import cn.wxiach.beans.BeansException;

/**
 * @author wxiach 2025/1/20
 */
public interface BeanPostProcessor {

    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
