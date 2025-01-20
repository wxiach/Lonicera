package cn.wxiach.context.annotation;

import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.BeanDefinitionRegistry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/1/13
 */
public class ConfigurationClassBeanDefinitionReader {
    private final BeanDefinitionRegistry registry;
    public ConfigurationClassBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(List<BeanDefinition> configBeanDefs){
        configBeanDefs.forEach(beanDef -> loadBeanDefinitionsForConfigurationClass(beanDef.getBeanClass()));
    }

    public void loadBeanDefinitionsForConfigurationClass(Class<?> configClass){
        List<Method> beanMethods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .collect(Collectors.toList());

        beanMethods.forEach(method -> {
            String beanName = method.getName();
            Class<?> beanClass = method.getReturnType();
            BeanDefinition beanDefinition = new BeanDefinition(beanClass);
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
            registry.registerBeanDefinition(beanName, beanDefinition);
        });
    }
}
