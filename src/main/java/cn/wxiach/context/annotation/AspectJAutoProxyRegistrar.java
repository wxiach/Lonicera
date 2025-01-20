package cn.wxiach.context.annotation;

import cn.wxiach.aop.config.AopConfigUtils;
import cn.wxiach.beans.support.BeanDefinitionRegistry;

/**
 * @author wxiach 2025/1/20
 */
public class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(BeanDefinitionRegistry registry) {
        AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);
    }
}
