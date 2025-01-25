package cn.wxiach.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/22
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAfterThrowingAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } catch (Throwable e) {
            invokeAdviceMethod(getJoinPoint(methodInvocation), null, e);
            throw e;
        }
    }
}
