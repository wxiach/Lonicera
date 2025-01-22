package cn.wxiach.aop.aspectj;

import org.aopalliance.aop.Advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/21
 */
public abstract class AbstractAspectJAdvice implements Advice {

    private final Method aspectJAdviceMethod;

    private final AspectInstanceFactory aspectInstanceFactory;

    public AbstractAspectJAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        this.aspectJAdviceMethod = aspectJAdviceMethod;
        this.aspectInstanceFactory = aspectInstanceFactory;
    }

    protected void invokeAdviceMethod() throws InvocationTargetException, IllegalAccessException {
        Object aspectInstance = aspectInstanceFactory.getAspectInstance();
        aspectJAdviceMethod.invoke(aspectInstance);
    }
}
