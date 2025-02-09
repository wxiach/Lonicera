package cn.wxiach.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/2/8
 */
public class AnnotationMetadataReader {

    private static final Map<AnnotatedElement, AnnotationMetadata> annotationMetadataCaching = new ConcurrentHashMap<>();

    public static AnnotationMetadata getAnnotationMetadata(AnnotatedElement element) {
        return annotationMetadataCaching.computeIfAbsent(element, e -> createAnnotationMetadata(element));
    }

    private static AnnotationMetadata createAnnotationMetadata(AnnotatedElement element) {
        return new AnnotationMetadataResolver().resolve(element);
    }

    private static class AnnotationMetadataResolver {

        private final AnnotationMetadata annotationMetadata = new AnnotationMetadata();

        private final AnnotationFilter annotationFilter = AnnotationFilter.defaultFilter();

        /**
         * Resolves the annotations of the given element using level-order traversal.
         * @param element the element whose annotations are to be resolved
         * @return a collection of annotations found on the element
         */
        public AnnotationMetadata resolve(AnnotatedElement element) {
            Deque<Annotation> deque = new LinkedList<>(getDeclaredAnnotationsWithFilter(element));
            while (!deque.isEmpty()) {
                Annotation annotation = deque.pop();
                /*
                 * Skip the annotation that has already been processed:
                 * 1. To avoid redundant processing.
                 * 2. To prevent circular dependencies.
                 */
                if (!annotationMetadata.hasAnnotation(annotation.annotationType())) {
                    deque.addAll(getDeclaredAnnotationsWithFilter(annotation.annotationType()));
                }
                annotationMetadata.addAnnotation(annotation);
            }
            return annotationMetadata;
        }

        private List<Annotation> getDeclaredAnnotationsWithFilter(AnnotatedElement element) {
            return Arrays.stream(element.getAnnotations()).filter(annotationFilter).collect(Collectors.toList());
        }
    }
}
