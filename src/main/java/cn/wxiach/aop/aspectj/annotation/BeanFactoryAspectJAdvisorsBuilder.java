package cn.wxiach.aop.aspectj.annotation;

import cn.wxiach.aop.Advisor;
import cn.wxiach.aop.Pointcut;
import cn.wxiach.aop.aspectj.*;
import cn.wxiach.aop.support.DefaultPointcutAdvisor;
import cn.wxiach.beans.ListableBeanFactory;
import org.aspectj.lang.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            for (Annotation annotation : method.getAnnotations()) {
                AspectJAdvisorStrategy strategy = AspectJAdvisorContext.getStrategy(annotation.annotationType());
                if (strategy != null) {
                    advisors.add(strategy.createAdvisor(annotation, method, new AspectInstanceFactory(aspectClass)));
                }
            }
        }

        return advisors;
    }

    private static class AspectJAdvisorContext {
        private static final Map<Class<? extends Annotation>, AspectJAdvisorStrategy> strategies = new HashMap<>();

        static {
            strategies.put(Before.class, new BeforeAdvisorStrategy());
            strategies.put(After.class, new AfterAdvisorStrategy());
            strategies.put(Around.class, new AroundAdvisorStrategy());
            strategies.put(AfterReturning.class, new AfterReturningAdvisorStrategy());
            strategies.put(AfterThrowing.class, new AfterThrowingAdvisorStrategy());
        }

        public static AspectJAdvisorStrategy getStrategy(Class<? extends Annotation> annotationType) {
            return strategies.get(annotationType);
        }
    }

    private interface AspectJAdvisorStrategy {
        DefaultPointcutAdvisor createAdvisor(Annotation annotation, Method method, AspectInstanceFactory instanceFactory);
    }

    private static class BeforeAdvisorStrategy implements AspectJAdvisorStrategy {
        @Override
        public DefaultPointcutAdvisor createAdvisor(Annotation annotation, Method method, AspectInstanceFactory instanceFactory) {
            Pointcut pointcut = new AspectJExpressionPointcut(((Before)annotation).value());
            AbstractAspectJAdvice advice = new AspectJBeforeAdvice(method, instanceFactory);
            return new DefaultPointcutAdvisor(pointcut, advice);
        }
    }

    private static class AfterAdvisorStrategy implements AspectJAdvisorStrategy {
        @Override
        public DefaultPointcutAdvisor createAdvisor(Annotation annotation, Method method, AspectInstanceFactory instanceFactory) {
            Pointcut pointcut = new AspectJExpressionPointcut(((After)annotation).value());
            AbstractAspectJAdvice advice = new AspectJAfterAdvice(method, instanceFactory);
            return new DefaultPointcutAdvisor(pointcut, advice);
        }
    }

    private static class AroundAdvisorStrategy implements AspectJAdvisorStrategy {
        @Override
        public DefaultPointcutAdvisor createAdvisor(Annotation annotation, Method method, AspectInstanceFactory instanceFactory) {
            Pointcut pointcut = new AspectJExpressionPointcut(((Around)annotation).value());
            AbstractAspectJAdvice advice = new AspectJAroundAdvice(method, instanceFactory);
            return new DefaultPointcutAdvisor(pointcut, advice);
        }
    }

    private static class AfterReturningAdvisorStrategy implements AspectJAdvisorStrategy {
        @Override
        public DefaultPointcutAdvisor createAdvisor(Annotation annotation, Method method, AspectInstanceFactory instanceFactory) {
            Pointcut pointcut = new AspectJExpressionPointcut(((AfterReturning)annotation).value());
            AbstractAspectJAdvice advice = new AspectJAfterReturningAdvice(method, instanceFactory);
            advice.setReturningName(((AfterReturning)annotation).returning());
            return new DefaultPointcutAdvisor(pointcut, advice);
        }
    }

    private static class AfterThrowingAdvisorStrategy implements AspectJAdvisorStrategy {
        @Override
        public DefaultPointcutAdvisor createAdvisor(Annotation annotation, Method method, AspectInstanceFactory instanceFactory) {
            Pointcut pointcut = new AspectJExpressionPointcut(((AfterThrowing)annotation).value());
            AbstractAspectJAdvice advice = new AspectJAfterThrowingAdvice(method, instanceFactory);
            advice.setThrowingName(((AfterThrowing)annotation).throwing());
            return new DefaultPointcutAdvisor(pointcut, advice);
        }
    }

}
