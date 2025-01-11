package cn.wxiach.app;

import cn.wxiach.annotations.Autowired;
import cn.wxiach.annotations.Component;
import cn.wxiach.annotations.Scope;

/**
 * @author wxiach 2025/1/10
 */
@Component
public class B {
    private A a;

    @Autowired
    public B(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }
}
