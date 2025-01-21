package cn.wxiach.aop.aspectj.annotation;

import cn.wxiach.aop.Advisor;
import cn.wxiach.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import cn.wxiach.beans.config.ConfigurableListableBeanFactory;

import java.util.List;

/**
 * @author wxiach 2025/1/20
 */
public class AnnotationAwareAspectJAutoProxyCreator extends AbstractAdvisorAutoProxyCreator {

    private BeanFactoryAspectJAdvisorsBuilder aspectJAdvisorsBuilder;

    @Override
    protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        aspectJAdvisorsBuilder = new BeanFactoryAspectJAdvisorsBuilder(beanFactory);
    }

    @Override
    protected List<Advisor> findCandidateAdvisors() {
        return aspectJAdvisorsBuilder.buildAspectJAdvisors();
    }
}
