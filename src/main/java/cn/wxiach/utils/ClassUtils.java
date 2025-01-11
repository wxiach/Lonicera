package cn.wxiach.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author wxiach 2025/1/10
 */
public class ClassUtils {

    public static final Comparator<Executable> EXECUTABLE_COMPARATOR = (e1, e2) -> {
        int result = Boolean.compare(Modifier.isPublic(e2.getModifiers()), Modifier.isPublic(e1.getModifiers()));
        return result != 0 ? result : Integer.compare(e2.getParameterCount(), e1.getParameterCount());
    };

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassUtils.class.getClassLoader();
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    public static void sortConstructors(Constructor<?>[] constructors) {
        Arrays.sort(constructors, EXECUTABLE_COMPARATOR);
    }
}
