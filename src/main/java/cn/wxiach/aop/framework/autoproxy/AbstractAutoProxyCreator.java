package cn.wxiach.aop.framework.autoproxy;

import cn.wxiach.aop.Advisor;
import cn.wxiach.aop.framework.ProxyFactory;
import cn.wxiach.beans.BeanFactory;
import cn.wxiach.beans.BeanFactoryAware;
import cn.wxiach.beans.BeansException;
import cn.wxiach.beans.config.BeanPostProcessor;

import java.util.List;

/**
 * @author wxiach 2025/1/20
 */
public abstract class AbstractAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

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
        List<Advisor> advisors = getAdvicesAndAdvisorsForBean(bean.getClass());
        if (!advisors.isEmpty()) {
            return createProxy(bean.getClass(), advisors, bean);
        }
        return bean;
    }

    protected abstract List<Advisor> getAdvicesAndAdvisorsForBean(Class<?> beanClass);

    private Object createProxy(Class<?> targetClass, List<Advisor> advisors, Object target) {
        ProxyFactory proxyFactory = new ProxyFactory(target, advisors, targetClass.getInterfaces());
        return proxyFactory.getProxy();
    }

}
