package cn.wxiach.context;

import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.BeanDefinitionRegistry;
import cn.wxiach.beans.DefaultBeanFactory;

/**
 * @author wxiach 2025/1/8
 */
public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {
    private final DefaultBeanFactory beanFactory;

    GenericApplicationContext() {
        this.beanFactory = new DefaultBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanFactory.getBeanDefinition(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanFactory.getBeanDefinitionNames();
    }

    @Override
    public DefaultBeanFactory getBeanFactory() {
        return this.beanFactory;
    }
}
