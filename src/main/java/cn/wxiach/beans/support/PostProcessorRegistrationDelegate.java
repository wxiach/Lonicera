package cn.wxiach.beans.support;

import cn.wxiach.beans.DefaultBeanFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wxiach 2025/1/9
 */
public final class PostProcessorRegistrationDelegate {

    public static void invokeBeanFactoryPostProcessor(DefaultBeanFactory beanFactory) {
        List<BeanFactoryPostProcessor> postProcessors = new ArrayList<>();
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class);
        for (String postProcessorName : postProcessorNames) {
            postProcessors.add((BeanFactoryPostProcessor) beanFactory.getBean(postProcessorName));
        }
        invokeBeanFactoryPostProcessor(beanFactory, postProcessors);
    }

    private static void invokeBeanFactoryPostProcessor(DefaultBeanFactory beanFactory, Collection<? extends BeanFactoryPostProcessor> postProcessors) {
        for (BeanFactoryPostProcessor postProcessor : postProcessors) {
            postProcessor.postProcessBeanFactory(beanFactory);
        }
    }
}
