package cn.wxiach.autowire.field.circular;

import cn.wxiach.beans.annotation.Autowired;
import cn.wxiach.context.annotation.Component;

/**
 * @author wxiach 2025/2/11
 */
@Component
public class BeanC implements BeanInterface {
    @Autowired
    private BeanInterface beanD;

    public void print() {}
}
