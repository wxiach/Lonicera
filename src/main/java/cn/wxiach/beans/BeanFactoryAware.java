package cn.wxiach.beans;

/**
 * @author wxiach 2025/1/14
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
