package cn.wxiach.aop.aspectj;

import cn.wxiach.aop.ProxyMethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/24
 */
public class MethodInvocationProceedingJoinPoint implements ProceedingJoinPoint, JoinPoint {

    private final ProxyMethodInvocation methodInvocation;

    private Signature signature;

    public MethodInvocationProceedingJoinPoint(ProxyMethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object proceed() throws Throwable {
        return methodInvocation.proceed();
    }

    @Override
    public Object getThis() {
        return methodInvocation.getProxy();
    }

    @Override
    public Object getTarget() {
        return methodInvocation.getThis();
    }

    @Override
    public Object[] getArgs() {
        return methodInvocation.getArguments();
    }

    @Override
    public Signature getSignature() {
        if (signature == null) {
            signature = new TargetMethodSignature();
        }
        return signature;
    }

    @Override
    public String getKind() {
        return ProceedingJoinPoint.METHOD_EXECUTION;
    }

    @Override
    public void set$AroundClosure(AroundClosure aroundClosure) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object proceed(Object[] arguments) throws Throwable {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toShortString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toLongString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SourceLocation getSourceLocation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public StaticPart getStaticPart() {
        throw new UnsupportedOperationException();
    }

    private class TargetMethodSignature implements MethodSignature {

        @Override
        public Class<?> getReturnType() {
            return methodInvocation.getMethod().getReturnType();
        }

        @Override
        public Method getMethod() {
            return methodInvocation.getMethod();
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return methodInvocation.getMethod().getParameterTypes();
        }

        @Override
        public String[] getParameterNames() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Class<?>[] getExceptionTypes() {
            return methodInvocation.getMethod().getExceptionTypes();
        }

        @Override
        public String toShortString() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toLongString() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getName() {
            return methodInvocation.getMethod().getName();
        }

        @Override
        public int getModifiers() {
            return methodInvocation.getMethod().getModifiers();
        }

        @Override
        public Class<?> getDeclaringType() {
            return methodInvocation.getMethod().getDeclaringClass();
        }

        @Override
        public String getDeclaringTypeName() {
            return getDeclaringType().getName();
        }
    }
}
