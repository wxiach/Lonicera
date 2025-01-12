package cn.wxiach.ioc.beans;

import cn.wxiach.annotations.Autowired;
import cn.wxiach.annotations.Component;

/**
 * @author wxiach 2025/1/12
 */
@Component
public class BeanB {
    @Autowired
    private BeanC beanC;

    public BeanC getBeanC() {
        return beanC;
    }
}
