package cn.wxiach.beans;

/**
 * @author wxiach 2025/1/9
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);
}
