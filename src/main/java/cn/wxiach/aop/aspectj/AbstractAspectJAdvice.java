package cn.wxiach.aop.aspectj;

import cn.wxiach.aop.ProxyMethodInvocation;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wxiach 2025/1/21
 */
public abstract class AbstractAspectJAdvice implements Advice {

    private final Method aspectJAdviceMethod;

    private final AspectInstanceFactory aspectInstanceFactory;

    private String returningName;

    private String throwingName;

    private int joinPointArgumentIndex = -1;

    private final Map<String, Integer> argumentBindings = new HashMap<>();

    public AbstractAspectJAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        this.aspectJAdviceMethod = aspectJAdviceMethod;
        this.aspectInstanceFactory = aspectInstanceFactory;
    }

    protected Object invokeAdviceMethod(JoinPoint joinPoint, Object returnValue, Throwable ex)
            throws InvocationTargetException, IllegalAccessException {
        Object[] args = argumentBinding(joinPoint, returnValue, ex);
        Object aspectInstance = aspectInstanceFactory.getAspectInstance();
        return aspectJAdviceMethod.invoke(aspectInstance, args);
    }

    protected Object[] argumentBinding(JoinPoint joinPoint, Object returnValue, Throwable ex) {
        Parameter[] parameters = aspectJAdviceMethod.getParameters();
        calculateArgumentBindings(parameters);

        Object[] adviceInvocationArgs = new Object[parameters.length];
        int numBound = 0;

        if (joinPointArgumentIndex != -1) {
            adviceInvocationArgs[joinPointArgumentIndex] = joinPoint;
            numBound++;
        }

        if (returningName != null) {
            int index = argumentBindings.get(returningName);
            if (index != -1) {
                adviceInvocationArgs[index] = returnValue;
                numBound++;
            }
        }

        if (throwingName != null) {
            int index = argumentBindings.get(throwingName);
            if (index != -1) {
                adviceInvocationArgs[index] = ex;
                numBound++;
            }
        }

        if (numBound != parameters.length) {
            throw new IllegalArgumentException("this advice method requires " + parameters.length + " arguments,  But got " + numBound);
        }

        return adviceInvocationArgs;
    }

    protected JoinPoint getJoinPoint(MethodInvocation methodInvocation) {
        if (!(methodInvocation instanceof ProxyMethodInvocation)) {
            throw new IllegalArgumentException("The methodInvocation is not a ProxyMethodInvocation");
        }
        ProxyMethodInvocation proxyMethodInvocation = (ProxyMethodInvocation) methodInvocation;
        return new MethodInvocationProceedingJoinPoint(proxyMethodInvocation);
    }

    protected void calculateArgumentBindings(Parameter[] parameters) {
        if (parameters.length == 0) {
            return;
        }

        if (maybeBindJoinPoint(parameters[0].getType()) || maybeBindProceedingJoinPoint(parameters[0].getType())) {
            joinPointArgumentIndex = 0;
        }

        for (int i = 0; i < parameters.length; i++) {
            argumentBindings.put(parameters[i].getName(), i);
        }
    }

    private boolean maybeBindJoinPoint(Class<?> candidateParameterType) {
        return JoinPoint.class == candidateParameterType;
    }

    private boolean maybeBindProceedingJoinPoint(Class<?> candidateParameterType) {
        if (ProceedingJoinPoint.class == candidateParameterType) {
            if (!supportsProceedingJoinPoint()) {
                throw new IllegalArgumentException("ProceedingJoinPoint is only supported for Around Advice");
            }
            return true;
        }
        return false;
    }

    protected boolean supportsProceedingJoinPoint() {
        return false;
    }

    public void setReturningName(String returningName) {
        this.returningName = returningName;
    }

    public void setThrowingName(String throwingName) {
        this.throwingName = throwingName;
    }
}
