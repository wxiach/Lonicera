package cn.wxiach.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/1/15
 */
public class AnnotationUtils {

    public static final String[] DEFAULT_ANNOTATION_FILTER = {"java.", "javax."};

    public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annotationClass) {
        if (clazz.isAnnotationPresent(annotationClass)) {
            return clazz.getAnnotation(annotationClass);
        }

        Collection<Annotation> candidateAnnotations = filterAnnotations(clazz.getAnnotations());
        for (Annotation candidateAnnotation : candidateAnnotations) {
            T metaAnnotation = findAnnotation(candidateAnnotation.annotationType(), annotationClass);
            if (metaAnnotation != null) {
                return metaAnnotation;
            }
        }

        return null;
    }


    public static <T extends Annotation> Collection<T> filterAnnotations(T[] annotations) {
        return Arrays.stream(annotations).filter(annotation -> {
            String annotationName = annotation.annotationType().getName();
            return Arrays.stream(DEFAULT_ANNOTATION_FILTER).noneMatch(annotationName::startsWith);
        }).collect(Collectors.toList());
    }
}
