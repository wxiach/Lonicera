package cn.wxiach.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/23
 */
public class AspectJAfterAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAfterAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } finally {
            invokeAdviceMethod(getJoinPoint(methodInvocation), null, null);
        }
    }
}
