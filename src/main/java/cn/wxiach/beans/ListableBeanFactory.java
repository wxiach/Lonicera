package cn.wxiach.beans;

import java.util.Map;

/**
 * @author wxiach 2025/1/9
 */
public interface ListableBeanFactory extends BeanFactory {

    boolean containsBeanDefinition(String beanName);

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws Exception;
}
