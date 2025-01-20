package cn.wxiach.beans.support;

import cn.wxiach.beans.BeansException;
import cn.wxiach.beans.NoSuchBeanDefinitionException;
import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.config.BeanPostProcessor;
import cn.wxiach.beans.config.ConfigurableBeanFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wxiach 2025/1/8
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new LinkedList<>();

    @Override
    public Object getBean(String name) {
        return this.doGetBean(name, null);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return this.doGetBean(name, requiredType);
    }

    @SuppressWarnings("unchecked")
    protected <T> T doGetBean(String name, Class<T> requiredType) {
        String beanName = transformedBeanName(name);
        Object beanInstance = getSingleton(beanName);
        if (beanInstance == null) {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            if (beanDefinition == null) {
                throw new NoSuchBeanDefinitionException("No bean with name '" + beanName + "' found");
            }
            if (beanDefinition.isSingleton()) {
                beanInstance = this.getSingleton(beanName, () -> this.createBean(beanName, beanDefinition));
            } else {
                beanInstance = createBean(beanName, beanDefinition);
            }
        }
        if (requiredType != null && !requiredType.isInstance(beanInstance)) {
            throw new BeansException("Bean with name '" + name + "' is not of required type " + requiredType.getName());
        }
        return (T) beanInstance;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    private String transformedBeanName(String name) {
        return name;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);
}
