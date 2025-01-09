package cn.wxiach.beans;

/**
 * @author wxiach 2025/1/8
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name) {
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
        return beanInstance;
    }

    private String transformedBeanName(String name) {
        return name;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);
}
