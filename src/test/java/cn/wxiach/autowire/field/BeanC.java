package cn.wxiach.autowire.field;

import cn.wxiach.beans.annotation.Autowired;
import cn.wxiach.context.annotation.Component;

/**
 * @author wxiach 2025/1/15
 */
@Component
public class BeanC {
    @Autowired
    private BeanD beanD;

    public BeanD getBeanD() {
        return beanD;
    }
}
