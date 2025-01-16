package cn.wxiach.autowire.construct;

import cn.wxiach.annotations.Autowired;
import cn.wxiach.annotations.Component;

/**
 * @author wxiach 2025/1/15
 */
@Component
public class BeanA {

    private final BeanB beanB;

    @Autowired
    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }

    public BeanB getBeanB() {
        return beanB;
    }
}
