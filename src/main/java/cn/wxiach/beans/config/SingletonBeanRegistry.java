package cn.wxiach.beans.config;

/**
 * @author wxiach 2025/1/9
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);
}
