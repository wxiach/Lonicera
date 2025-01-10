package cn.wxiach.beans.support;

import cn.wxiach.annotations.Configuration;
import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.DefaultBeanFactory;

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

        // Todo: create the proxy for Configuration Classes
    }

    private boolean checkConfigurationClassCandidate(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        return beanClass.isAnnotationPresent(Configuration.class);
    }


}
