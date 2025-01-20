package cn.wxiach.context.support;

import cn.wxiach.beans.ListableBeanFactory;
import cn.wxiach.beans.config.BeanFactoryPostProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wxiach 2025/1/9
 */
public final class PostProcessorRegistrationDelegate {

    public static void invokeBeanFactoryPostProcessor(ListableBeanFactory beanFactory) {
        List<BeanFactoryPostProcessor> postProcessors = new ArrayList<>();
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class);
        for (String postProcessorName : postProcessorNames) {
            postProcessors.add((BeanFactoryPostProcessor) beanFactory.getBean(postProcessorName));
        }
        invokeBeanFactoryPostProcessor(beanFactory, postProcessors);
    }

    private static void invokeBeanFactoryPostProcessor(ListableBeanFactory beanFactory, Collection<? extends BeanFactoryPostProcessor> postProcessors) {
        for (BeanFactoryPostProcessor postProcessor : postProcessors) {
            postProcessor.postProcessBeanFactory(beanFactory);
        }
    }
}
