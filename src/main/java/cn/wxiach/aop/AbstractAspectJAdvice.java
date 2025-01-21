package cn.wxiach.aop;

import cn.wxiach.aop.aspectj.AspectJExpressionPointcut;
import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/21
 */
public abstract class AbstractAspectJAdvice implements Advice {

    private Method method;

    private AspectJExpressionPointcut pointcut;

    public AbstractAspectJAdvice(Method method, AspectJExpressionPointcut pointcut) {
        this.method = method;
        this.pointcut = pointcut;
    }
}
