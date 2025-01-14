package cn.wxiach.beans.config;

import cn.wxiach.annotations.Configuration;
import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.DefaultBeanFactory;
import cn.wxiach.beans.support.BeanFactoryPostProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/1/9
 */
public class ConfigurationClassPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(DefaultBeanFactory beanFactory) {
        List<BeanDefinition> configBeanDefs = Arrays.stream(beanFactory.getBeanDefinitionNames())
                .map(beanFactory::getBeanDefinition)
                .filter(this::checkConfigurationClassCandidate)
                .collect(Collectors.toList());

        ConfigurationClassParser parser = new ConfigurationClassParser(beanFactory);
        parser.parse(configBeanDefs);

        ConfigurationClassBeanDefinitionReader reader = new ConfigurationClassBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(configBeanDefs);

        ConfigurationClassEnhancer enhancer = new ConfigurationClassEnhancer(beanFactory);
        enhancer.enhance(configBeanDefs);
    }

    private boolean checkConfigurationClassCandidate(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        return beanClass.isAnnotationPresent(Configuration.class);
    }


}
