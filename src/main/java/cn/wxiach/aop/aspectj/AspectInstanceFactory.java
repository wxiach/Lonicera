package cn.wxiach.aop.aspectj;

import java.lang.reflect.InvocationTargetException;

/**
 * @author wxiach 2025/1/22
 */
public class AspectInstanceFactory {

    private final Class<?> aspectClass;

    public AspectInstanceFactory(Class<?> aspectClass) {
        this.aspectClass = aspectClass;
    }

    public Object getAspectInstance() {
        try {
            return aspectClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
