package cn.wxiach.core.util;

import java.lang.annotation.Annotation;

/**
 * @author wxiach 2025/1/15
 */
public class AnnotationUtils {

    public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annotationClass) {
        if (clazz.isAnnotationPresent(annotationClass)) {
            return clazz.getAnnotation(annotationClass);
        }

        Annotation[] candidateAnnotations = clazz.getAnnotations();
        for (Annotation candidateAnnotation : candidateAnnotations) {
            String candidateAnnotationName = candidateAnnotation.annotationType().getName();
            if (!candidateAnnotationName.startsWith("java.") && !candidateAnnotationName.startsWith("javax.")) {
                T metaAnnotation = findAnnotation(candidateAnnotation.annotationType(), annotationClass);
                if (metaAnnotation != null) {
                    return metaAnnotation;
                }
            }
        }

        return null;
    }
}
