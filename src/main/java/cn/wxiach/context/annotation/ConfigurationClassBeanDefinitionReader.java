package cn.wxiach.context.annotation;

import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.BeanDefinitionRegistry;
import cn.wxiach.core.annotation.AnnotationMetadata;
import cn.wxiach.core.annotation.AnnotationMetadataReader;

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
        loadBeanDefinitionForRegistrars(configClass);
    }

    protected void loadBeanDefinitionForBeanMethod(Method method) {
        BeanDefinition beanDefinition = new BeanDefinition(method.getReturnType());
        registry.registerBeanDefinition(method.getName(), beanDefinition);
    }

    protected void loadBeanDefinitionForRegistrars(Class<?> configClass) {
        for (ImportBeanDefinitionRegistrar registrar : getImportBeanDefinitionRegistrars(configClass)) {
            registrar.registerBeanDefinitions(registry);
        }
    }

    private ImportBeanDefinitionRegistrar[] getImportBeanDefinitionRegistrars(Class<?> configClass) {
        LinkedHashSet<ImportBeanDefinitionRegistrar> registrars = new LinkedHashSet<>();
        AnnotationMetadata metadata = AnnotationMetadataReader.getAnnotationMetadata(configClass);
        for (Import annotation : metadata.findAnnotations(Import.class)) {
            Arrays.stream(annotation.value()).forEach(value -> {
                if (ImportBeanDefinitionRegistrar.class.isAssignableFrom(value)) {
                    registrars.add(instantiateRegistrar(value));
                }
            });
        }
        return registrars.toArray(new ImportBeanDefinitionRegistrar[0]);
    }

    private ImportBeanDefinitionRegistrar instantiateRegistrar(Class<?> registrarClass) {
        try {
            return (ImportBeanDefinitionRegistrar) registrarClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to create instance of " + registrarClass.getName() + " using its default constructor.", e);
        }
    }
}
