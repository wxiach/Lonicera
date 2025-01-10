package cn.wxiach.beans;

/**
 *
 * @author wxiach 2025/1/8
 */
public interface BeanFactory {
    Object getBean(String name);
    <T> T getBean(String name, Class<T> requiredType);
}
