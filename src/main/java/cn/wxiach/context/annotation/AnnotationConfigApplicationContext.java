package cn.wxiach.context.annotation;

import cn.wxiach.context.support.GenericApplicationContext;

/**
 * @author wxiach 2025/1/9
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext {

    private final AnnotatedBeanDefinitionReader reader;

    private final ClassPathBeanDefinitionScanner scanner;

    public AnnotationConfigApplicationContext() {
        reader = new AnnotatedBeanDefinitionReader(this);
        scanner = new ClassPathBeanDefinitionScanner(this);
    }

    public AnnotationConfigApplicationContext(Class<?> commponentClass) {
        this();
        reader.registerBean(commponentClass);
        refresh();
    }

    public AnnotationConfigApplicationContext(String basePackage) {
        this();
        scanner.scanPackage(basePackage);
        refresh();
    }

}
