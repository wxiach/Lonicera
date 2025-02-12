package cn.wxiach.autowire.field.circular;

import cn.wxiach.beans.annotation.Autowired;
import cn.wxiach.context.annotation.Component;

/**
 * @author wxiach 2025/1/15
 */
@Component
public class BeanB {
    @Autowired
    private BeanA beanA;

    public BeanA getBeanA() {
        return beanA;
    }
}
