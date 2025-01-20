package cn.wxiach.beans.config;

import cn.wxiach.beans.ListableBeanFactory;

/**
 * @author wxiach 2025/1/20
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory {

    void preInstantiateSingletons();
}
