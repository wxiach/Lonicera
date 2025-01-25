package cn.wxiach.aop.framework;

import cn.wxiach.aop.Advisor;
import cn.wxiach.aop.PointcutAdvisor;
import cn.wxiach.aop.aspectj.AspectJExpressionPointcut;
import cn.wxiach.util.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxiach 2025/1/22
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), advised.getProxiedInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<MethodInterceptor> chain = getMethodInterceptors(method, advised.getTarget().getClass());
        MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, advised.getTarget(), method, args, chain);
        return invocation.proceed();
    }

    private List<MethodInterceptor> getMethodInterceptors(Method method, Class<?> targetClass) {
        List<MethodInterceptor> interceptors = new ArrayList<>();

        for (Advisor advisor : advised.getAdvisors()) {
            PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
            if (((AspectJExpressionPointcut) pointcutAdvisor.getPointcut()).matches(method, targetClass)) {
                interceptors.add((MethodInterceptor) advisor.getAdvice());
            }
        }

        return interceptors;
    }
}
