package cn.wxiach.context.annotation;

import cn.wxiach.beans.ListableBeanFactory;
import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.config.BeanFactoryPostProcessor;
import cn.wxiach.beans.support.BeanDefinitionRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/1/9
 */
public class ConfigurationClassPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ListableBeanFactory beanFactory) {

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

        List<BeanDefinition> configBeanDefs = Arrays.stream(registry.getBeanDefinitionNames())
                .map(registry::getBeanDefinition)
                .filter(this::checkConfigurationClassCandidate)
                .collect(Collectors.toList());

        ConfigurationClassParser parser = new ConfigurationClassParser(registry);
        parser.parse(configBeanDefs);

        ConfigurationClassBeanDefinitionReader reader = new ConfigurationClassBeanDefinitionReader(registry);
        reader.loadBeanDefinitions(configBeanDefs);

        ConfigurationClassEnhancer enhancer = new ConfigurationClassEnhancer();
        enhancer.enhance(configBeanDefs);
    }

    private boolean checkConfigurationClassCandidate(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        return beanClass.isAnnotationPresent(Configuration.class);
    }


}
