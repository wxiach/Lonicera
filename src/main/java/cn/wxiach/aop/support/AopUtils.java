package cn.wxiach.aop.support;

import cn.wxiach.aop.AopInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wxiach 2025/1/25
 */
public class AopUtils {

    public static Object invokeJoinPointUsingReflection(Object object, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(object, args);
        } catch (IllegalAccessException e) {
            throw new AopInvocationException("Could not access method [" + method + "]", e);
        } catch (IllegalArgumentException e) {
            throw new AopInvocationException("Invalid arguments provided to method [" + method + "]", e);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}
