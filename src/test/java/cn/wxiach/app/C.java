package cn.wxiach.app;

import cn.wxiach.annotations.Component;
import cn.wxiach.annotations.Scope;
import cn.wxiach.beans.BeanDefinition;

/**
 * @author wxiach 2025/1/8
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class C {
    String name = "c";

    public String getName() {
        return name;
    }
}
