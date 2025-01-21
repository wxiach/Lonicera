package cn.wxiach.aop.framework.autoproxy;

import cn.wxiach.aop.Advisor;
import cn.wxiach.aop.PointcutAdvisor;
import cn.wxiach.aop.aspectj.AspectJExpressionPointcut;
import cn.wxiach.beans.BeanFactory;
import cn.wxiach.beans.BeansException;
import cn.wxiach.beans.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxiach 2025/1/21
 */
public abstract class AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator{

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        super.setBeanFactory(beanFactory);
        if(!(beanFactory instanceof ConfigurableListableBeanFactory)){
            throw new IllegalArgumentException(
                    "AdvisorAutoProxyCreator requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
        initBeanFactory((ConfigurableListableBeanFactory)beanFactory);
    }

    abstract protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory);

    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass) {
        List<Advisor> advisors = findEligibleAdvisors(beanClass);
        if (advisors.isEmpty()) {
            return DO_NOT_PROXY;
        }
        return advisors.toArray();
    }

    protected List<Advisor> findEligibleAdvisors(Class<?> beanClass) {
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        return findAdvisorsThatCanApply(candidateAdvisors, beanClass);
    }

    private List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> beanClass) {
        List<Advisor> eligibleAdvisors = new ArrayList<>();
        for (Advisor advisor : candidateAdvisors) {
            if (advisor instanceof PointcutAdvisor) {
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                AspectJExpressionPointcut expression = (AspectJExpressionPointcut) pointcutAdvisor.getPointcut();
                for (Method method : beanClass.getDeclaredMethods()) {
                    if (expression.matches(method, beanClass)) {
                        eligibleAdvisors.add(advisor);
                    }
                }
            }
        }
        return eligibleAdvisors;
    }

    protected abstract List<Advisor> findCandidateAdvisors();
}
