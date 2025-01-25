package cn.wxiach.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/22
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAfterReturningAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object returnValue = methodInvocation.proceed();
        invokeAdviceMethod(getJoinPoint(methodInvocation), returnValue, null);
        return returnValue;
    }
}
