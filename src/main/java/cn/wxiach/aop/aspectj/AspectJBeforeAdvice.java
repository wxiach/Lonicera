package cn.wxiach.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/21
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJBeforeAdvice(Method method) {
        super(method);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return null;
    }
}
