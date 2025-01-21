package cn.wxiach.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/21
 */
public abstract class AbstractAspectJAdvice implements Advice {

    private Method method;

    public AbstractAspectJAdvice(Method method) {
        this.method = method;
    }
}
