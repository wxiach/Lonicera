package cn.wxiach.context;

import cn.wxiach.beans.ListableBeanFactory;

/**
 * @author wxiach 2025/1/8
 */
public interface ApplicationContext extends ListableBeanFactory {
    void registerBean(Class<?> beanClass);
}
