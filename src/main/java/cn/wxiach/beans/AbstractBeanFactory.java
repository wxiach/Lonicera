package cn.wxiach.beans;

/**
 * @author wxiach 2025/1/8
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        return this.doGetBean(name, null);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return this.doGetBean(name, requiredType);
    }

    @SuppressWarnings("unchecked")
    protected <T> T doGetBean(String name, Class<T> requiredType) {
        String beanName = transformedBeanName(name);
        Object beanInstance = getSingleton(beanName);
        if (beanInstance == null) {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            if (beanDefinition == null) {
                throw new BeansException("Bean with name " + name + " not found");
            }
            beanInstance = createBean(beanName, beanDefinition);
            registerSingleton(beanName, beanInstance);
        }
        if (requiredType != null && !requiredType.isInstance(beanInstance)) {
            throw new BeansException("Bean with name " + name + " is not of required type " + requiredType.getName());
        }
        return (T) beanInstance;
    }

    private String transformedBeanName(String name) {
        return name;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);
}
