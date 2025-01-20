package cn.wxiach.context.annotation;

import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.BeanDefinitionRegistry;
import cn.wxiach.beans.support.BeanDefinitionUtils;
import cn.wxiach.util.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author wxiach 2025/1/13
 */
public class ConfigurationClassBeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    public ConfigurationClassBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(List<BeanDefinition> configBeanDefs) {
        configBeanDefs.forEach(beanDef -> loadBeanDefinitionsForConfigurationClass(beanDef.getBeanClass()));
    }

    protected void loadBeanDefinitionsForConfigurationClass(Class<?> configClass) {

        for (Method method : configClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Bean.class)) {
                loadBeanDefinitionForBeanMethod(method);
            }
        }

        for (Class<?> registrarClass : getImportBeanDefinitionRegistrars(configClass)) {
            loadBeanDefinitionForRegistrars(registrarClass);
        }
    }

    protected void loadBeanDefinitionForBeanMethod(Method method) {
        BeanDefinition beanDefinition = new BeanDefinition(method.getReturnType());
        registry.registerBeanDefinition(method.getName(), beanDefinition);
    }

    protected void loadBeanDefinitionForRegistrars(Class<?> registrarClass) {
        BeanDefinition beanDefinition = new BeanDefinition(registrarClass);
        registry.registerBeanDefinition(BeanDefinitionUtils.generateBeanName(registrarClass), beanDefinition);
    }

    private Class<?>[] getImportBeanDefinitionRegistrars(Class<?> configClass) {
        LinkedHashSet<Class<?>> registrars = new LinkedHashSet<>();
        Deque<Annotation> deque = new LinkedList<>(AnnotationUtils.filterAnnotations(configClass.getAnnotations()));
        while (!deque.isEmpty()) {
            Annotation annotation = deque.poll();
            if (annotation instanceof Import) {
                Arrays.stream(((Import) annotation).value()).forEach(value -> {
                    if (ImportBeanDefinitionRegistrar.class.isAssignableFrom(value)) {
                        registrars.add(value);
                    }
                });
            }
            deque.addAll(AnnotationUtils.filterAnnotations(annotation.annotationType().getAnnotations()));
        }
        return registrars.toArray(new Class<?>[0]);
    }
}
