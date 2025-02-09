package cn.wxiach.core.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxiach 2025/2/8
 */
public class AnnotationMetadata {

    /**
     * Stores annotations for the annotated element:
     * 1. Records duplicate annotations.
     * 2. Does not inherit annotations from superclasses.
     */
    private final Map<Class<? extends Annotation>, List<Annotation>> annotations = new ConcurrentHashMap<>();


    public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        return annotations.containsKey(annotationType);
    }

    /**
     * Finds the first annotation of the given type.
     * If multiple annotations of the same type exist, the first one represents the outermost annotation,
     * which is not affected by any meta-annotations.
     *
     * @param annotationType the annotation type to find
     * @return the first annotation of the given type
     */
    @SuppressWarnings("unchecked")
    public <T extends Annotation> T findAnnotation(Class<T> annotationType) {
        return (T) annotations.get(annotationType).get(0);
    }

    @SuppressWarnings("unchecked")
    public <T extends Annotation> List<T> findAnnotations(Class<T> annotationType) {
         List<Annotation> ret = annotations.get(annotationType);
         return ret != null ? (List<T>) ret : Collections.EMPTY_LIST;
    }

    public void addAnnotation(Annotation annotation) {
        annotations.computeIfAbsent(annotation.annotationType(), k -> new ArrayList<>()).add(annotation);
    }
}
