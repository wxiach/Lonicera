package cn.wxiach.context;

import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.BeanDefinitionRegistry;
import cn.wxiach.context.util.AnnotationConfigUtils;
import cn.wxiach.context.util.BeanNameGenerator;

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
        registry.registerBeanDefinition(BeanNameGenerator.generate(beanClass), beanDefinition);
    }


}
