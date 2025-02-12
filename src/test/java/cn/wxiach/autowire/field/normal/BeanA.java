package cn.wxiach.autowire.field.normal;

import cn.wxiach.beans.annotation.Autowired;
import cn.wxiach.context.annotation.Component;

/**
 * @author wxiach 2025/1/15
 */
@Component
public class BeanA {
    @Autowired
    private BeanB beanB;

    public BeanB getBeanB() {
        return beanB;
    }
}
