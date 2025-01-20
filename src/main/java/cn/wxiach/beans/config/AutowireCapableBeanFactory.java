package cn.wxiach.beans.config;

import cn.wxiach.beans.BeanFactory;

/**
 * @author wxiach 2025/1/20
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName);

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName);

}
