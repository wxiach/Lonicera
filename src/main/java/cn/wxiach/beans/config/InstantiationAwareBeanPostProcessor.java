package cn.wxiach.beans.config;

import cn.wxiach.beans.BeansException;

/**
 * 
 * @author wxiach 2025/2/13
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
