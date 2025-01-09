package cn.wxiach.context;

import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.BeanDefinitionRegistry;
import cn.wxiach.beans.support.ConfigurationClassPostProcessor;

/**
 * @author wxiach 2025/1/9
 */
public class AnnotatedBeanDefinitionReader {
    public static final String CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME =
            "cn.wxiach.beans.annotation.configurationAnnotationProcessor";
    private final BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        registerAnnotationConfigProcessor();
    }

    private void registerAnnotationConfigProcessor() {
        if (!this.registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            BeanDefinition beanDefinition = new BeanDefinition(ConfigurationClassPostProcessor.class);
            this.registry.registerBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, beanDefinition);
        }
    }

    public void registerBean(Class<?> beanClass) {
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        String beanName = beanClass.getSimpleName();
        registry.registerBeanDefinition(beanName, beanDefinition);
    }


}
