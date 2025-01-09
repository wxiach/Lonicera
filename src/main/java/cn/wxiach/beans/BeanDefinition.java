package cn.wxiach.beans;

/**
 * @author wxiach 2025/1/8
 */
public class BeanDefinition {
    private Class<?> beanClass;
    private String scope;

    public BeanDefinition() {}

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
