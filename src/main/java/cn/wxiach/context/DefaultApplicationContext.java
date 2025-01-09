package cn.wxiach.context;

/**
 * @author wxiach 2025/1/9
 */
public class DefaultApplicationContext extends GenericApplicationContext {

    private final AnnotatedBeanDefinitionReader reader;

    public DefaultApplicationContext() {
        reader = new AnnotatedBeanDefinitionReader(this);
    }

    public DefaultApplicationContext(Class<?> commponentClass) {
        this();
        reader.registerBean(commponentClass);
        refresh();
    }
}
