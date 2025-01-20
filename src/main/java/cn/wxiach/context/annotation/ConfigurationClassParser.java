package cn.wxiach.context.annotation;

import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.support.BeanDefinitionRegistry;
import cn.wxiach.util.StringUtils;

import java.util.List;

/**
 * @author wxiach 2025/1/10
 */
public class ConfigurationClassParser {
    private final BeanDefinitionRegistry registry;

    public ConfigurationClassParser(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void parse(List<BeanDefinition> configBeanDefs) {
        configBeanDefs.forEach(beanDef -> parseConfigurationClass(beanDef.getBeanClass()));
    }

    private void parseConfigurationClass(Class<?> configurationClass) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this.registry);
        if (configurationClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = configurationClass.getAnnotation(ComponentScan.class);
            String basePackageName = componentScan.value();
            if (!StringUtils.hasText(basePackageName)) {
                basePackageName = configurationClass.getPackage().getName();
            }
            scanner.scanPackage(basePackageName);
        }
    }
}
