package cn.wxiach.aop.aspectj;


import cn.wxiach.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/1/21
 */
public class AspectJExpressionPointcut implements Pointcut {

    private final String pointcutExpression;

    public AspectJExpressionPointcut(String pointcutExpression) {
        this.pointcutExpression = pointcutExpression;
    }

    public boolean matches(Method method, Class<?> targetClass) {
        if (pointcutExpression.startsWith("execution")) {
            return matchesExecution(method, targetClass);
        }
        return false;
    }

    private boolean matchesExecution(Method method, Class<?> targetClass) {
        String executionPattern = pointcutExpression.substring(10, pointcutExpression.length() - 1);

        String returnTypePattern = executionPattern.split(" ")[0];
        String methodSignPattern = executionPattern.split(" ")[1];

        String methodNamePattern = methodSignPattern.split("\\(")[0];
        String methodParamPattern = methodSignPattern.split("\\(")[1].replace(")", "");

        if (!matchesReturnType(returnTypePattern, method.getReturnType())) {
            return false;
        }

        if (!matchesMethodName(methodNamePattern, targetClass.getName() + "." + method.getName())) {
            return false;
        }

        return matchesParameter(methodParamPattern, method.getParameterTypes());
    }

    private boolean matchesReturnType(String pattern, Class<?> returnType) {
        return matchesPattern(pattern, returnType.getName());
    }

    private boolean matchesMethodName(String pattern, String methodName) {
        return matchesPattern(pattern, methodName);
    }

    private boolean matchesParameter(String pattern, Class<?>[] parameterTypes) {
        String parameters = Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.joining(", "));
        return matchesPattern(pattern, parameters);
    }

    private boolean matchesPattern(String pattern, String target) {
        if ("*".equals(pattern)) {
            return true;
        }

        if ("..".equals(pattern)) {
            return true;
        }

        String regex = pattern.replace(".", "\\.").replace("*", ".*");
        Matcher matcher = Pattern.compile(regex).matcher(target);
        return matcher.matches();
    }

}
