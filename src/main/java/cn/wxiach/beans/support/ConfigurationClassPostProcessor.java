package cn.wxiach.beans.support;

import cn.wxiach.annotations.ComponentScan;
import cn.wxiach.annotations.Configuration;
import cn.wxiach.beans.AbstractBeanFactory;
import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.BeanDefinitionRegistry;
import cn.wxiach.beans.DefaultBeanFactory;
import cn.wxiach.context.ClassPathBeanDefinitionScanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxiach 2025/1/9
 */
public class ConfigurationClassPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(DefaultBeanFactory beanFactory) {
        List<BeanDefinition> configCandidates = new ArrayList<>();
        String[] candidateNames = beanFactory.getBeanDefinitionNames();
        for (String candidateName : candidateNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(candidateName);
            if (checkConfigurationClassCandidate(beanDefinition)) {
                configCandidates.add(beanDefinition);
            }
        }
        ConfigurationClassParser parser = new ConfigurationClassParser(beanFactory);
        parser.parse(configCandidates);
    }

    private boolean checkConfigurationClassCandidate(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        return beanClass.isAnnotationPresent(Configuration.class);
    }


}
