package cn.wxiach.context;

import cn.wxiach.beans.DefaultBeanFactory;
import cn.wxiach.beans.support.PostProcessorRegistrationDelegate;

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

    public void refresh() {
        PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessor(getBeanFactory());
    }

    public abstract DefaultBeanFactory getBeanFactory();
}
