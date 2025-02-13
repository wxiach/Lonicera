package cn.wxiach.context.annotation;

import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.BeanDefinitionRegistry;
import cn.wxiach.core.annotation.AnnotationMetadata;
import cn.wxiach.core.annotation.AnnotationMetadataReader;
import cn.wxiach.core.util.StringUtils;

import java.util.List;

/**
 * @author wxiach 2025/1/10
 */
public class ConfigurationClassParser {
    private final BeanDefinitionRegistry registry;

    public ConfigurationClassParser(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void parse(List<BeanDefinition> configClasses) {
        configClasses.forEach(beanDefinition -> parseConfigurationClass(beanDefinition.getBeanClass()));
    }

    private void parseConfigurationClass(Class<?> configClass) {
        AnnotationMetadata metadata = AnnotationMetadataReader.getAnnotationMetadata(configClass);
        if (metadata.hasAnnotation(ComponentScan.class)) {
            ComponentScan componentScan = metadata.findAnnotation(ComponentScan.class);
            String packageName = StringUtils.hasText(componentScan.value()) ?
                    componentScan.value() : configClass.getPackage().getName();
            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
            scanner.scan(packageName);
        }
    }
}
