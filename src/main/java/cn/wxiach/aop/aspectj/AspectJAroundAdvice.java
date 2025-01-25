package cn.wxiach.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/22
 */
public class AspectJAroundAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAroundAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return invokeAdviceMethod(getJoinPoint(methodInvocation), null, null);
    }

    @Override
    protected boolean supportsProceedingJoinPoint() {
        return true;
    }
}
