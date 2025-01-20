package cn.wxiach.context.annotation;

import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.BeanDefinitionRegistry;
import cn.wxiach.beans.support.BeanDefinitionUtils;

/**
 * @author wxiach 2025/1/9
 */
public class AnnotatedBeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        AnnotationConfigUtils.registerAnnotationConfigProcessors(registry);
    }

    public void registerBean(Class<?> beanClass) {
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        registry.registerBeanDefinition(BeanDefinitionUtils.generateBeanName(beanClass), beanDefinition);
    }


}
