package cn.wxiach.ioc.beans;

import cn.wxiach.annotations.Autowired;
import cn.wxiach.annotations.Component;

/**
 * @author wxiach 2025/1/12
 */
@Component
public class BeanA {
    private BeanB beanB;

    @Autowired
    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }

    public BeanB getBeanB() {
        return beanB;
    }
}
