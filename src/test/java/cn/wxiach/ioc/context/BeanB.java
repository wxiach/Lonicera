package cn.wxiach.ioc.context;

import cn.wxiach.annotations.Autowired;
import cn.wxiach.annotations.Component;

/**
 * @author wxiach 2025/1/10
 */
@Component
public class BeanB {
    private final BeanA beanA;

    @Autowired
    public BeanB(BeanA beanA) {
        this.beanA = beanA;
    }

    public BeanA getA() {
        return beanA;
    }
}
