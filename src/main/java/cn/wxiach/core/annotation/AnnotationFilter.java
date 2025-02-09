package cn.wxiach.core.annotation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author wxiach 2025/2/9
 */
@FunctionalInterface
public interface AnnotationFilter extends Predicate<Annotation> {

    List<String> DEFAULT_EXCLUDED_PACKAGE_PREFIXES = List.of("java.", "javax.");

    static AnnotationFilter defaultFilter () {
        return of(DEFAULT_EXCLUDED_PACKAGE_PREFIXES);
    }

    static AnnotationFilter of(List<String> packagePrefixes) {
        return annotation -> {
            String annotationName = annotation.annotationType().getName();
            return packagePrefixes.stream().noneMatch(annotationName::startsWith);
        };
    }
}
