package cn.wxiach.core.util;

import java.util.Objects;

/**
 * @author wxiach 2025/1/10
 */
public class ClassUtils {

    /**
     * This method guarantees that a non-null ClassLoader is returned.
     *
     * @return a non-null ClassLoader
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (Objects.isNull(classLoader)) {
            classLoader = ClassUtils.class.getClassLoader();
            if (Objects.isNull(classLoader)) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }
}
