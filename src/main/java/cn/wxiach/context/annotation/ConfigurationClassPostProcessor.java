package cn.wxiach.context.annotation;

import cn.wxiach.beans.ListableBeanFactory;
import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.config.BeanFactoryPostProcessor;
import cn.wxiach.beans.config.ConfigurableListableBeanFactory;
import cn.wxiach.beans.support.BeanDefinitionRegistry;
import cn.wxiach.beans.support.BeanDefinitionRegistryPostProcessor;
import cn.wxiach.core.annotation.AnnotationMetadata;
import cn.wxiach.core.annotation.AnnotationMetadataReader;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/1/9
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {

    Set<Integer> registriesPostProcessed = new HashSet<>();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        int registryId = System.identityHashCode(registry);
        if (registriesPostProcessed.contains(registryId)) {
            throw new IllegalArgumentException("postProcessBeanFactory has already been registered");
        }
        this.registriesPostProcessed.add(registryId);
        processConfigBeanDefinitions(registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        int factoryId = System.identityHashCode(beanFactory);
        if (!registriesPostProcessed.contains(factoryId)) {
            processConfigBeanDefinitions((BeanDefinitionRegistry) beanFactory);
        }
        enhanceConfigurationClasses(beanFactory);
    }

    public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
        List<BeanDefinition> configClasses = Arrays.stream(registry.getBeanDefinitionNames())
                .map(registry::getBeanDefinition)
                .filter(beanDefinition -> {
                    AnnotationMetadata metadata = AnnotationMetadataReader.getAnnotationMetadata(beanDefinition.getBeanClass());
                    return metadata.hasAnnotation(Configuration.class);
                })
                .collect(Collectors.toList());

        ConfigurationClassParser parser = new ConfigurationClassParser(registry);
        parser.parse(configClasses);

        ConfigurationClassBeanDefinitionReader reader = new ConfigurationClassBeanDefinitionReader(registry);
        reader.loadBeanDefinitions(configClasses);
    }

    public void enhanceConfigurationClasses(ConfigurableListableBeanFactory beanFactory) {
        ConfigurationClassEnhancer enhancer = new ConfigurationClassEnhancer();
        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(beanName -> {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            AnnotationMetadata metadata = AnnotationMetadataReader.getAnnotationMetadata(beanDefinition.getBeanClass());
            if (metadata.hasAnnotation(Configuration.class)) {
                enhancer.enhance(beanDefinition);
            }
        });
    }
}
