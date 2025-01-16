package cn.wxiach.context.util;

import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.BeanDefinitionRegistry;
import cn.wxiach.beans.config.ConfigurationClassPostProcessor;

/**
 * @author wxiach 2025/1/15
 */
public class AnnotationConfigUtils {

    public static final String CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME =
            "cn.wxiach.beans.annotation.configurationAnnotationProcessor";

    public static void registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            BeanDefinition beanDefinition = new BeanDefinition(ConfigurationClassPostProcessor.class);
            registry.registerBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, beanDefinition);
        }
    }
}
