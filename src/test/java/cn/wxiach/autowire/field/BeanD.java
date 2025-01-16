package cn.wxiach.autowire.field;

import cn.wxiach.annotations.Autowired;
import cn.wxiach.annotations.Component;

/**
 * @author wxiach 2025/1/15
 */
@Component
public class BeanD {
    @Autowired
    private BeanC beanC;
}
