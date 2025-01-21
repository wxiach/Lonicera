package cn.wxiach.aop.config;

import cn.wxiach.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.BeanDefinitionRegistry;

/**
 * @author wxiach 2025/1/20
 */
public class AopConfigUtils {

    public static final String AUTO_PROXY_CREATOR_BEAN_NAME = "cn.wxiach.aop.config.internalAutoProxyCreator";

    public static void registerAspectJAnnotationAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME)) {
            BeanDefinition beanDefinition = new BeanDefinition(AnnotationAwareAspectJAutoProxyCreator.class);
            registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
        }
    }
}
