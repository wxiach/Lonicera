package cn.wxiach.aop.aspectj.annotation;

import cn.wxiach.aop.Advisor;
import cn.wxiach.aop.AspectJBeforeAdvice;
import cn.wxiach.aop.DefaultPointcutAdvisor;
import cn.wxiach.aop.aspectj.AspectJExpressionPointcut;
import cn.wxiach.beans.ListableBeanFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxiach 2025/1/21
 */
public class BeanFactoryAspectJAdvisorsBuilder {

    private final ListableBeanFactory beanFactory;

    public BeanFactoryAspectJAdvisorsBuilder(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public List<Advisor> buildAspectJAdvisors() {
        List<Advisor> advisors = new ArrayList<>();

        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            Class<?> beanType = beanFactory.getType(beanName);

            if (beanType.isAnnotationPresent(Aspect.class)) {
                advisors.addAll(getAdvisors(beanType));
            }
        }

        return advisors;
    }

    private List<Advisor> getAdvisors(Class<?> aspectClass) {
        List<Advisor> advisors = new ArrayList<>();

        Method[] candidateMethods = aspectClass.getDeclaredMethods();
        for (Method method : candidateMethods) {
            if (method.isAnnotationPresent(Before.class)) {
                Before before = method.getAnnotation(Before.class);
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(before.value());
                AspectJBeforeAdvice advice = new AspectJBeforeAdvice(method, pointcut);
                DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
                advisors.add(advisor);
            }
        }

        return advisors;
    }
}
