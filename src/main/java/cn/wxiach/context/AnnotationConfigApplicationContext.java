package cn.wxiach.context;

/**
 * @author wxiach 2025/1/9
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext {

    private final AnnotatedBeanDefinitionReader reader;

    private final ClassPathBeanDefinitionScanner scanner;

    protected AnnotationConfigApplicationContext() {
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
