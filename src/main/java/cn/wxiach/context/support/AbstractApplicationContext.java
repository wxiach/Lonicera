package cn.wxiach.context.support;

import cn.wxiach.beans.config.ConfigurableListableBeanFactory;
import cn.wxiach.beans.support.DefaultListableBeanFactory;
import cn.wxiach.context.ApplicationContext;

import java.util.Map;

/**
 * @author wxiach 2025/1/9
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws Exception {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public Class<?> getType(String name) {
        return getBeanFactory().getType(name);
    }

    public void refresh() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessor(beanFactory);

        PostProcessorRegistrationDelegate.registerBeanPostProcessor(beanFactory);

        beanFactory.preInstantiateSingletons();
    }

    public abstract DefaultListableBeanFactory getBeanFactory();
}
