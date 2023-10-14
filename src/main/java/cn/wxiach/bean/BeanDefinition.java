package cn.wxiach.bean;

/**
 * @author wxiach 2023/10/12
 */
public class BeanDefinition {

    private final Object bean;
    private final Class<?> type;

    public BeanDefinition(Object bean) {
        this(bean, bean.getClass());
    }

    public BeanDefinition(Object bean, Class<?> type) {
        this.bean = bean;
        this.type = type;
    }

    public Object getBean() {
        return bean;
    }

    public Class<?> getType() {
        return type;
    }
}
