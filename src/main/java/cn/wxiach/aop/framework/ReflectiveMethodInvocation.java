package cn.wxiach.aop.framework;


import cn.wxiach.aop.ProxyMethodInvocation;
import cn.wxiach.aop.support.AopUtils;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wxiach 2025/1/22
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation {

    private final Object proxy;
    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final List<MethodInterceptor> methodInterceptors;
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
                                      List<MethodInterceptor> methodInterceptors) {

        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.methodInterceptors = methodInterceptors;
    }

    @Override
    public Object proceed() throws Throwable {
        if (currentInterceptorIndex == methodInterceptors.size() - 1) {
            return AopUtils.invokeJoinPointUsingReflection(target, method, arguments);
        }
        MethodInterceptor methodInterceptor = methodInterceptors.get(++currentInterceptorIndex);
        return methodInterceptor.invoke(this);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }

    @Override
    public Object getProxy() {
        return proxy;
    }
}
