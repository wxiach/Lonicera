package cn.wxiach.aop.framework.autoproxy;

import cn.wxiach.beans.BeanFactory;
import cn.wxiach.beans.BeanFactoryAware;
import cn.wxiach.beans.BeansException;
import cn.wxiach.beans.config.BeanPostProcessor;

/**
 * @author wxiach 2025/1/20
 */
public abstract class AbstractAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    protected static final Object[] DO_NOT_PROXY = null;

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return wrapIfNecessary(bean, beanName);
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass());
        if(specificInterceptors != DO_NOT_PROXY) {
            return createProxy(bean);
        }
        return bean;
    }

    protected abstract Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass);

    protected Object createProxy(Object bean) {
        return null;
    }



}
