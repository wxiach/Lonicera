package cn.wxiach.autowire.field.circular;

import cn.wxiach.beans.annotation.Autowired;
import cn.wxiach.context.annotation.Component;

/**
 * @author wxiach 2025/2/11
 */
@Component
public class BeanD implements BeanInterface {

    @Autowired
    private BeanInterface beanC;

    public void print() {}
}
