package cn.wxiach.beans;

/**
 * @author wxiach 2025/1/9
 */
public interface SingletonBeanRegistry {
    public void registerSingleton(String beanName, Object singletonObject);
    public Object getSingleton(String beanName);
}
