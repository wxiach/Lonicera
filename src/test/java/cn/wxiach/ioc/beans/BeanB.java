package cn.wxiach.ioc.beans;

import cn.wxiach.annotations.Autowired;

/**
 * @author wxiach 2025/1/12
 */
public class BeanB {
    @Autowired
    private BeanC beanC;

    public BeanC getBeanC() {
        return beanC;
    }
}
