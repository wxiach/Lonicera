package cn.wxiach.context.support;

import cn.wxiach.beans.ListableBeanFactory;
import cn.wxiach.beans.config.BeanFactoryPostProcessor;
import cn.wxiach.beans.config.BeanPostProcessor;
import cn.wxiach.beans.config.ConfigurableListableBeanFactory;
import cn.wxiach.beans.support.BeanDefinitionRegistry;
import cn.wxiach.beans.support.BeanDefinitionRegistryPostProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/1/9
 */
final class PostProcessorRegistrationDelegate {

    private PostProcessorRegistrationDelegate() {
    }

    public static void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class);
            List<BeanDefinitionRegistryPostProcessor> registryProcessors = Arrays.stream(postProcessorNames)
                    .map(name -> beanFactory.getBean(name, BeanDefinitionRegistryPostProcessor.class))
                    .collect(Collectors.toList());
            invokeBeanDefinitionRegistryPostProcessor(registryProcessors, registry);
        }

        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class);
        List<BeanFactoryPostProcessor> postProcessors = Arrays.stream(postProcessorNames)
                .map(name -> beanFactory.getBean(name, BeanFactoryPostProcessor.class))
                .collect(Collectors.toList());
        invokeBeanFactoryPostProcessor(beanFactory, postProcessors);
    }

    private static void invokeBeanDefinitionRegistryPostProcessor(
            Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors, BeanDefinitionRegistry registry) {

        postProcessors.forEach(postProcessor -> postProcessor.postProcessBeanDefinitionRegistry(registry));
    }

    private static void invokeBeanFactoryPostProcessor(
            ConfigurableListableBeanFactory beanFactory, Collection<? extends BeanFactoryPostProcessor> postProcessors) {

        postProcessors.forEach(postProcessor -> postProcessor.postProcessBeanFactory(beanFactory));
    }


    public static void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class);
        for (String postProcessorName : postProcessorNames) {
            beanFactory.addBeanPostProcessor((beanFactory.getBean(postProcessorName, BeanPostProcessor.class)));
        }
    }
}
