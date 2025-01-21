package cn.wxiach.aop.framework.autoproxy;

import cn.wxiach.aop.Advisor;
import cn.wxiach.beans.BeanFactory;
import cn.wxiach.beans.BeansException;
import cn.wxiach.beans.config.ConfigurableListableBeanFactory;

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
        return candidateAdvisors;
    }

    protected abstract List<Advisor> findCandidateAdvisors();
}
