package cn.wxiach.aop;

import cn.wxiach.context.annotation.Component;

/**
 * @author wxiach 2025/1/22
 */
@Component
public class User {
    public void say() {
        System.out.println("I'm Bob!");
    }
}
